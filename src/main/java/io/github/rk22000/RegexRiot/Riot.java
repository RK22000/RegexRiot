package io.github.rk22000.RegexRiot;

/**
 * Riot class that contains constructors for different expressions, 
 * including child expressions that build upon previously existing RiotStrings
 */
public class Riot {
    /**
     * Utility creation method for a simple RiotString object
     * 
     * @param seed  The string base with which to build the regular expression.
     * @return  The newly created SimpleRiotString
     */
    public static SimpleRiotString create(String seed) {
        return new BasicRiotString(seed);
    }

    /**
     * Utility creation method for a more complex RiotExpression, still uses BasicRiotString() constructor.
     * The difference is made apparent in testing.
     * 
     * @param seed The string base with which to build the regular expression.
     * @return  The newly created SimpleRiotString
     */
    public static SimpleRiotString riotExpression(String seed) {
        return new BasicRiotString(seed);
    }

    /**
     * Utility creation method for a complex RiotString subgroup object which can be added to daisychain
     * 
     * @param seed  The string base with which to build the regular expression. 
     * @param isUnitChain   Boolean value to determine whether the expression is part of a daisychain.
     * @return  The newly created RiotString variable that adds to the daisychain
     */
    static RiotString riot(String seed, boolean isUnitChain) {
        return new ChildRiotString(new BasicRiotString(seed, isUnitChain));
    }

    /**
     * Utility creation method for a simpler RiotString subgroup object which is built from a String object
     * 
     * @param seed  The string base with which to build the regular expression. 
     * @return  The newly created ChildRiotString object given the base string
     */
    public static RiotString riot(String seed) {
        return new ChildRiotString(new BasicRiotString(seed));
    }

    /**
     * Utility creation method for simple RiotString subgroup object which is built from a SimpleRiotString object
     * 
     * @param seed  The SimpleRiotString base with which to build the regular expression. 
     * @return  The newly created ChildRiotString object given the base SimpleRiotString
     */
    public static RiotString riot(SimpleRiotString seed) {
        return new ChildRiotString(seed);
    }
}
