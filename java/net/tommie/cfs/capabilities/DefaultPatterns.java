package net.tommie.cfs.capabilities;

import net.minecraft.nbt.ListNBT;

public class DefaultPatterns implements IPatterns{

    private ListNBT patterns = new ListNBT();
    private String color = "white";
    @Override
    public void setPatterns(ListNBT patterns) {
        this.patterns = patterns;
    }

    @Override
    public ListNBT getPatterns() {
        return this.patterns;
    }

    @Override
    public boolean comparePatterns(ListNBT patterns) {
        return this.patterns.equals(patterns);
    }

    @Override
    public boolean compareColors(String color) {
        return this.color.equals(color);
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public void setColor(String color) {
        this.color=color;
    }
}
