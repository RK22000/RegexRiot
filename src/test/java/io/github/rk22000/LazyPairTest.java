package io.github.rk22000;

import io.github.rk22000.RegexRiot.LazyPair;
import org.junit.jupiter.api.Test;

public class LazyPairTest {
    @Test
    void t1() {
        var a = new LazyPair("Hello");
        assert a.toString().equals("Hello");
        var b = new LazyPair(" World");
        assert b.toString().equals(" World");
        var c = a.and(b);
        assert c.toString().equals("Hello World");
        assert c.and("!").toString().equals("Hello World!");

    }
}
