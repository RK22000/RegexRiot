//package io.github.rk22000.RegexRiot.RiotTypedSet;
//
//import static io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet.riotInclude;
//
//public interface RiotSetUtils {
//    class RiotCharRange {
//        private final char startChar;
//        RiotCharRange(char inclusiveStartChar) {
//            startChar = inclusiveStartChar;
//        }
//        public String through(char inclusiveEndChar) {
//            return startChar+"-"+inclusiveEndChar;
//        }
//    }
//
//    static RiotCharRange chars(char inclusiveStartChar) {
//        return new RiotCharRange(inclusiveStartChar);
//    }
//    static InclusiveSet chars(String includedChars) {
//        return riotInclude(includedChars);
//    }
//}
