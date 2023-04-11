package io.github.rk22000.RegexRiot;

import java.util.function.BiFunction;

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
     * @param seed initial value of the RiotString
     * @return a new RiotString of the same type as the current type but with the value of seed
     */
    RiotString riotString(String seed);
    <T extends RiotString> RiotString and(T extension);
    default <T extends RiotStringable> RiotString and(T extension) {
        return and(extension.toRiotString());
    }
    default <T> RiotString and(T extension) {
        return and(riotString(extension.toString()));
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
    RiotString times(int n);
    RiotString times(int atleast, int almost);
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
}

class RiotStringImplementations {
    public static RiotString newLazyRiot(String seed) {
        return new LazyRiotString(seed, seed.length()==1);
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
                boolean unit ) {
            if (pfix==null ^ sfix==null) throw new IllegalArgumentException("Can not have only one of prefix or suffix be null. Either both should be null or neither should be");
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
            if (prefix==null||suffix==null)
                return combinator.apply(null, null);
            else
                return eval().toString();
        }

        @Override
        public RiotString riotString(String seed) {
            return newLazyRiot(seed);
        }

        @Override
        public <T extends RiotString> RiotString and(T extension) {
            return new LazyRiotString(
                    this,
                    (LazyRiotString) extension,
                    (p, s) -> ""+p+s,
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
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "{"+n+"}"),
                        false
                );
            else
                return wholeThingGroupedAndForgotten().wholeTimes(n);
        }

        @Override
        public RiotString wholeTimes(int atleast, int atmoast) {
            String lbound = atleast==UNLIMITED? "" : String.valueOf(atleast);
            String ubound = atmoast==UNLIMITED? "" : String.valueOf(atmoast);
            if (unitChained)
                return new LazyRiotString(
                        prefix,
                        suffix,
                        combinator.andThen(res -> res + "{"+lbound+","+ubound+"}"),
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
                    combinator.andThen(res -> "("+res+")"),
                    true
            );
        }

        @Override
        public RiotString grouped() {
            if (suffix==null || unitChained)
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
                    combinator.andThen(res -> "(?:"+res+")"),
                    true
            );
        }

        @Override
        public RiotString groupedAndForgotten() {
            if (suffix==null || unitChained)
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
                    combinator.andThen(res -> "(?<"+name+">"+res+")"),
                    true
            );
        }

        @Override
        public RiotString groupedAs(String name) {
            if (suffix==null || unitChained)
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
                    (p,s) -> ""+p+"(?="+s+")",
                    false
            );
        }
    }
}
