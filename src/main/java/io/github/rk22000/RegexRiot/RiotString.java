package io.github.rk22000.RegexRiot;

import java.util.function.BiFunction;
import java.util.regex.Pattern;

import static io.github.rk22000.RegexRiot.RiotTokens.UNLIMITED;

public interface RiotString {
    static RiotString lazyRiot(String seed) {
        return RiotStringImplementations.newLazyRiot(seed);
    }

    interface RiotStringable {
        RiotString toRiotString();
    }

    /**
     * A method to create another RiotString of the same type(Lazy/Eager) as the current RiotString
     *
     * @param seed initial value of the RiotString
     * @return a new RiotString of the same type as the current type but with the value of seed
     */
    RiotString riotString(String seed);

    <T extends RiotString> RiotString then(T extension);

    default <T extends RiotStringable> RiotString then(T extension) {
        return then(extension.toRiotString());
    }

    default <T> RiotString then(T extension) {
        return then(riotString(extension.toString()));
    }

    <T extends RiotString> RiotString or(T extension);

    default <T extends RiotStringable> RiotString or(T extension) {
        return or(extension.toRiotString());
    }

    default <T> RiotString or(T extension) {
        return or(riotString(extension.toString()));
    }

    RiotString wholeTimes(int n);

    RiotString wholeTimes(int atleast, int atmoast);

    default RiotString wholeAtLeast(int n) {
        return wholeTimes(n, UNLIMITED);
    }

    default RiotString wholeAtMost(int n) {
        return wholeTimes(UNLIMITED, n);
    }

    RiotString times(int n);

    RiotString times(int atleast, int almost);

    default RiotString atLeast(int n) {
        return times(n, UNLIMITED);
    }

    default RiotString atMost(int n) {
        return times(UNLIMITED, n);
    }

    RiotString wholeThingGrouped();

    RiotString grouped();

    RiotString wholeThingGroupedAndForgotten();

    RiotString groupedAndForgotten();

    RiotString wholeThingGroupedAs(String name);

    default RiotString wholeAs(String name) {
        return wholeThingGroupedAs(name);
    }

    RiotString groupedAs(String name);

    default RiotString as(String name) {
        return groupedAs(name);
    }

    RiotString wholeThingOptional();

    RiotString optionally();

    RiotString wholeOnceOrMoreTimes();

    RiotString onceOrMoreTimes();

    RiotString wholeZeroOrMoreTimes();

    RiotString zeroOrMoreTimes();

    boolean isUnitChain();

    default boolean isNotUnitChain() {
        return !isUnitChain();
    }

    RiotString followedBy(RiotString extension);

    default <T extends RiotStringable> RiotString followedBy(T expression) {
        return followedBy(expression.toRiotString());
    }

    default <T> RiotString followedBy(T expression) {
        return followedBy(riotString(expression.toString()));
    }

    RiotString followedByNot(RiotString expression);

    default <T extends RiotStringable> RiotString followedByNot(T expression) {
        return followedByNot(expression.toRiotString());
    }

    default <T> RiotString followedByNot(T expression) {
        return followedByNot(riotString(expression.toString()));
    }

    default Pattern compile() {
        return Pattern.compile(toString());
    }
}

class RiotStringImplementations {
    public static RiotString newLazyRiot(String seed) {
        return new LazyRiotString(seed, seed.length() == 1);
    }

    public static RiotString newLazyRiot(String seed, Boolean unit) {
        return new LazyRiotString(seed, unit);
    }

    private static class LazyRiotString implements Evalable<RiotString>, RiotString {
        final LazyRiotString prefix, suffix;
        final BiFunction<LazyRiotString, LazyRiotString, String> combinator;
        final boolean unitChained;

        LazyRiotString(String seed, boolean unit) {
            prefix = null;
            suffix = null;
            combinator = (__, ___) -> seed;
            unitChained = unit;
        }

        LazyRiotString(
                LazyRiotString pfix,
                LazyRiotString sfix,
                BiFunction<LazyRiotString, LazyRiotString, String> extender,
                boolean unit) {
            if (pfix == null ^ sfix == null)
                throw new IllegalArgumentException("Can not have only one of prefix or suffix be null. Either both should be null or neither should be");
            prefix = pfix;
            suffix = sfix;
            combinator = extender;
            unitChained = unit;
        }

        @Override
        public RiotString eval() {
            return riotString(combinator.apply(prefix, suffix));
        }

        @Override
        public String toString() {
            if (prefix == null || suffix == null)
                return combinator.apply(null, null);
            else
                return eval().toString();
        }

        @Override
        public RiotString riotString(String seed) {
            return newLazyRiot(seed);
        }

        @Override
        public <T extends RiotString> RiotString then(T extension) {
            return new LazyRiotString(
                    this,
                    (LazyRiotString) extension,
                    (p, s) -> "" + p + s,
                    false
            );
        }

