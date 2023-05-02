package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

/**
 * An interface to make a set of Riot expressions.
 */
public interface RiotSet {
    static RiotSet riotSet(RiotString initialElement) {
        return new BasicRiotSet(initialElement);
    }

    /**
     * A method to make a union of the data present within two RiotSet objects, no
     * default implemenation provided.
     * 
     * @param oSet The other set with which to combine the current RiotSet
     * @return A new RiotSet with combined information from both RiotSets involved
     */
    RiotSet union(RiotSet oSet);

    /**
     * A method to convert a RiotSet into a RiotString, no default implementation
     * provided
     * 
     * @return The RiotString that is output from the RiotSet being built
     */
    RiotString toRiotString();

    /**
     * A method to include a set of characters in a Riot expression
     * 
     * @param chars A char array of the possible characters which can be parsed
     * @return A RiotSet object that contains the specified characters within a
     *         RiotString object
     */
    static RiotSet charsIn(char[] chars) {
        return riotSet(riot(new String(chars)));
    }

    /**
     * A utility class that allows for a range of characters to be specified,
     * inclusive of specific digits.
     */
    class RiotCharRange {
        private final char startChar; // Utility variable to keep track of the start character in the range

        /**
         * Method to create a char range and set the start char as specified with the
         * parameter.
         * 
         * @param inclusiveStartChar The inclusive char to set as a starting character
         *                           for the range
         */
        RiotCharRange(char inclusiveStartChar) {
            startChar = inclusiveStartChar;
        }

        /**
         * Method to define char range by setting the end char as specified with the
         * parameter.
         * Makes the assumption that the start char has already been defined.
         * 
         * @param inclusiveEndChar The inclusive char to set as an ending character for
         *                         the range
         * @return The new RiotSet object which includes the newly defined character
         *         range
         */
        public RiotSet through(char inclusiveEndChar) {
            return riotSet(riot(startChar + "-" + inclusiveEndChar, true));
        }
    }

    /**
     * Static implementation to create new RiotCharRange without need for a Riot
     * object of some kind.
     * 
     * @param inclusiveStartChar The inclusive char to set as a starting character
     *                           for the range
     * @return The newly created RiotCharRange object
     */
    static RiotCharRange chars(char inclusiveStartChar) {
        return new RiotCharRange(inclusiveStartChar);
    }

}

/**
 * Class that provides implementations built upon those defined by RiotSet
 * interface.
 * 
 */
class BasicRiotSet implements RiotSet {
    RiotString setString; // The string that contains the regex being modified.

    /**
     * Constructor for RiotSet.
     * 
     * @param initialElement The RiotString to use as the data to modify/append to
     */
    BasicRiotSet(RiotString initialElement) {
        setString = initialElement;
    }

    /**
     * toString converts the Riot Regex into a String Regex expression
     * 
     * @return The String Regex Expression built by the Riot Regex object
     */
    @Override
    public String toString() {
        return setString.toString();
    }

    /**
     * A method to make a union of the data present within two RiotSet objects, no
     * default implemenation provided.
     * 
     * @param oSet The other set with which to combine the current RiotSet
     * @return A new RiotSet with combined information from both RiotSets involved
     */
    @Override
    public RiotSet union(RiotSet oSet) {
        return new BasicRiotSet(
                setString.and(oSet.toString()));
    }

    /**
     * A method to convert a RiotSet into a RiotString, no default implementation
     * provided
     * 
     * @return The RiotString that is output from the RiotSet being built
     */
    @Override
    public RiotString toRiotString() {
        return riot(
                "[" + setString + "]",
                true);
    }
}
