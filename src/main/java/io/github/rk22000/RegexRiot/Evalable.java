package io.github.rk22000.RegexRiot;


interface Evalable<T> {
    /**
     * Evaluates all the recursive nested parts down to a simple string but does not process/format the string if its needed.
     * @return the string produced on evaluating the recursive nested part.
     */
    T eval(); // Why not just return a string
}
