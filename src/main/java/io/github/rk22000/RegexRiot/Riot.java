package io.github.rk22000.RegexRiot;

public class Riot {
    public static RiotString riot(String seed) {
        return RiotString.lazyRiot(seed);//new ChildRiotString(new BasicRiotString(seed));
    }
    public static RiotString riot(RiotString seed) {
        return riot("").and(seed);
    }
    public static <T extends RiotString.RiotStringable> RiotString riot(T seed) {
        return seed.toRiotString();
    }
}
