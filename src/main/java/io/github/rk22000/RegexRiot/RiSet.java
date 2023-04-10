package io.github.rk22000.RegexRiot;

import java.util.regex.Pattern;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotSet extends RiotString{
    static RiotSet riotSet() {
        return new BasicRiotSet("", "");
    }
    RiotSet include(String includes);
    default RiotSet include(RiotString includes) {
        return this.include(includes.toString());
    }
    RiotSet exclude(String excludes);
    default RiotSet exclude(RiotString excludes) {
        return this.exclude(excludes.toString());
    }

    // TODO: Deprecate this methods. Instead exclusively use include() and exclude()
    RiotSet union(RiotSet oSet);

    default RiotString toRiotString() {
        return riot(toString(), true);
    }

    class RiotCharRange {
        private final char startChar;
        RiotCharRange(char inclusiveStartChar) {
            startChar = inclusiveStartChar;
        }
        public String through(char inclusiveEndChar) {
            return startChar+"-"+inclusiveEndChar;
        }
    }

    static RiotCharRange chars(char inclusiveStartChar) {
        return new RiotCharRange(inclusiveStartChar);
    }
    static RiotSet chars(String chars) {
        return riotSet().include(chars);
    }
}

abstract class RiotSetString implements RiotSet{

    @Override
    public SimpleRiotString simpleRiotStringFrom(String expression) {
        return riot(expression).toSimpleRiotString();
    }

    @Override
    public SimpleRiotString toSimpleRiotString() {
        return toRiotString().toSimpleRiotString();
    }

    @Override
    public RiotString and(SimpleRiotString extension) {
        return toRiotString().and(extension);
    }

    @Override
    public RiotString or(SimpleRiotString extension) {
        return toRiotString().or(extension);
    }

    @Override
    public RiotString wholeTimes(int atleast, int atmost) {
        return toRiotString().wholeTimes(atleast, atmost);
    }

    @Override
    public RiotString wholeTimes(int repeatCount) {
        return toRiotString().wholeTimes(repeatCount);
    }

    @Override
    public RiotString wholeThingOptional() {
        return toRiotString().wholeThingOptional();
    }

    @Override
    public RiotString wholeThingGrouped() {
        return toRiotString().wholeThingGrouped();
    }

    @Override
    public RiotString wholeThingGroupedAs(String name) {
        return toRiotString().wholeThingGroupedAs(name);
    }

    @Override
    public boolean isUnitChain() {
        return toRiotString().isUnitChain();
    }

    @Override
    public Pattern compiledPattern() {
        return toRiotString().compiledPattern();
    }

    @Override
    public RiotString times(int atleast, int atmost) {
        return toRiotString().times(atleast, atmost);
    }

    @Override
    public RiotString times(int repeatCount) {
        return toRiotString().times(repeatCount);
    }

    @Override
    public RiotString optionally() {
        return toRiotString().optionally();
    }

    @Override
    public RiotString grouped() {
        return toRiotString().grouped();
    }

    @Override
    public RiotString groupedAs(String name) {
        return toRiotString().groupedAs(name);
    }
}

class BasicRiotSet extends RiotSetString{
    String inString = "", outString = "";
    BasicRiotSet(String includes, String excludes) {
        inString = includes;
        outString = excludes;
    }

    @Override
    public String toString() {
        var suffix = "".equals(outString) ? "" : "^"+outString;
        return "["+inString+suffix+"]";
    }

    @Override
    public RiotSet include(String includes) {
        // inString = A-F, include = a-f, result -> A-Faf. not good
        return new BasicRiotSet(includes+inString, outString);
    }
    @Override
    public RiotSet exclude(String excludes) {
        return new BasicRiotSet(inString, excludes+outString);
    }

    @Override
    public RiotSet union(RiotSet oSet) {
        return oSet.include(inString).exclude(outString);
    }
}
