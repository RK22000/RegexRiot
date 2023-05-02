package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

/**
 * An interface to determine different amounts of instances that a certain sub-expression can have within an overall regex instance.
 */
public interface RiotQuantifiers {
    /**
     * A method to make a regex subexpression entirely optional, where it is present 0-1 times when parsing.
     * 
     * @param ritex The regex expression which will be modified
     * @return  The newly edited regex expression
     */
    static RiotString oneOrNone(RiotString ritex) {
        return ritex.wholeThingOptional();
    }

    /**
     * A method to make a new regex subexpression from a String base, marked as optional such that it is present 0-1 times when parsing.
     * 
     * @param expression The string with which to make optional using regex conventions
     * @return  The newly created regex expression
     */
    static RiotString oneOrNone(String expression) {
        return oneOrNone(riot(expression));
    }

    /**
     * A method to make a regex subexpression able to match zero or more times when parsing.
     * 
     * @param ritex The regex subexpression to be modified
     * @return  The newly edited regex expression
     */
    static RiotString zeroOrMore(RiotString ritex) {
        if (ritex.isNotUnitChain())
            ritex = ritex.wholeThingGrouped();
        return ritex.and("*");
    }

    /**
     * A method to make a new regex subexpression from a String base, where it is able to match zero or more times when parsing.
     * 
     * @param expression    The string with which to make optional using regex conventions
     * @return  The newly created regex expression
     */
    static RiotString zeroOrMore(String expression) {
        return zeroOrMore(riot(expression));
    }

    /**
     * A method to make a regex subexpression able to match at least once when parsing.
     * 
     * @param ritex The regex subexpression to be modified
     * @return  The newly edited regex expression
     */
    static RiotString oneOrMore(RiotString ritex) {
        if (ritex.isNotUnitChain())
            ritex = ritex.wholeThingGrouped();
        return ritex.and("+");
    }

    /**
     * A method to make a new regex subexpression from a String base, where it is able to match at least once when parsing.
     * 
     * @param expression    The String with which to make a regex qualifier
     * @return  The newly created regex expression
     */
    static RiotString oneOrMore(String expression) {
        return oneOrMore(riot(expression));
    }

}
