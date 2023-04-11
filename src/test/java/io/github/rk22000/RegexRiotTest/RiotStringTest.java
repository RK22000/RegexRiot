package io.github.rk22000.RegexRiotTest;

import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.RiotString.lazyRiot;

class RiotStringTest {
    @Test
    void and() {
        var rs = lazyRiot("Hello");
        assert rs.toString().equals("Hello");
        assert rs.and(" World").toString().equals("Hello World");
        assert rs.and(lazyRiot(" World")).toString().equals("Hello World");
    }

    @Test
    void or() {
        assert lazyRiot("Hello ")
                .and("World")
                .or("There")
                .or(lazyRiot("General"))
                .toString().equals("Hello World|There|General");
    }
    <T> void check(T a, String b) {
        assert a.toString().equals(b):"Expected "+b+
                "\nActual "+a;
    }
    @Test
    void wholeThingGroupedAndForgotten() {
        var rs = lazyRiot("Hello").and(" World").wholeThingGroupedAndForgotten();
        check(rs.toString(), "(?:Hello World)");
    }
    @Test
    void groupedAndForgotten() {
        check(
                lazyRiot("Hello ").and("World").groupedAndForgotten().toString(),
                "Hello (?:World)"
        );
    }
    @Test
    void wholeTimes() {
        check(
                lazyRiot("a").wholeTimes(3).toString(),
                "a{3}"
        );
        check(
                lazyRiot("a").or("b").wholeTimes(5).toString(),
                "(?:a|b){5}"
        );
        check(
                lazyRiot("a").wholeTimes(3, 8).toString(),
                "a{3,8}"
        );
        check(
                lazyRiot("a").or("b").wholeTimes(5,24).toString(),
                "(?:a|b){5,24}"
        );
    }
    @Test
    void times() {
        check(
                lazyRiot("a").times(3).toString(),
                "a{3}"
        );
        check(
                lazyRiot("a").or("b").times(5).toString(),
                "a|b{5}"
        );
        check(
                lazyRiot("a").times(3,19).toString(),
                "a{3,19}"
        );
        check(
                lazyRiot("a").or("b").times(5,23).toString(),
                "a|b{5,23}"
        );

    }
    @Test
    void t1() {
        check(
                lazyRiot("a").or("b").wholeTimes(3).times(2).toString(),
                "(?:a|b){3}{2}"
        );
    }
    @Test
    void wholeThingOptional() {
        check(
                lazyRiot("a").wholeThingOptional(),
                "a?"
        );
        check(
                lazyRiot("ab").wholeThingOptional(),
                "(?:ab)?"
        );
        check(
                lazyRiot("a").and("b").wholeThingOptional(),
                "(?:ab)?"
        );
    }
    @Test
    void optionally() {
        check(
                lazyRiot("a").optionally(),
                "a?"
        );
        check(
                lazyRiot("ab").optionally(),
                "(?:ab)?"
        );
        check(
                lazyRiot("a").and("b").optionally(),
                "ab?"
        );
    }

}
//
//import org.junit.jupiter.api.Test;
//
//import static io.github.rk22000.RegexRiot.Riot.riot;
//import static io.github.rk22000.RegexRiot.RiotTokens.DIGIT;
//import static io.github.rk22000.RegexRiot.RiotTokens.WORD_CHAR;
////import static io.github.rk22000.RegexRiot.SimpleRiotTokens.DIGIT;
//
//class RiotStringTest {
//    RiotString riotex;
//    String result;
//    void check() {
//        assert result.equals(riotex.toString()): "EXPECTED: "+result+"\nACTUAL:   "+riotex;
//    }
//
//    @Test
//    void tokens() {
//        riotex = DIGIT;
//        result = "\\d";
//        check();
//        riotex = WORD_CHAR;
//        result = "\\w";
//        check();
//    }
//    @Test
//    void and() {
//        riotex = DIGIT.and(WORD_CHAR);
//        System.out.println(riotex);
//        result = "\\d\\w";
//        check();
//    }
//
//    @Test
//    void or() {
//        riotex = DIGIT.or(WORD_CHAR);
//        System.out.println(riotex);
//        result = "\\d|\\w";
//        check();
//    }
//    @Test
//    void wholeTimesRanged() {
//        riotex = DIGIT.or(WORD_CHAR).wholeTimes(1, 2);
//        result = "(\\d|\\w){1,2}";
//        System.out.println(riotex);
//        check();
//    }
//
//    @Test
//    void wholeTimes() {
//        riotex = DIGIT.and(WORD_CHAR).wholeTimes(3);
//        System.out.println(riotex);
//        result = "(\\d\\w){3}";
//        check();
//
//        riotex = DIGIT.and("HelloWorld").wholeTimes(4);
//        System.out.println(riotex);
//        result = "(\\dHelloWorld){4}";
//        check();
//    }
//
//    @Test
//    void wholeThingOptional() {
//        riotex = DIGIT.and(WORD_CHAR).wholeThingOptional();
//        System.out.println(riotex);
//        result = "(\\d\\w)?";
//        check();
//    }
//
//    @Test
//    void wholeThingGrouped() {
//        riotex = DIGIT.and(WORD_CHAR).wholeThingGrouped();
//        System.out.println(riotex);
//        result = "(\\d\\w)";
//        check();
//    }
//
//    @Test
//    void wholeThingGroupedAs() {
//        riotex = DIGIT.and(WORD_CHAR).wholeThingGroupedAs("digitNword");
//        System.out.println(riotex);
//        result = "(?<digitNword>\\d\\w)";
//        check();
//    }
//
//    @Test
//    void times() {
//        riotex = DIGIT.and(WORD_CHAR).times(3);
//        System.out.println(riotex);
//        result = "\\d\\w{3}";
//        check();
//
//        riotex = DIGIT.and("HelloWorld").times(4);
//        System.out.println(riotex);
//        result = "\\d(HelloWorld){4}";
//        check();
//    }
//
//    @Test
//    void optionally() {
//        riotex = DIGIT.and(WORD_CHAR).optionally();
//        System.out.println(riotex);
//        result = "\\d\\w?";
//        check();
//    }
//
//    @Test
//    void grouped() {
//        riotex = DIGIT.and(WORD_CHAR).grouped();
//        System.out.println(riotex);
//        result = "\\d(\\w)";
//        check();
//    }
//
//    @Test
//    void groupedAs() {
//        riotex = DIGIT.and(WORD_CHAR).groupedAs("digitNword");
//        System.out.println(riotex);
//        result = "\\d(?<digitNword>\\w)";
//        check();
//    }
//}