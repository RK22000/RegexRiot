//package io.github.rk22000.RegexRiot;
//
//import java.util.regex.Pattern;
//
//import static io.github.rk22000.RegexRiot.Riot.riot;
//
//public interface RiSet extends RiotString{
//    static RiSet riotSet() {
//        return new BasicRiSet("", "");
//    }
//    RiSet include(String includes);
//    default RiSet include(RiotString includes) {
//        return this.include(includes.toString());
//    }
//    RiSet exclude(String excludes);
//    default RiSet exclude(RiotString excludes) {
//        return this.exclude(excludes.toString());
//    }
//
//    // TODO: Deprecate this methods. Instead exclusively use include() and exclude()
//    RiSet union(RiSet oSet);
//
//    default RiotString toRiotString() {
//        return riot(toString(), true);
//    }
//
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
//    static RiSet chars(String chars) {
//        return riotSet().include(chars);
//    }
//}
//
//abstract class RiSetString implements RiSet {
//    @Override
//    public RiotString wholeTimes(int atleast, int atmost) {
//        return toRiotString().wholeTimes(atleast, atmost);
//    }
//
//    @Override
//    public RiotString wholeTimes(int repeatCount) {
//        return toRiotString().wholeTimes(repeatCount);
//    }
//
//    @Override
//    public RiotString wholeThingOptional() {
//        return toRiotString().wholeThingOptional();
//    }
//
//    @Override
//    public RiotString wholeThingGrouped() {
//        return toRiotString().wholeThingGrouped();
//    }
//
//    @Override
//    public RiotString wholeThingGroupedAs(String name) {
//        return toRiotString().wholeThingGroupedAs(name);
//    }
//
//    @Override
//    public boolean isUnitChain() {
//        return toRiotString().isUnitChain();
//    }
//
//    @Override
//    public RiotString times(int atleast, int atmost) {
//        return toRiotString().times(atleast, atmost);
//    }
//
//    @Override
//    public RiotString times(int repeatCount) {
//        return toRiotString().times(repeatCount);
//    }
//
//    @Override
//    public RiotString optionally() {
//        return toRiotString().optionally();
//    }
//
//    @Override
//    public RiotString grouped() {
//        return toRiotString().grouped();
//    }
//
//    @Override
//    public RiotString groupedAs(String name) {
//        return toRiotString().groupedAs(name);
//    }
//}
//
//class BasicRiSet extends RiSetString {
//    String inString = "", outString = "";
//    BasicRiSet(String includes, String excludes) {
//        inString = includes;
//        outString = excludes;
//    }
//
//    @Override
//    public String toString() {
//        var suffix = "".equals(outString) ? "" : "^"+outString;
//        return "["+inString+suffix+"]";
//    }
//
//    @Override
//    public RiSet include(String includes) {
//        // inString = A-F, include = a-f, result -> A-Faf. not good
//        return new BasicRiSet(includes+inString, outString);
//    }
//    @Override
//    public RiSet exclude(String excludes) {
//        return new BasicRiSet(inString, excludes+outString);
//    }
//
//    @Override
//    public RiSet union(RiSet oSet) {
//        return oSet.include(inString).exclude(outString);
//    }
//}
//
