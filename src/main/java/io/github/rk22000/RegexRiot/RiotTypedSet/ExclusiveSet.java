//package io.github.rk22000.RegexRiot.RiotTypedSet;
//
//import io.github.rk22000.RegexRiot.Riot;
//import io.github.rk22000.RegexRiot.RiotSet;
//import io.github.rk22000.RegexRiot.RiotString;
//
//import java.util.List;
//import java.util.function.Function;
//
//import static io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet.riotInclude;
//
//public abstract class ExclusiveSet extends RiotSetString {
//    public abstract ExclusiveSet exclude(String exclude);
//    public static <T> ExclusiveSet riotExclude(T initialChars) {
//        String initial = initialChars instanceof RiotSet ? ((RiotSet) initialChars).rawString() : initialChars.toString();
//        return new ExclusiveSet() {
//            @Override
//            public String rawString() {
//                return initial;
//            }
//
//            @Override
//            public InclusiveSet complement() {
//                return riotInclude(this);
//            }
//
//
//            @Override
//            public ExclusiveSet exclude(String exclude) {
//                return riotExclude(rawString()+exclude);
//            }
//
//            @Override
//            public String toString() {
//                return "[^"+rawString()+"]";
//            }
//        };
//    }
//}