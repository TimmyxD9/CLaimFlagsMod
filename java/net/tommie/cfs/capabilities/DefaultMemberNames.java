package net.tommie.cfs.capabilities;

import java.util.ArrayList;
import java.util.List;

public class DefaultMemberNames implements IMemberNames{
    private List<String> memberNames = new ArrayList<>();

    public void removeMemberName(String memberId) {
        if(this.memberNames.contains(memberId))
            this.memberNames.remove(memberId);
    }

    public void addMemberName(String memberId) {
        if(!this.memberNames.contains(memberId))
            this.memberNames.add(memberId);
    }

    public boolean checkMemberName(String memberId) {
        if(this.memberNames.contains(memberId))
            return true;
        else
            return false;
    }

    public List<String> getMemberNames(){
        return this.memberNames;
    }
}
