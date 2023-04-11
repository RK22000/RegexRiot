//package io.github.rk22000.RegexRiot.RiotTypedSet;
//
//import io.github.rk22000.RegexRiot.RiotSet;
//
//import static io.github.rk22000.RegexRiot.RiotTypedSet.ExclusiveSet.riotExclude;
//
//public abstract class InclusiveSet extends RiotSetString {
//    public abstract InclusiveSet include(String includes);
//    public static <T> InclusiveSet riotInclude(T initialChars) {
//        String initial = initialChars instanceof RiotSet ? ((RiotSet) initialChars).rawString() : initialChars.toString();
//        return new InclusiveSet() {
//            @Override
//            public String rawString() {
//                return initial;
//            }
//
//            @Override
//            public ExclusiveSet complement() {
//                return riotExclude(this);
//            }
//
//            @Override
//            public InclusiveSet include(String includes) {
//                return riotInclude(rawString() + includes);
//            }
//
//            @Override
//            public String toString() {
//                return "["+rawString()+"]";
//            }
//        };
//    }
//}
//
