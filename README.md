# Constructive Real Numbers

This small Java library demonstrates an approach to representing real numbers
that is described at length in the book Constructive Analysis by Errett Bishop
and Douglas Bridges. If it has not been removed, a PDF of the relevant chapter
can be found [here](http://www.nuprl.org/MathLibrary/ConstructiveAnalysis/).
The motivation to work on this implementation came from an assignment done in
CS 3110 at Cornell University, with professor Robert Constable.

## Building
This project uses [gradle](http://gradle.org/) as a build system. If gradle is
installed globally, then the project can be built by running:
```
gradle build
```
Also helpful may be `gradle test` for verifying the integrity of the code, or
`gradle jar` for compiling a java library. A prebuilt jar is also available in
the `build/libs/` directory. Finally, `gradle javadoc` builds the
documentation.

## Usage
The documentation (found at `build/docs/javadoc/`) will be very helpful for
getting started, but there are also examples in `src/examples/`.
