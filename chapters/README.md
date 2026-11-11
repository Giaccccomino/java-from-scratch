# The chapter examples

Each folder contains the examples of one chapter. They are single files, in the default package:
run them with `java FileName.java` from the chapter's folder.

The `intentional-errors` folders contain the examples that **must not compile**: they let you see
the error messages explained in the text (and in Appendix A).

Chapter 41 uses the Jackson library and is built with Maven (`../../mvnw compile` in its folder).
Chapter 32 contains a project with packages (`src/`) and one with modules (`m/`):
the commands to compile them are in Chapter 32 of the book.
Chapter 42 needs a JDBC driver (H2 or PostgreSQL) on the classpath, and loads `schema.sql`.
