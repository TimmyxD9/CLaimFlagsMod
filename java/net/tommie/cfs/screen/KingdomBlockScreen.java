package net.tommie.cfs.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tommie.cfs.ClaimFlags;
import net.tommie.cfs.container.KingdomBlockContainer;
import net.tommie.cfs.packeting.Networking;
import net.tommie.cfs.packets.GetClaimsPacket;
import net.tommie.cfs.packets.KickMemberPacket;
import net.tommie.cfs.packets.PurchasePacket;

import java.util.*;
import java.util.List;

public class KingdomBlockScreen extends ContainerScreen<KingdomBlockContainer> {
    private final ResourceLocation GUI = new ResourceLocation(ClaimFlags.MOD_ID, "textures/gui/kingdom_block_gui.png");
    private PlayerEntity player = null;
    private World world = null;
    private int indexStarting = 1;
    private List<String> members = new ArrayList<>();
    private List<String> memberNames = new ArrayList<>();
    private Map<String,Button> kicks = new HashMap<>();

    private final int PLAYER_DISPLAY_AREA_HEIGHT = 48;
    private final int PLAYER_DISPLAY_AREA_WIDTH = 73;
    private final int PLAYER_DISPLAY_AREA_TOP = 5;
    private final int PLAYER_DISPLAY_AREA_BOTTOM = 52;
    private final int PLAYER_DISPLAY_AREA_LEFT = 38;
    private final int PLAYER_DISPLAY_AREA_RIGHT = 111;
    private int GUI_TOP;
    private int GUI_LEFT;
    public KingdomBlockScreen(KingdomBlockContainer container, PlayerInventory inventory, ITextComponent text) {
        super(container, inventory, text);
        player = container.getPlayer();
        world = player.getEntityWorld();
        members=this.getContainer().members;
        memberNames=this.getContainer().memberNames;
    }

