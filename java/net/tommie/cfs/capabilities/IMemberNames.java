package net.tommie.cfs.capabilities;

import java.util.List;

public interface  IMemberNames {
    void removeMemberName(String memberId);

    void addMemberName(String memberId);

    //void removeList();

    boolean checkMemberName(String memberId);

    List<String> getMemberNames();

}
