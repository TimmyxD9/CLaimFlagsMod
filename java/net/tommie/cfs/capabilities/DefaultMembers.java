package net.tommie.cfs.capabilities;


import java.util.ArrayList;
import java.util.List;

public class DefaultMembers implements IMembers{
    private List<String> members = new ArrayList<>();

    public void removeMember(String memberId) {
        if(this.members.contains(memberId))
            this.members.remove(memberId);
    }

    public void addMember(String memberId) {
        if(!this.members.contains(memberId))
            this.members.add(memberId);
    }

    public boolean checkMember(String memberId) {
        if(this.members.contains(memberId))
            return true;
        else
            return false;
    }

    public List<String> getMembers(){
        return this.members;
    }
}
