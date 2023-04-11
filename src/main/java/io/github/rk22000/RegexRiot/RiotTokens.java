package io.github.rk22000.RegexRiot;

import java.util.List;

import static io.github.rk22000.RegexRiot.RiotStringImplementations.newLazyRiot;

public interface RiotTokens {
    public static final RiotString
            DIGIT   = newLazyRiot("\\d", true),
            DOT     = newLazyRiot("\\.", true),
            ANY_CHAR    = newLazyRiot("."),
            WORD_CHAR   = newLazyRiot("\\w", true),
            OPEN_BRACKET    = newLazyRiot("\\(", true),
            CLOSE_BRACKET   = newLazyRiot("\\)", true),
            LINE_START  = newLazyRiot("^", false),
            Line_END    = newLazyRiot("$", false);

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
