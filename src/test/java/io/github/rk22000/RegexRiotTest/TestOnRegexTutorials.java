package io.github.rk22000.RegexRiotTest;

import io.github.rk22000.RegexRiot.RiotString;
import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotGroupings.group;
import static io.github.rk22000.RegexRiot.RiotGroupings.replacementGroup;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.zeroOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.exclude;
import static io.github.rk22000.RegexRiot.RiotSet.include;
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
                                include().chars('0').through('8')
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
                                include().chars('A').through('F')
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
                                include().chars('A').through('F')
                        ).or(
                                include().chars('a').through('f')
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
        var pattern = riot("\\b").and(oneOrMore(exclude().chars(" "))).wholeThingGrouped().and(" ").and(group(1)).compile();
        var raw = "It was a chilly November afternoon. I had just just consummated an unusually hearty dinner, of which the dyspeptic truffe formed not the least important item, and was sitting alone in the dining-room dining-room, with my feet upon the the fender, and at my elbow a small table which I had rolled up to the fire, and upon which were some apologies for dessert, with some miscellaneous bottles bottles of wine, spirit and liqueur. In the morning I had had been reading Glover's \"Leonidas\", Wilkie's Wilkie's \"Epigoniad\", Lamartine's \"Pilgrimage\", Barlow's \"Columbiad\", Tuckermann's \"Sicily\", and Griswold's \"Curiosities\" ; I am willing to confess, therefore, that I now felt a little stupid.";
        var correct = "It was a chilly November afternoon. I had just consummated an unusually hearty dinner, of which the dyspeptic truffe formed not the least important item, and was sitting alone in the dining-room, with my feet upon the fender, and at my elbow a small table which I had rolled up to the fire, and upon which were some apologies for dessert, with some miscellaneous bottles of wine, spirit and liqueur. In the morning I had been reading Glover's \"Leonidas\", Wilkie's \"Epigoniad\", Lamartine's \"Pilgrimage\", Barlow's \"Columbiad\", Tuckermann's \"Sicily\", and Griswold's \"Curiosities\" ; I am willing to confess, therefore, that I now felt a little stupid.";
        var replaced = pattern.matcher(raw).replaceAll(replacementGroup(1).toString());
        System.err.println("Expected: "+correct);
        System.err.println("Actual  : "+replaced);
        System.err.println("RAW     : "+raw);
        System.err.println(pattern.pattern());
        assert replaced.equals(correct);
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
                oneOrMore(exclude().chars(">"))// riotSet().exclude(">"))
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
        var raw = """
                1 Euro = 1.351299 US Dollar
                British Pound = 1.614873 US Dollar
                Australian Dollar = 0.916063 US Dollar
                Canadian Dollar = 0.947400 US Dollar
                Emirati Dirham = 0.272257 US Dollar
                Swiss Franc = 1.096267 US Dollar
                Chinese Yuan = 0.164114 US Dollar
                Malaysian Ringgit = 0.310681 US Dollar
                New Zealand Dollar = 0.819950 US Dollar""";
        var correct = """
                1 Euro = 1.35 US Dollar
                British Pound = 1.61 US Dollar
                Australian Dollar = 0.91 US Dollar
                Canadian Dollar = 0.94 US Dollar
                Emirati Dirham = 0.27 US Dollar
                Swiss Franc = 1.09 US Dollar
                Chinese Yuan = 0.16 US Dollar
                Malaysian Ringgit = 0.31 US Dollar
                New Zealand Dollar = 0.81 US Dollar""";
        var replaced = oneOrMore(DIGIT).and(DOT).and(DIGIT).times(1,2).wholeThingGrouped().and(oneOrMore(DIGIT))
                .compile().matcher(raw).replaceAll(replacementGroup(1).toString());
        assert replaced.equals(correct);
    }

    @Test
    void EX9_digitCommaFormatting() {
        var raw = """
                Ten Countries with the Highest Population:
                1 China 1361220000
                2 India 1236800000
                3 United States 317121000
                4 Indonesia 237641326
                5 Brazil 201032714
                6 Pakistan 184872000
                7 Nigeria 173615000
                8 Bangladesh 152518015
                9 Russia 143600000
                10 Japan 127290000""";
        var correct = """
                Ten Countries with the Highest Population:
                1 China 1,361,220,000
                2 India 1,236,800,000
                3 United States 317,121,000
                4 Indonesia 237,641,326
                5 Brazil 201,032,714
                6 Pakistan 184,872,000
                7 Nigeria 173,615,000
                8 Bangladesh 152,518,015
                9 Russia 143,600,000
                10 Japan 127,290,000""";
        var replaced = DIGIT.times(1,3).grouped().followedBy(oneOrMore(DIGIT.times(3)).and(BOUNDARY))
                .compile().matcher(raw).replaceAll(replacementGroup(1).and(",").toString());
        assert replaced.equals(correct);
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
                include().chars('a').through('z')
//                riotInclude(chars('a').through('z'))
//                        riotSet().include(chars('a').through('z')).toRiotString()
                ).and(oneOrMore(WORD_CHAR))
                .and(OPEN_BRACKET)
                .and(zeroOrMore(exclude().chars(CLOSE_BRACKET)))//riotExclude(CLOSE_BRACKET)))//riotSet().exclude(CLOSE_BRACKET)))
                .and(CLOSE_BRACKET);
        answer = "function [a-z]\\w+\\([^\\)]*\\)";
        check();
    }

    @Test
    void EX11_changeDateFormat() {
        var raw = """
                Robert Boyle 1627-01-25 — 1691-12-31
                Antoine Lavoisier 1743-08-26 — 1794-05-08
                Michael Faraday 1791-09-22 — 1867-06-25
                Louis Pasteur 1822-12-27 — 1895-09-28
                Alfred Nobel 1833-10-21 — 1896-12-10
                Dmitri Mendeleev 1834-02-08 — 1907-02-02
                Marie Curie 1867-11-07 — 1934-07-04
                Linus Pauling 1901-02-28 — 1994-08-19""";
        var correct = """
                Robert Boyle 25.01.1627 — 31.12.1691
                Antoine Lavoisier 26.08.1743 — 08.05.1794
                Michael Faraday 22.09.1791 — 25.06.1867
                Louis Pasteur 27.12.1822 — 28.09.1895
                Alfred Nobel 21.10.1833 — 10.12.1896
                Dmitri Mendeleev 08.02.1834 — 02.02.1907
                Marie Curie 07.11.1867 — 04.07.1934
                Linus Pauling 28.02.1901 — 19.08.1994""";
        var replaced = DIGIT.times(4).grouped().and("-")
                .and(DIGIT).times(2).grouped().and("-")
                .and(DIGIT).times(2).grouped()

                .compile().matcher(raw)
                .replaceAll(
                        replacementGroup(3).and(DOT)
                                .and(replacementGroup(2)).and(DOT)
                                .and(replacementGroup(1)).toString()
                );
        assert replaced.equals(correct);
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
                        include().chars("01").andThen(DIGIT).or(
                                riot("2").and(include().chars("0123"))
                        )
                ).grouped()
                        .and(":")
                        .and(
                                // Minutes
                                include().chars('0').through('5').andThen(DIGIT)
                        );
        answer = "([01]\\d|2[0123]):[0-5]\\d";
        check();
        ritex = include().chars("01").andThen(DIGIT).or(
                riot("2").and(include().chars("0123"))
        ).wholeThingGrouped()
                .and(":")
                .and(include().chars("012345").andThen(DIGIT));
        answer = "([01]\\d|2[0123]):[012345]\\d";
        check();
    }
    @Test
    void EX13_validate12hFormat() {
        ritex =
                riot(BOUNDARY)
                        .and(riot("0").optionally().and(DIGIT).or("1").and(include().chars("012"))) // Hours
                        .grouped()
                        .and(":")
                        .and(include().chars('0').through('5').andThen(DIGIT)) // Minutes
                        .and(oneOrMore(SPACE))
                        .and(riot("AM").or("PM"))
                        .grouped();
        answer = "\\b(0?\\d|1[012]):[0-5]\\d\\s+(AM|PM)";
        check();
    }

    @Test
    void EX14_pascalToCStyleParameters() {
        var raw = """
                void Foo(int x; float y; char z)
                {
                write(x, y, z);
                }
                                
                int Avg(List<int> l; int count)
                {
                return Sum(l, count)/count;
                }""";
        var correct = """
                void Foo(int x, float y, char z)
                {
                write(x, y, z);
                }
                                
                int Avg(List<int> l, int count)
                {
                return Sum(l, count)/count;
                }""";
        ritex = riot(";").followedBy(oneOrMore(exclude().chars(OPEN_BRACKET)).and(CLOSE_BRACKET));
        var replaced = ritex
                .compile().matcher(raw).replaceAll(",");
        System.err.println("----");
        System.err.println(replaced);
        System.err.println("-----");
        System.err.println(correct);
        System.err.println("------");
        System.err.println(ritex);
        assert replaced.equals(correct);
    }

    @Test
    void EX15_variableInitializationStyle() {
        var raw = """
                function init() {
                var foo = new Foo();
                var bar = new Bar(foo, true);
                var foos = new List<Foo>(3);
                var baz = new Baz(random());
                }
                """;
        var correct = """
                function init() {
                Foo foo();
                Bar bar(foo, true);
                List<Foo> foos(3);
                Baz baz(random());
                }
                """;
        ritex = riot(BOUNDARY+"var"+SPACES)
                .and(oneOrMore(WORD_CHAR)).groupedAs("name")
                .and(SPACES+"="+SPACES+"new"+SPACES)
                .and(oneOrMore(exclude().chars(OPEN_BRACKET))).groupedAs("type");
        var replaced = ritex
                .compile().matcher(raw).replaceAll(replacementGroup("type")+" "+replacementGroup("name"));
        System.err.println(replaced);
        assert replaced.equals(correct);
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

    @Test
    void EX18_operatorReplacedByFunc() {
        // Replace a + b with Add(a,b)
        var raw = """
                int c = a+b;
                var average = (a1 + a2)/2
                sum(foo, bar, x) = foo(x) + bar(x)
                var dotProduct(v1, v2) = v1.x*v2.x + v1.y*v2.y
                """;
        var correct = """
                int c = a+b;
                var average = (Add(a1, a2))/2
                sum(foo, bar, x) = Add(foo(x), bar(x))
                var dotProduct(v1, v2) = Add(v1.x*v2.x, v1.y*v2.y)
                """;
        // (\w[a-z0-9()*.]+)\s?\+\s?(\w[a-z0-9()*.]+)
        var argument = WORD_CHAR.and(include().chars(WORD_CHAR).chars("()*.")).zeroOrMoreTimes();
        ritex =
                riot(argument).as("a")
                        .and(SPACES).and(PLUS).and(SPACES)
                        .and(argument).as("b");

        var replaced = ritex
                        .compile().matcher(raw).replaceAll("Add("+replacementGroup("a")+", "+replacementGroup("b")+")");
        System.err.println(correct);
        System.err.println(replaced);
        System.err.println(ritex);
        assert replaced.equals(correct);
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
                .and(oneOrMore(exclude().chars(QUESTION_MARK).chars("/")));
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

