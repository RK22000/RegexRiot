//package io.github.rk22000.RegexRiotTest;
//
//import io.github.rk22000.RegexRiot.RiotSet;
//import io.github.rk22000.RegexRiot.RiotTypedSet.RiotSetUtils;
//import org.junit.jupiter.api.Test;
//
//import static io.github.rk22000.RegexRiot.RiotTypedSet.ExclusiveSet.riotExclude;
//import static io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet.riotInclude;
//
//
//public class RiotTypedSetTest {
//    @Test
//    void t1() {
//        RiotSet set = riotInclude("abc").include("xyz");
//        assert set.toString().equals("[abcxyz]");
//        set = riotExclude("abc").exclude("xyz");
//        assert set.toString().equals("[^abcxyz]");
//    }
//}
