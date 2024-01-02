package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.tommie.cfs.ClaimFlags;

import java.util.*;

public class PlayerNameOwnerCache extends WorldSavedData{
        private List<String> playerCache = new ArrayList<>();
        private List<String> nameCache = new ArrayList<>();
        private List<Boolean> ownerCache = new ArrayList<>();
        public PlayerNameOwnerCache(String p_i2141_1_) {
            super(p_i2141_1_);
        }

        public static net.tommie.cfs.capabilities.PlayerNameOwnerCache getCache(MinecraftServer server){
            return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getSavedData().getOrCreate(() -> new net.tommie.cfs.capabilities.PlayerNameOwnerCache(ClaimFlags.MOD_ID), ClaimFlags.MOD_ID);
        }

        public boolean checkOwner(String id)
        {
            return ownerCache.get(playerCache.indexOf(id));
        }
        public String getPlayerName(String id){return nameCache.get(playerCache.indexOf(id));}

        public void addPlayer(String id, String name){
            if(!playerCache.contains(id))
                {
                    playerCache.add(id);
                    nameCache.add(name);
                    ownerCache.add(false);
                }
            else
            {
                int index = playerCache.indexOf(id);
                if(!nameCache.get(index).equals(name))
                    nameCache.add(index,name);
            }
            this.markDirty();
        }
        public List<String> getMembers(){
            return playerCache;
        }
        public List<String> getNames(){
            return nameCache;
        }
        public List<Boolean> getOwners(){
            return ownerCache;
        }
        public String getEverything(){
            return playerCache.toString() + "\n" + nameCache.toString() + "\n" + ownerCache.toString();
        }

        public void setOwner(String playerId, boolean isOwner){
            if(playerCache.contains(playerId)) {
                int index = playerCache.indexOf(playerId);
                ownerCache.add(index,isOwner);
                this.markDirty();
            }
        }

        @Override
        public void read(CompoundNBT nbt) {
            playerCache.clear();
            nameCache.clear();
            ownerCache.clear();
            final ListNBT cacheList = nbt.getList("caches", 10);
            for(int i = 0, l = cacheList.size(); i<l; i++){
                CompoundNBT tmpTag = cacheList.getCompound(i);
                playerCache.add(tmpTag.getString("playerid"));
                nameCache.add(tmpTag.getString("playername"));
                ownerCache.add(tmpTag.getBoolean("isowner"));
            }
        }

        @Override
        public CompoundNBT write(CompoundNBT compound) {
            final ListNBT cacheList = new ListNBT();
            for(int i=0; i< playerCache.size();i++)
            {
                CompoundNBT entryTag = new CompoundNBT();
                entryTag.putString("playerid", playerCache.get(i));
                entryTag.putString("playername", nameCache.get(i));
                entryTag.putBoolean("isowner",ownerCache.get(i));
                cacheList.add(entryTag);
            }
            compound.put("caches", cacheList);
            return compound;
        }
    }
