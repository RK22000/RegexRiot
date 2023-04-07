package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotSet {
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
    RiotSet union(RiotSet oSet);

    default RiotString toRiotString() {
        return riot(toString(), true);
    }

    static RiotSet charsIn(char[] chars) {
        return riotSet().include(new String(chars));
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

class BasicRiotSet implements RiotSet{
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

