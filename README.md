# RegexRiot

Welcome to the official development repository of RegexRiot!

## Documentation

Here is a direct link to the [repository](https://github.com/diego-ruben-cruz/RegexRiot-Documentation) that contains both the project proposal and final report of the class project that led to the creation of RegexRiot.

# Usage

This library is built around the interface `RiotString`.
`RiotString`s are immutable type objects that represent
a regex. They can have two types of operations done upon
them.

- **combination:** A `RiotString` can combine with another
  `RiotString` to produce a new `RiotString` that is the
  combination of its parent `RiotString`s
  - 'abc' then 'xyz' => 'abcxyz'
  - 'abc' or 'xyz' => 'abc|xyz'
- **modification:** A `RiotString` can be modified to create
  a new altered variant of the original `RiotString`
  - 'abc' times 3 => '(abc){3}'
  - 'abc' but 'c' is optional => 'abc?'

# Points of improvement

There should be room to improve LazyRiotString evaluation
speed by caching evaluated results. But that breaks the
principal of immutability. Instead, the closest current thing
is to use the eval method to create a new LazyRiotString
that is the same regex but with all the nested prefixes and
suffixes resolved to a single string regex
