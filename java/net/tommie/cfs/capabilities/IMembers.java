package net.tommie.cfs.capabilities;

import java.util.List;

public interface IMembers {
    void removeMember(String memberId);

    void addMember(String memberId);

    //void removeList();

    boolean checkMember(String memberId);

    List<String> getMembers();

}
