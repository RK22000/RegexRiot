package io.github.rk22000.RegexRiot;

import java.util.function.Function;


public class LazyPair {
    LazyPair p1, p2;
    Function<LazyPair, Function<LazyPair, String>> mixer;
    String eval() {
        if (mixer == null) return "";
        return mixer.apply(p1).apply(p2);
    }
    public LazyPair(String val) {
        mixer = v1 -> (v2 -> val);
    }
    public LazyPair(LazyPair v1, LazyPair v2, Function<LazyPair, Function<LazyPair, String>> mix) {
        p1 = v1;
        p2 = v2;
        mixer = mix;
    }
    public <T extends LazyPair> LazyPair and(T other) {
        return new LazyPair(
                this,
                other,
                v1 -> (v2 -> v1.toString() + v2.toString())
        );
    }
    public <T> LazyPair and(T other) {
        return and(new LazyPair(other.toString()));
    }

    @Override
    public String toString() {
        return eval();
    }
}
