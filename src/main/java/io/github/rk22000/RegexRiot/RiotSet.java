//package io.github.rk22000.RegexRiot;
//
//import io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet;
//
//import static io.github.rk22000.RegexRiot.Riot.riot;
//import static io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet.riotInclude;
//
//public interface RiotSet {
//    default RiotString toRiotString() {
//        return riot(toString(), true);
//    }
//    String rawString();
//    RiotSet complement();
//}