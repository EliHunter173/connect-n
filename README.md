# Connect-N

This project is a fork of my comprehensive exercise project for CSC 116 (Intro to Java) at NC State University.

I considered it interesting enough to copy it over to my own personal account for further development. I would have
forked the repo, but NC State has its own GitHub Enterprise network, and I wanted this to be associated with my
personal GitHub.

For my comprehensive exercise, I chose to do Connect Four with a twist. Instead of the traditional two-player game
where the goal is to always connect four tokens on a 7x6 board, I decided to make it so that the user can specify an
arbitrary number of players, an arbitrary board size, and an arbitrary number of tokens to connect, with very few
limits on their creativity.

I call this game, *Connect-N!*

*Note: Although this project was supposed to be a group project, only I, Eli W. Hunter, have written any code,
documentation artifacts, or tests. For that reason, I, Eli W. Hunter, am listed as the sole author of this project.*

## Getting Started

```shell
# Clone our repository
git clone https://github.ncsu.edu/mfms/csc116-005-CE-3.git
# Compile the source code
mkdir bin
javac -d bin -cp bin src/*.java
# Run your desired user interface
java -cp bin CLI
```

## Built With

* [**Java**](https://www.java.com/en/) - A common, object-oriented, statically and strongly typed programming language
  derived from C.
* [**JUnit 5**](https://junit.org/junit5/) - A unit testing framework for Java 8 and above.

## Contributing

Check out our issues to see what needs to be worked on.
If you see something you believe needs work but there isn't an issue, create one! Even if you don't plan on working
on it yourself.

To claim an issue as yours, simply assign yourself to it.
If you need any help, mark that issue with `help wanted` label and leave a brief description of what you're trying
to accomplish (with a little bit about how) and what's causing the issue.

## Authors

* **Eli W. Hunter**
