package io.github.rk22000.RegexRiot;

import java.util.function.BiFunction;

import static io.github.rk22000.RegexRiot.RiotStringImplementations.newLazyRiot;

//import io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet;
//
//import static io.github.rk22000.RegexRiot.Riot.riot;
//import static io.github.rk22000.RegexRiot.RiotTypedSet.InclusiveSet.riotInclude;
//
public interface RiotSet extends RiotString.RiotStringable {
    static <T>RiotSet inSetOf(T seed) {
        return RiotSetImplementations.lazyInclusiveSetOf(seed.toString());
    }
    static <T>RiotSet outSetOf(T seed) {
        return RiotSetImplementations.lazyExclusiveSetOf(seed.toString());
    }
    <T>RiotSet and(T extension);
    @FunctionalInterface
    interface RiotSetRange {
        RiotSet through(char endCharInclusive);
    }
    RiotSetRange andChars(char startCharInclusive);
    RiotSet complement();
    default RiotString toRiotString() {
        return newLazyRiot(toString(), true);
    }
    default RiotString andThen(RiotString expression) {
        return toRiotString().then(expression);
    }
}

class RiotSetImplementations {
    static RiotSet lazyInclusiveSetOf(String seed) {
        return new LazyInclusiveRiotSet(seed);
    }
    static RiotSet lazyExclusiveSetOf(String seed) {
        return new LazyExclusiveRiotSet(seed);
    }
    private static abstract class LazyRiotSet implements Evalable<RiotSet>, RiotSet {
        final LazyRiotSet prefix, suffix;
        final BiFunction<LazyRiotSet, LazyRiotSet, String> combinator;

        LazyRiotSet(String seed) {
            super();
            prefix = null;
            suffix = null;
            combinator = (__,___) -> seed;
        }
        LazyRiotSet(
                LazyRiotSet pfix,
                LazyRiotSet sfix,
                BiFunction<LazyRiotSet, LazyRiotSet, String> extender) {
            super();
            if (pfix==null ^ sfix==null) throw new IllegalArgumentException("Can not have only one of prefix or suffix be null. Either both should be null or neither should be");
            prefix = pfix;
            suffix = sfix;
            if (extender==null) throw new IllegalArgumentException("Extender can not be null");
            combinator = extender;
        }

        abstract LazyRiotSet riotSet(String seed);
        abstract LazyRiotSet riotSet(
                LazyRiotSet pfix,
                LazyRiotSet sfix,
                BiFunction<LazyRiotSet, LazyRiotSet, String> extender);
        String rawString() {
            return combinator.apply(prefix, suffix);
        }
        @Override
        public RiotSet eval() {
            return riotSet(combinator.apply(prefix, suffix));
        }
        @Override
        public <T>RiotSet and(T extension) {
            return riotSet(
                    this,
                    riotSet(extension.toString()),
                    (p,s) -> ""+p.rawString()+s.rawString()
            );
        }

        @Override
        public RiotSetRange andChars(char startCharInclusive) {
            return endCharInclusive -> and(""+startCharInclusive+"-"+endCharInclusive);
        }
    }
    private static class LazyInclusiveRiotSet extends LazyRiotSet {

        @Override
        public String toString() {
            if (prefix==null||suffix==null)
                return "["+combinator.apply(null,null)+"]";
            else
                return eval().toString();
        }

        LazyInclusiveRiotSet(String seed) {
            super(seed);
        }

        LazyInclusiveRiotSet(LazyRiotSet pfix, LazyRiotSet sfix, BiFunction<LazyRiotSet, LazyRiotSet, String> extender) {
            super(pfix, sfix, extender);
        }

        @Override
        public RiotSet complement() {
            return new LazyExclusiveRiotSet(prefix, suffix, combinator);
        }

        @Override
        LazyRiotSet riotSet(String seed) {
            return new LazyInclusiveRiotSet(seed);
        }

        @Override
        LazyRiotSet riotSet(LazyRiotSet pfix, LazyRiotSet sfix, BiFunction<LazyRiotSet, LazyRiotSet, String> extender) {
            return new LazyInclusiveRiotSet(pfix, sfix, extender);
        }
    }
    private static class LazyExclusiveRiotSet extends LazyRiotSet {

        LazyExclusiveRiotSet(String seed) {
            super(seed);
        }

        LazyExclusiveRiotSet(LazyRiotSet pfix, LazyRiotSet sfix, BiFunction<LazyRiotSet, LazyRiotSet, String> extender) {
            super(pfix, sfix, extender);
        }

        @Override
        public String toString() {
            if (prefix==null||suffix==null)
                return "[^"+combinator.apply(null,null)+"]";
            else
                return eval().toString();
        }
        @Override
        public RiotSet complement() {
            return new LazyInclusiveRiotSet(prefix, suffix, combinator);
        }

        @Override
        LazyRiotSet riotSet(String seed) {
            return new LazyExclusiveRiotSet(seed);
        }

        @Override
        LazyRiotSet riotSet(LazyRiotSet pfix, LazyRiotSet sfix, BiFunction<LazyRiotSet, LazyRiotSet, String> extender) {
            return new LazyExclusiveRiotSet(pfix, sfix, extender);
        }
    }
}
//    default RiotString toRiotString() {
//        return riot(toString(), true);
//    }
//    String rawString();
//    RiotSet complement();
//}