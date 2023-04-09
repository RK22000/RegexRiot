package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.BasicRiotString.emptyBasicRiotString;
import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotSet.chars;
import static io.github.rk22000.RegexRiot.RiotSet.riotSet;

public class RiotTokens {
    public static final RiotString
            DIGIT   = riot("\\d", true),
            DOT     = riot("\\.", true),
            ANY_CHAR    = riot(".", true),
            WORD_CHAR   = riot("\\w", true),
            OPEN_BRACKET    = riot("\\(", true),
            CLOSE_BRACKET   = riot("\\)", true),
            LINE_START  = riot("^", false),
            Line_END    = riot("$", false),
            EMPTY   = riot(emptyBasicRiotString()),
            HEX = riotSet()
                    .include(DIGIT)
                    .include(chars('a').through('f'))
                    .include(chars('A').through('F')),
            HEX_UPPER = riotSet()
                    .include(DIGIT)
                    .include(chars('A').through('F')),
            HEX_LOWER = riotSet()
                    .include(DIGIT)
                    .include(chars('a').through('f'));
    public static final int
            UNLIMITED=-1;
}
