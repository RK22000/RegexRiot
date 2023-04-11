//package io.github.rk22000.RegexRiot.RiotTypedSet;
//
//
//import io.github.rk22000.RegexRiot.RiotSet;
//import io.github.rk22000.RegexRiot.RiotString;
//
//import java.util.regex.Pattern;
//
//import static io.github.rk22000.RegexRiot.Riot.riot;
//
//abstract class RiotSetString implements RiotSet, RiotString {
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
//    public Pattern compiledPattern() {
//        return toRiotString().compiledPattern();
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
