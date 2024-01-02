package net.tommie.cfs.capabilities;

public class DefaultOwner implements IOwner{
    private String ownerId = "";

    public void setOwnerId(String ownerId){this.ownerId = ownerId;}

    public String getOwnerId(){return this.ownerId;}
}
