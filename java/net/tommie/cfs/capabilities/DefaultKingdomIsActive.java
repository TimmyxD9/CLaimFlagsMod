package net.tommie.cfs.capabilities;

public class DefaultKingdomIsActive implements IKingdomIsActive{
    private boolean kingdomIsACtive = true;

    @Override
    public boolean getKingdomIsActive() {
        return this.kingdomIsACtive;
    }

    @Override
    public void setKingdomIsActive(boolean active) {
        this.kingdomIsACtive = active;
    }
}
