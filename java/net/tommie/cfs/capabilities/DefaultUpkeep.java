package net.tommie.cfs.capabilities;

import net.tommie.cfs.config.ClaimFlagsConfig;

public class DefaultUpkeep implements IUpkeep{
    private int upkeep = ClaimFlagsConfig.kingdom_upkeep.get();
    @Override
    public int getUpkeep() {
        return this.upkeep;
    }

    @Override
    public void setUpkeep(int upkeep) {
        this.upkeep = upkeep;
    }
}
