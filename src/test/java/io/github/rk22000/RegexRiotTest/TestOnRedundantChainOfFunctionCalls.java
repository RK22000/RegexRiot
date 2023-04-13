package io.github.rk22000.RegexRiotTest;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotGroupings.replacementGroup;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.zeroOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.inSetOf;
import static io.github.rk22000.RegexRiot.RiotTokens.*;

public class TestOnRedundantChainOfFunctionCalls {

    <T>void check(T actual, String expected) {
        assert actual.toString().equals(expected): "Expected: "+expected+"\n" +
                "Actual  : "+actual.toString();
    }
    @Test
    void EX18_operatorReplacedByFunc() {
        // Replace a + b with Add(a,b)
        var raw = """
                int c = a+b;
                var average = (a1 + a2)/2
                sum(foo, bar, x) = foo(x) + bar(x)
                var dotProduct(v1, v2) = v1.x*v2.x + v1.y*v2.y
                """;
        var correct = """
                int c = a+b;
                var average = (Add(a1, a2))/2
                sum(foo, bar, x) = Add(foo(x), bar(x))
                var dotProduct(v1, v2) = Add(v1.x*v2.x, v1.y*v2.y)
                """;
        // (\w[a-z0-9()*.]+)\s?\+\s?(\w[a-z0-9()*.]+)
        var argument = WORD_CHAR.then(inSetOf(WORD_CHAR).and("()*.")).zeroOrMoreTimes();
        var ritex =
                riot(argument).as("a")
                        .then(SPACES).then(PLUS).then(SPACES)
                        .then(argument).as("b");

        var replaced = ritex
                .compile().matcher(raw).replaceAll("Add("+replacementGroup("a")+", "+replacementGroup("b")+")");
        check(replaced, correct);

        var variableOrFunction = riot(
                WORD_CHAR.then(
                        zeroOrMore(
                                inSetOf(WORD_CHAR)
                                        .and("()*.")
                        )
                )
        );
        replaced = riot(variableOrFunction.as("a"))
                .then(SPACES).then(PLUS).then(SPACES)
                .then(variableOrFunction.as("b"))

                .compile().matcher(raw).replaceAll("Add("+replacementGroup("a")+", "+replacementGroup("b")+")");
        check(
                replaced,
                correct
        );


    }

    public static void main(String[] args) {
        var test = "[";
        System.out.println("-- "+test+" --");
        Stream.of(
                ".",
                "a",
                "1",
                "?",
                "\\",
                "b",
                "["
        ).map(it -> it + " -> " + inSetOf(test).toRiotString().compile().matcher(it).matches())
                .forEach(System.out::println);
    }
}
