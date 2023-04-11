package io.github.rk22000.RegexRiot;


import java.util.function.Function;

import static io.github.rk22000.RegexRiot.RiotString.lazyRiot;

public class RiotQuantifiers {
    public static RiotString oneOrNone(RiotString ritex) {
        return ritex.wholeThingOptional();
    }
    public static <T extends RiotString.RiotStringable>RiotString oneOrNone(T expression) {
        return oneOrNone(expression.toRiotString());
    }
    public static <T>RiotString oneOrNone(T expression) {
        return oneOrNone(lazyRiot(expression.toString()));
    }
    public static RiotString zeroOrMore(RiotString ritex) {
        return ritex.wholeZeroOrMoreTimes();
    }
    public static <T extends RiotString.RiotStringable> RiotString zeroOrMore(T expression) {
        return zeroOrMore(expression.toRiotString());
    }
    public static <T>RiotString zeroOrMore(T expression) {
        return zeroOrMore(lazyRiot(expression.toString()));
    }
    public static RiotString oneOrMore(RiotString ritex) {
        return ritex.wholeOnceOrMoreTimes();
    }
    public static <T extends RiotString.RiotStringable>RiotString oneOrMore(T expression) {
        return oneOrMore(expression.toRiotString());
    }
    public static <T>RiotString oneOrMore(T expression) {
        return oneOrMore(lazyRiot(expression.toString()));
    }
}
