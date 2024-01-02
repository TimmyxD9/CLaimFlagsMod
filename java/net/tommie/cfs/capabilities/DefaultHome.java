package net.tommie.cfs.capabilities;

public class DefaultHome implements IHome{
    private String home = "";
    public void setHome(String home){ this.home=home; }
    public String getHome(){ return this.home; }
}
