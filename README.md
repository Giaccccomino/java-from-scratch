# Java from Scratch – the book's materials

[![build](https://github.com/Giaccccomino/java-from-scratch/actions/workflows/build.yml/badge.svg)](https://github.com/Giaccccomino/java-from-scratch/actions/workflows/build.yml)

The code of the book **Java from Scratch: The Complete Guide**: the examples of every chapter, the complete
Aurora Cinema projects and the solutions to the exercises. Everything is written for **Java 21** (it also works with Java 25).

## What's inside

| Folder | Contents |
|---|---|
| `chapters/chapter-NN/` | The chapter's examples, one per file, compiled and run while the book was being written |
| `chapters/chapter-NN/intentional-errors/` | Programs that **don't compile on purpose**: they are the errors shown in the text, to try out and read the compiler's messages |
| `projects/aurora/` | The booking system of Chapter 45 (Maven, with JUnit tests) |
| `projects/aurora-spring/` | The same system with Spring Boot 4 (Chapter 46) |
| `projects/aurora-gui/` | The Swing box office (Chapter 47) |
| `solutions/` | The code of the solutions in Appendix D |
| `tools/` | The script that checks that every example compiles |

## How to use it

You need JDK 21 (Chapter 2). To run an example, from the chapter's folder:

```
cd chapters/chapter-26
java Stream1.java
```

For the Maven projects (Chapters 33 and 45-47) you can use the Maven Wrapper included in the repository, or your IDE:
in IntelliJ IDEA open the project's folder and let it import the `pom.xml`.

```
./mvnw verify                                       # from the root: builds and tests projects 45 and 47 and Chapter 41
cd projects/aurora && ../../mvnw exec:java          # starts the Aurora Cinema API at http://localhost:8080/api/showings
cd projects/aurora-gui && ../../mvnw exec:java      # after "./mvnw install" from the root: the graphical box office
cd projects/aurora-spring && ./mvnw spring-boot:run
```

On every change, GitHub Actions compiles all the examples, checks that the ones in `intentional-errors` really
give an error, and runs the projects' tests (the file `.github/workflows/build.yml`, Chapter 37).

## Notes

- Some examples deliberately show behavior that depends on the computer (timings, threads, memory):
  the numbers you see will differ from the ones in the book.
- The examples of Chapter 45 use fixed dates in October 2026, as in the text (`DemoAurora` starts the API with a clock
  set to those days); the Spring Boot project instead generates its showings in the days after startup.
- Chapter 42 needs a JDBC driver on the classpath (H2 or PostgreSQL): `schema.sql` contains the tables and data.
- Found a mistake? Open an *issue* or a *pull request* (Section 37.11).
