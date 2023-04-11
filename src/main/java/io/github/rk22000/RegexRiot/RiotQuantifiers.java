package io.github.rk22000.RegexRiot;


import java.util.function.Function;

import static io.github.rk22000.RegexRiot.RiotString.lazyRiot;

public class RiotQuantifiers {
    public static RiotString oneOrNone(RiotString ritex) {
        return ritex.wholeThingOptional();
    }
    public static RiotString oneOrNone(String expression) {
        return oneOrNone(lazyRiot(expression));
    }
    public static RiotString zeroOrMore(RiotString ritex) {
        return ritex.wholeZeroOrMoreTimes();
    }
    public static RiotString zeroOrMore(String expression) {
        return zeroOrMore(lazyRiot(expression));
    }
    public static RiotString oneOrMore(RiotString ritex) {
        return ritex.wholeOnceOrMoreTimes();
    }
    public static RiotString oneOrMore(String expression) {
        return oneOrMore(lazyRiot(expression));
    }
}