        @Override
        public <T extends RiotString> RiotString or(T extension) {
            return new LazyRiotString(
                    this,
                    (LazyRiotString) extension,
                    (p, s) -> p + "|" + s,
                    false
            );
        }

        @Override
        public RiotString wholeTimes(int n) {
            if (unitChained)
                return riotString("").then(
                        // This weird nesting is done cuz when
                        // prefix = a
                        // sufix = b
                        // operation = or then whole{3}
                        // string = (?:a|b){3}
                        // doing a new operation of just {2} should result in (?:(?:a|b){3}){2}, not (?:a|b{2}){3}

                        // this weirdness needs to be followed because regex does not interpret (?:a|b){3}{2} like `a or b 3times then that 2 times`
                        // it needs to be written as (?:(?:a|b){3}){2}

                        new LazyRiotString(
                                prefix,
                                suffix,
                                combinator.andThen(res -> res + "{" + n + "}"),
                                false
                                // for some reason "a{2}{3}" is only "aa" and not "aaaaaa".
                                // The {3} seems to do nothing unless "a{2}" is grouped like "(a{2}){3}"
                        )
                );
            else
                return wholeThingGroupedAndForgotten().wholeTimes(n);
        }

        @Override
        public RiotString wholeTimes(int atleast, int atmoast) {
            String lbound = atleast == UNLIMITED ? "" : String.valueOf(atleast);
            String ubound = atmoast == UNLIMITED ? "" : String.valueOf(atmoast);
            if (unitChained)
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "{" + lbound + "," + ubound + "}"),
                        false
                );
            else
                return wholeThingGroupedAndForgotten().wholeTimes(atleast, atmoast);
        }

        @Override
        public RiotString times(int n) {
            if (suffix == null || unitChained)
                return wholeTimes(n);
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeTimes(n),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString times(int atleast, int atmost) {
            if (suffix == null || unitChained)
                return wholeTimes(atleast, atmost);
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeTimes(atleast, atmost),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeThingGrouped() {
            return new LazyRiotString(
                    prefix,
                    suffix,
                    combinator.andThen(res -> "(" + res + ")"),
                    true
            );
        }

        @Override
        public RiotString grouped() {
            if (suffix == null || unitChained)
                return wholeThingGrouped();
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeThingGrouped(),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeThingGroupedAndForgotten() {
            return new LazyRiotString(
                    prefix,
                    suffix,
                    combinator.andThen(res -> "(?:" + res + ")"),
                    true
            );
        }

        @Override
        public RiotString groupedAndForgotten() {
            if (suffix == null || unitChained)
                return wholeThingGroupedAndForgotten();
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeThingGroupedAndForgotten(),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeThingGroupedAs(String name) {
            return new LazyRiotString(
                    prefix,
                    suffix,
                    combinator.andThen(res -> "(?<" + name + ">" + res + ")"),
                    true
            );
        }

        @Override
        public RiotString groupedAs(String name) {
            if (suffix == null || unitChained)
                return wholeThingGroupedAs(name);
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeThingGroupedAs(name),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeThingOptional() {
            if (unitChained)
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "?"),
                        true
                );
            else
                return wholeThingGroupedAndForgotten().wholeThingOptional();
        }

        @Override
        public RiotString optionally() {
            if (suffix == null || unitChained)
                return wholeThingOptional();
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeThingOptional(),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeOnceOrMoreTimes() {
            if (unitChained)
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "+"),
                        true
                );
            else
                return wholeThingGroupedAndForgotten().wholeOnceOrMoreTimes();
        }

        @Override
        public RiotString onceOrMoreTimes() {
            if (suffix == null || unitChained)
                return wholeOnceOrMoreTimes();
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeOnceOrMoreTimes(),
                        combinator,
                        false
                );
        }

        @Override
        public RiotString wholeZeroOrMoreTimes() {
            if (unitChained)
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "*"),
                        true
                );
            else
                return wholeThingGroupedAndForgotten().wholeZeroOrMoreTimes();
        }

        @Override
        public RiotString zeroOrMoreTimes() {
            if (suffix == null || unitChained)
                return wholeZeroOrMoreTimes();
            else
                return new LazyRiotString(
                        prefix,
                        (LazyRiotString) suffix.wholeZeroOrMoreTimes(),
                        combinator,
                        false
                );
        }

        @Override
        public boolean isUnitChain() {
            return unitChained;
        }

        @Override
        public RiotString followedBy(RiotString extension) {
            return new LazyRiotString(
                    this,
                    (LazyRiotString) extension,
                    (p, s) -> "" + p + "(?=" + s + ")",
                    false
            );
        }

        @Override
        public RiotString followedByNot(RiotString expression) {
            return new LazyRiotString(
                    this,
                    (LazyRiotString) expression,
                    (p, s) -> "" + p + "(?!" + s + ")",
                    false
            );
        }
    }
}
