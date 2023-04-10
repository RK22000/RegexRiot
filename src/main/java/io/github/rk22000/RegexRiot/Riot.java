package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.RiotTokens.EMPTY;

public class Riot {
    static RiotString riot(String seed, boolean isUnitChain) {
        return new ChildRiotString(new BasicRiotString(seed, isUnitChain));
    }
    public static RiotString riot(String seed) {
        return new ChildRiotString(new BasicRiotString(seed));
    }
    public static RiotString riot() {
        return EMPTY;
    }
    public static RiotString riot(SimpleRiotString seed) {
        return new ChildRiotString(seed);
    }
}