    @Override
    public void init(){
        super.init();
        GUI_TOP = this.guiTop;
        GUI_LEFT = this.guiLeft;
        int i = this.guiLeft + 133;
        int j = this.guiTop + 8;
        this.addButton(new Button(i, j, 34, 19, new TranslationTextComponent("btntext.cfs.buy"), button -> purchase()));
        i-=18;
        j+=20;
        if(player.isCreative()||player.isSpectator())
            this.addButton(new Button(i, j, 52, 19, new TranslationTextComponent("btntext.cfs.locate"), button -> locate(this.getContainer().getTe())));
        if(player.getUniqueID().toString().equals(this.getContainer().getOwner())) {
            Button kick;
            i = this.guiLeft;
            j = this.guiTop;
            int i2 = i + 38;
            int j2 = j + 8;
            int cap = this.indexStarting + 3;
            j2+=12;

            for (int displaying = 2; displaying <= members.size(); displaying++) {
                    int finalDisplaying = displaying - 1;
                    int finalJ = j2;
                    kick = new Button(i2 + 44, j2 - 1 + (finalDisplaying - 1) * 12, 25, 8, new TranslationTextComponent("Kick"), button -> kick(this.indexStarting+(button.y-finalJ)/12));
                    this.addButton(kick);
                    kick.visible = false;
                    kicks.put(members.get(finalDisplaying), kick);
            }
            for(int displaying = this.indexStarting; displaying <= members.size() && displaying<cap; displaying++){
                if(displaying!=1)
                    kicks.get(members.get(displaying-1)).visible = true;
            }
        }

    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y){
        int cap = this.indexStarting + 3;
        for(int k = this.indexStarting-1; k< members.size() && k<cap; k++) {
            String name = memberNames.get(k);
            if(name.length()>8)
                name=name.substring(0,6) + "...";
            drawString(matrixStack, this.font, name, PLAYER_DISPLAY_AREA_LEFT + 2, PLAYER_DISPLAY_AREA_TOP + 2 + (k-indexStarting+1)*12, 0);
        }
        if(x>(PLAYER_DISPLAY_AREA_LEFT + GUI_LEFT) && x<(PLAYER_DISPLAY_AREA_RIGHT + GUI_LEFT) && y>(PLAYER_DISPLAY_AREA_TOP + GUI_TOP) && y<(PLAYER_DISPLAY_AREA_BOTTOM + GUI_TOP))
            {
                this.minecraft.getTextureManager().bindTexture(GUI);
                int index = this.indexStarting-1 + (y-PLAYER_DISPLAY_AREA_TOP-GUI_TOP)/12;
                if(memberNames.size()>index) {
                    int hoverToolTipLength = memberNames.get(index).length() * 6 + 5;
                    this.blit(matrixStack, x - GUI_LEFT, y - GUI_TOP, 0, this.ySize + 14, hoverToolTipLength, 12);
                    this.blit(matrixStack, x + hoverToolTipLength - GUI_LEFT, y - GUI_TOP, this.xSize, this.ySize + 14, 2, 12);
                    drawString(matrixStack, this.font, memberNames.get(index), x - GUI_LEFT + 5, y - GUI_TOP + 2, 16776448);
                }
            }
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack, i, j,0 ,0, this.xSize, this.ySize);
        List<Slot> slots = this.getContainer().getSlots();
        Slot slot = slots.get(0);
        if(!slot.getHasStack())
            this.blit(matrixStack,i+9,j+22,178,0,15,15);
        for(int index=1; index<slots.size(); index++)
        {
            slot = slots.get(index);
            if(!slot.getHasStack())
                this.blit(matrixStack,i+9+(index-1)*18,j+55,178,15,15,15);
        }
        int cap = this.indexStarting + 4;
        int i2 = i+PLAYER_DISPLAY_AREA_LEFT;
        int j2 = j+PLAYER_DISPLAY_AREA_TOP;
        for(int displaying = this.indexStarting; displaying <= members.size() && displaying < cap; ++displaying)
        {
            this.minecraft.getTextureManager().bindTexture(GUI);
            int l = this.ySize;
            this.blit(matrixStack, i2, j2, 0, l+2, PLAYER_DISPLAY_AREA_WIDTH, 12);
            j2 += 12;
        }
    }

    protected void purchase(){
        TileEntity entity = this.container.getTe();

        Networking.INSTANCE.sendToServer(new PurchasePacket(entity.getPos()));
    }
    protected void locate(TileEntity te){
        Networking.INSTANCE.sendToServer(new GetClaimsPacket(te.getPos()));
    }
    protected void kick(int position){
        boolean flag = false;
        String playerId = members.get(position);
        String kick;
        Button currentKick;
        for(int i=1; i<members.size(); i++)
        {
            kick = members.get(i);
            currentKick = kicks.get(kick);
            if(flag)
                if(kicks.containsKey(kick))
                    currentKick.y = currentKick.y - 12;
                else
                    break;
            if(kick.equals(playerId))
            {
                flag=true;
                if(members.size()-this.indexStarting>=4) {
                    kicks.get(members.get(this.indexStarting+3)).visible = true;
                }
                currentKick.visible = false;
            }
        }
        kicks.remove(playerId);
        Networking.INSTANCE.sendToServer(new KickMemberPacket(this.getContainer().getTe().getPos(), playerId, memberNames.get(position)));
        memberNames.remove(members.indexOf(playerId));
        members.remove(playerId);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        System.out.println(delta);
        if(delta>(double)0) {
            if (indexStarting > 1) {
                this.indexStarting--;
                shiftKicksDown();
                refreshKicks();
                }
            }
        else
            {
                if(indexStarting+3<this.getContainer().members.size()) {
                    this.indexStarting++;
                    shiftKicksUp();
                    refreshKicks();
                }
            }

        return true;
    }

    protected void shiftKicksUp(){
        for(int i=0; i<kicks.size(); i++)
            kicks.get(members.get(i+1)).y=kicks.get(members.get(i+1)).y-12;
    }
    protected void shiftKicksDown(){
        for(int i=0; i<kicks.size(); i++)
            kicks.get(members.get(i+1)).y=kicks.get(members.get(i+1)).y+12;
    }
    protected void refreshKicks(){
        for(int i=0; i<kicks.size();i++)
            kicks.get(members.get(i+1)).visible=false;
        int j;
        if(indexStarting==1)
            j=2;
        else
            j=indexStarting;
        for(int i = j; i<indexStarting+4;i++)
        {
            kicks.get(members.get(i-1)).visible=true;
        }
    }



}
