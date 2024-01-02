package net.tommie.cfs.capabilities;

import net.minecraft.nbt.ListNBT;


public interface IPatterns {
    void setPatterns(ListNBT patterns);

    ListNBT getPatterns();

    boolean comparePatterns(ListNBT patterns);

    boolean compareColors(String color);

    String getColor();

    void setColor(String color);

}
