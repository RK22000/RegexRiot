package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

/**
 * Inferface for grouping RiotString objects together to make one large master expression.
 */
public interface RiotGroupings {
    /**
     * Default RiotString grouping function implementation.
     * 
     * @param groupNo The group number which the expression belongs to
     * @return Returns a Riot String that contains two backslashes and the group
     *         number
     */
    static RiotString group(int groupNo) {
        return riot("\\" + groupNo, true);
    }
}
