package io.github.rk22000.RegexRiot;

public class Riot {
    public static RiotString riot(String seed) {
        return RiotString.lazyRiot(seed);//new ChildRiotString(new BasicRiotString(seed));
    }
}
