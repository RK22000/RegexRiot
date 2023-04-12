package io.github.rk22000.RegexRiotTest;

import io.github.rk22000.RegexRiot.RiotString;
import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotGroupings.group;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.zeroOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.exclusiveRiotSetOf;
import static io.github.rk22000.RegexRiot.RiotSet.inclusiveRiotSetOf;
import static io.github.rk22000.RegexRiot.RiotTokens.*;

/**
 * This test class is to test if RegexRiot is good enough to generate answers for the exercises on
 * <a href="http://regextutorials.com/index.html">Regex Tutorials</a>
 */
public class TestOnRegexTutorials {
    private RiotString ritex;
    private String answer;
    private void check() {
        assert ritex.toString().equals(answer): "Expected: "+answer+"\n" +
                "Actual:   "+ritex;
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Floating%20point%20numbers">
     *     Floating Point Numbers
     * </a>
     * Match numbers containing floating point. Skip those that don't.
     */
    @Test
    void EX1_FloatingPointNumbers() {
        answer = "\\d+\\.\\d+";
        ritex = oneOrMore(DIGIT).and(DOT).and(oneOrMore(DIGIT));
        System.out.println(ritex);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Years%20before%201990">
     *     Years before 1990
     * </a>
     * Match titles of all films produced before 1990.
     * <p/>
     * 1 The Shawshank Redemption (1994)
     * 2 The Godfather (1972)
     * 3 The Godfather: Part II (1974)
     * 4 Pulp Fiction (1994)
     * 5 The Good, the Bad and the Ugly (1966)
     * 6 The Dark Knight (2008)
     * 7 12 Angry Men (1957)
     * 8 Schindler's List (1993)
     * 9 The Lord of the Rings: The Return of the King (2003)
     * 10 Fight Club (1999)
     */
    @Test
    void EX2_filmsBefore1990() {
        answer = "^.+\\((19[0-8]\\d|\\d{3}|\\d{2}|\\d)\\)";
        ritex = LINE_START.and(oneOrMore(ANY_CHAR))
                .and(OPEN_BRACKET)
                .and(
                        riot("19").and(
                                inclusiveRiotSetOf().chars('0').through('8')
                        ).and(DIGIT).or(
                                DIGIT.times(3)
                        ).or(
                                DIGIT.times(2)
                        ).or(
                                DIGIT
                        ).wholeThingGrouped()
                )
                .and(CLOSE_BRACKET);
        System.out.println(ritex);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Hexadecimal%20colors">
     *     Hexadecimal colors
     * </a>
     * Match 24-bit hexadecimal colors. Skip 12 bit colors.
     * <p/>
     * 24 bit:
     * AliceBlue #F0F8FF
     * AntiqueWhite #FAEBD7
     * Aqua #00FFFF
     * Aquamarine #7FFFD4
     * Azure #F0FFFF
     * 12 bit:
     * White #FFF
     * Red #F00
     * Green #0F0
     * Blue #00F
     */
    @Test
    void EX3_hexadecimalColors() {
        ritex = riot("#")
                .and(
                        DIGIT.or(
                                inclusiveRiotSetOf().chars('A').through('F')
                        )
                ).times(6);
        // TODO: Default grouping should be grouping without remembering like #(?:\d|[A-F]){6} instead of #(\d|[A-F]){6}
        System.out.println(ritex);
        answer = "#(?:\\d|[A-F]){6}";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Grayscale%20colors">
     *     Grayscale colors
     * </a>
     * In this exercise you need to match 12 and 24 bit colors whose red, green and blue components are equal.
     * Colors start with a '#'.
     * <p/>
     * 24 bit:
     * Alice Blue #F0F8FF
     * Black #000000
     * Antique White #FAEBD7
     * Dark Grey #a9a9a9
     * Aqua #00FFFF
     * Azure #F0FFFF
     * Battleship grey #848484
     * 12 bit:
     * White #FFF
     * Red #F00
     * Green #0F0
     * Black #000
     *
     */
    @Test
    void EX4_greyScaleColors() {
        answer = "#((?:\\d|[A-F]|[a-f]){1,2})\\1{2}";
        ritex = riot("#")
                .and(
                        DIGIT.or(
                                inclusiveRiotSetOf().chars('A').through('F')
                        ).or(
                                inclusiveRiotSetOf().chars('a').through('f')
                        )
                ).times(1, 2)
                .grouped()
                .and(group(1))
                .times(2);
        System.out.println(ritex);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Too%20long%20lines">
     *     Too Long Lines
     * </a>
     * Match lines that are more than 30 characters long.
     * This line is way too loooooooooooong.
     * This one is fine.
     * This one is fine too.
     * This line is also too long................
     *
     */
    @Test
    void EX5_tooLongLines() {
        /*
        ANY_CHAR.times(30).and(oneOrMore(ANY_CHAR)
        ANY_CHAR.times(31, infinity)
         */
        answer = ".{30}.+";
        ritex = ANY_CHAR.times(30).and(oneOrMore(ANY_CHAR));
        check();
        answer = ".{31,}";
        ritex = ANY_CHAR.times(31, UNLIMITED);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Remove%20repeating%20words">
     *     Remove Repeat Words
     * </a>
     * Some words in this text are accidentally repeated. Remove the doubles.
     * <br>
     * It was a chilly November afternoon. I had just just consummated an unusually hearty dinner, of which the dyspeptic truffe formed not the least important item, and was sitting alone in the dining-room dining-room, with my feet upon the the fender, and at my elbow a small table which I had rolled up to the fire, and upon which were some apologies for dessert, with some miscellaneous bottles bottles of wine, spirit and liqueur. In the morning I had had been reading Glover's "Leonidas", Wilkie's Wilkie's "Epigoniad", Lamartine's "Pilgrimage", Barlow's "Columbiad", Tuckermann's "Sicily", and Griswold's "Curiosities" ; I am willing to confess, therefore, that I now felt a little stupid.
     */
    @Test
    void EX6_removeRepeatWords() {
        assert false: "Nope, Just Nope. I've got no clue for now how to test this. ATB Future me!";
        /*
        Replace regex:( [^ ]+)\1
        with         :$1
         */
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Match%20HTML%20tags">
     *     Match HTML Tags
     * </a>
     * It is a common wisdom that regular expressions are not a good way of dealing with HTML, and there are perfectly good reasons for this. But in this excercise you only need to match simple HTML tags.
     * <br>
     *
     * <!DOCTYPE html>
     * <html>
     * <head>
     * <title>This is a title</title>
     * </head>
     * <body>
     * <p>Hello world!</p>
     * </body>
     * </html>
     */
    @Test
    void EX7_matchHTML() {
        ritex = riot("<").and(
                oneOrMore(exclusiveRiotSetOf().chars(">"))// riotSet().exclude(">"))
        ).and(">");
        answer = "<[^>]+>";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Cut%20numbers%20to%20two%20digits%20after%20floating%20point">
     *     Cut numbers to two digits after floating point
     * </a>
     * Leave only two numbers after the floating point in every number
     * Input text:
     * 1 Euro = 1.351299 US Dollar
     * British Pound = 1.614873 US Dollar
     * Australian Dollar = 0.916063 US Dollar
     * Canadian Dollar = 0.947400 US Dollar
     * Emirati Dirham = 0.272257 US Dollar
     * Swiss Franc = 1.096267 US Dollar
     * Chinese Yuan = 0.164114 US Dollar
     * Malaysian Ringgit = 0.310681 US Dollar
     * New Zealand Dollar = 0.819950 US Dollar
     * <br>
     * replace = (\d+\.\d{0,2})\d*
     * with = $1
     */
    @Test
    void EX8_cutNumbersTwoDecimal() {
        assert false: "Nope. Not yet";
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Match%20lowercase%20function%20declarations">
     *     Match lowercase function declarations
     * </a>
     * Match all function declarations that are not uppercase
     * <br>
     * function foo() {return bar();}
     * function Foo() {return Bar();}
     * function Baz(x) {return function(y){return x+y;}}
     * function bazEx(x) {return function(y, z){return x+y+z;}}
     * function bar() {return 0;}
     * function Bar() {return 1;}
     */
    @Test
    void EX10_lowercaseFunctionDeclaration() {
        ritex = riot("function ").and(
                inclusiveRiotSetOf().chars('a').through('z')
//                riotInclude(chars('a').through('z'))
//                        riotSet().include(chars('a').through('z')).toRiotString()
                ).and(oneOrMore(WORD_CHAR))
                .and(OPEN_BRACKET)
                .and(zeroOrMore(exclusiveRiotSetOf().chars(CLOSE_BRACKET)))//riotExclude(CLOSE_BRACKET)))//riotSet().exclude(CLOSE_BRACKET)))
                .and(CLOSE_BRACKET);
        answer = "function [a-z]\\w+\\([^\\)]*\\)";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Validate%2024h%20time%20format">
     *     Match valid time in 24h format
     * </a>
     * Match valid time in 24h format
     * Valid and invalid time:
     * 00:15
     * 07:40
     * 08:61
     * 09:59
     * 13:00
     * 14:7
     * 20:20
     * 23:61
     * 24:15
     */
    @Test
    void EX12_validate24hFormat() {
        ritex =
                riot(
                        // Hours
                        inclusiveRiotSetOf().chars("01").andThen(DIGIT).or(
                                riot("2").and(inclusiveRiotSetOf().chars("0123"))
                        )
                ).grouped()
                        .and(":")
                        .and(
                                // Minutes
                                inclusiveRiotSetOf().chars('0').through('5').andThen(DIGIT)
                        );
        answer = "([01]\\d|2[0123]):[0-5]\\d";
        check();
        ritex = inclusiveRiotSetOf().chars("01").andThen(DIGIT).or(
                riot("2").and(inclusiveRiotSetOf().chars("0123"))
        ).wholeThingGrouped()
                .and(":")
                .and(inclusiveRiotSetOf().chars("012345").andThen(DIGIT));
        answer = "([01]\\d|2[0123]):[012345]\\d";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?IPv6%20adresses">
     *     IPv6 adresses
     * </a>
     * Match IPv6 addresses. An IPv6 address consists of 8 colon-delimited blocks of 4 hexadecimal digits. Blocks of all zeroes can be omitted. Leading zeroes in a block can be omitted too.
     * <br>
     * Initial address: 2001:0db8:0000:0000:0000:ff00:0042:8329
     * After removing all leading zeroes: 2001:db8:0:0:0:ff00:42:8329
     * After omitting consecutive sections of zeroes: 2001:db8::ff00:42:8329
     * Another example: 2607:f0d0:1002:0051:0000:0000:0000:0004
     * After removing all leading zeroes: 2607:f0d0:1002:51:0:0:0:4
     * After omitting consecutive sections of zeroes: 2607:f0d0:1002:51::4
     */
    @Test
    void EX16_IPV6address() {
        ritex = HEX_LOWER.toRiotString().times(1, 4)
                .and(
                        riot(":").and(HEX_LOWER.toRiotString().times(0, 4))
                                .wholeTimes(1, 6)
                )
                .and(":")
                .and(HEX_LOWER.toRiotString().times(1, 4));
        answer = "[\\da-f]{1,4}(?::[\\da-f]{0,4}){1,6}:[\\da-f]{1,4}";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Validate%2032%20or%2024%20bit%20hexadecimal%20colors">
     *     Validate 32 or 24 bit hexadecimal colors
     * </a>
     * Highlight valid 24 and 32 bit hexadecimal colors. Colors start with #
     * <br>
     * White: #ffffff, #ffffffff
     * Black:#000000 #000000ff
     * Semitrnasparent green: #00ff0088
     * Nonhexadecimal: #00ffhh #agaeffe0
     * Wrong bytes count:#00ff00f #fffff #888888fff
     */
    @Test
    void EX17_valid32_24HexColors() {
        ritex = riot("#")
                .and(HEX).times(6)
                .and(HEX).times(2).optionally()
                .followedBy(HEX.complement());
        answer = "#[\\da-fA-F]{6}(?:[\\da-fA-F]{2})?(?=[^\\da-fA-F])";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Extract%20query%20string%20from%20URL">
     *     Extract query string from URL
     * </a>
     * Match the URL parameters in the form param : value
     * <br>
     * http://www.learnregexp.com?excercise=extract query from URL
     * https://www.google.com/search?q=regexp
     * http://pitchimprover.com/index.php?type=Perfect
     * http://www.learnregexp.com?excercise=extract-host-from-URL
     */
    @Test
    void EX19_extractURL_Query() {
        ritex = riot(QUESTION_MARK)
                .and(oneOrMore(WORD_CHAR))
                .and("=")
                .and(oneOrMore(ANY_CHAR));
        answer = "\\?\\w+=.+";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Extract%20host%20from%20URL">
     *     Extract host from URL
     * </a>
     * Extract the host from URL
     * <br>
     * http://www.learnregexp.com?excercise=extract query from URL
     * https://www.google.com/search?q=regexp
     * http://pitchimprover.com/index.php?type=Perfect
     * http://www.learnregexp.com?excercise=extract host from URL
     */
    @Test
    void EX20_extractURL_host() {
        ritex = riot("http")
                .and("://")
                .and(oneOrMore(exclusiveRiotSetOf().chars(QUESTION_MARK).chars("/")));
        answer = "http://[^\\?/]+";
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Strings%20not%20containing%20word">
     *     Strings not containing word
     * </a>
     * Match all recipes that do not contain the word 'chocolate' (recipes are separated by newline)
     * <br>
     * Cake 1: sugar, flour, cocoa powder, baking powder, baking soda, salt, eggs, milk, vegetable oil, vanilla extract, chocolate chips
     * Cake 2: cream cheese, sugar, vanilla extract, crescent rolls, cinnamon, butter, honey
     * Cake 3: dark chocolate cake mix, instant chocolate pudding mix, sour cream, eggs, vegetable oil, coffee liqueur
     * Cake 4: flour, baking powder, salt, cinnamon, butter, sugar, egg, vanilla extract, milk, chopped walnuts
     * Cake 5: gingersnap cookies, chopped pecans, butter, cream cheese, sugar, vanilla extract, eggs, canned pumpkin, cinnamon
     * Cake 6: flour, baking soda, sea salt, butter, white sugar, brown sugar, eggs, vanilla extract, chocolate chips, canola oil
     * Cake 7: wafers, cream cheese, sugar, eggs, vanilla extract, cherry pie filling
     */
    @Test
    void EX21_stringWithNoChocolate() {
        ritex = LINE_START
                .followedByNot(oneOrMore(ANY_CHAR).and("chocolate"))
                .and(ANY_CHAR).onceOrMoreTimes();
        answer = "^(?!.+chocolate).+";
        check();
    }
}

