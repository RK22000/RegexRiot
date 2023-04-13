package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.inSetOf;
import static io.github.rk22000.RegexRiot.RiotStringImplementations.newLazyRiot;

public interface RiotTokens {
    // TODO: Identify which tokens are save to use as arguments to RiotSet.chars() and which are not
    /**
     * This should be safe to use as argument to use as Argument to RiotSet.chars()
     */
    // TODO: create RiotSetable interface and make these tokens implement it
    RiotString
            DIGIT   = newLazyRiot("\\d", true),
            DOT     = newLazyRiot("\\.", true),
            WORD_CHAR   = newLazyRiot("\\w", true),
            OPEN_BRACE = newLazyRiot("\\(", true),
            CLOSE_BRACE = newLazyRiot("\\)", true),
            OPEN_SQ_BRACE = newLazyRiot("\\[", true),
            CLOSE_SQ_BRACE = newLazyRiot("\\]", true),
            QUESTION_MARK = newLazyRiot("\\?", true),
            BOUNDARY = newLazyRiot("\\b", true),
            SPACE = newLazyRiot("\\s", true),
            SPACES = oneOrMore(SPACE),
            PLUS = newLazyRiot("\\+", true);
    /**
     * This is not safe to use as arguments to RiotSet.chars()
     */

    RiotString
            ANY_CHAR    = newLazyRiot("."),
            LINE_START  = newLazyRiot("^", true),
            Line_END    = newLazyRiot("$", true);
    RiotSet
            HEX = inSetOf(DIGIT)
                    .andChars('a').through('f')
                    .andChars('A').through('F'),
            HEX_LOWER = inSetOf(DIGIT)
                    .andChars('a').through('f'),
            HEX_UPPER = inSetOf(DIGIT)
                    .andChars('A').through('F');
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
