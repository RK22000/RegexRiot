package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.include;
import static io.github.rk22000.RegexRiot.RiotStringImplementations.newLazyRiot;

public interface RiotTokens {
    RiotString
            DIGIT   = newLazyRiot("\\d", true),
            DOT     = newLazyRiot("\\.", true),
            ANY_CHAR    = newLazyRiot("."),
            WORD_CHAR   = newLazyRiot("\\w", true),
            OPEN_BRACKET    = newLazyRiot("\\(", true),
            CLOSE_BRACKET   = newLazyRiot("\\)", true),
            LINE_START  = newLazyRiot("^", true),
            Line_END    = newLazyRiot("$", true),
            QUESTION_MARK = newLazyRiot("\\?", true),
            BOUNDARY = newLazyRiot("\\b", true),
            SPACE = newLazyRiot("\\s", true),
            SPACES = oneOrMore(SPACE),
            PLUS = newLazyRiot("\\+", true);
    RiotSet
            HEX = include()
                    .chars(DIGIT)
                    .chars('a').through('f')
                    .chars('A').through('F'),
            HEX_LOWER = include()
                    .chars(DIGIT)
                    .chars('a').through('f'),
            HEX_UPPER = include()
                    .chars(DIGIT)
                    .chars('A').through('F');
    int
            UNLIMITED=-1;

}
//    public static final RiotString
//            DIGIT   = riot("\\d", true),
//            DOT     = riot("\\.", true),
//            ANY_CHAR    = riot(".", true),
//            WORD_CHAR   = riot("\\w", true),
//            OPEN_BRACKET    = riot("\\(", true),
//            CLOSE_BRACKET   = riot("\\)", true),
//            LINE_START  = riot("^", false),
//            Line_END    = riot("$", false),
////            EMPTY   = riot(emptyBasicRiotString()),
//            HEX = riotSet()
//                    .include(DIGIT)
//                    .include(chars('a').through('f'))
//                    .include(chars('A').through('F')),
//            HEX_UPPER = riotSet()
//                    .include(DIGIT)
//                    .include(chars('A').through('F')),
//            HEX_LOWER = riotSet()
//                    .include(DIGIT)
//                    .include(chars('a').through('f'));
//    public static final int
//            UNLIMITED=-1;
//}
