package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.RiotStringImplementations.newLazyRiot;

public interface RiotGroupings {
    static RiotString group(int groupNo) {
        return newLazyRiot("\\"+groupNo, true);
    }
    static RiotString group(String groupName) {
        return newLazyRiot("\\k<"+groupName+">", true);
    }
    static RiotString replacementGroup(int groupNo) {
        return newLazyRiot("$"+groupNo, true);
    }
    static RiotString replacementGroup(String groupName) {
        return newLazyRiot("${"+groupName+"}");
    }
}
