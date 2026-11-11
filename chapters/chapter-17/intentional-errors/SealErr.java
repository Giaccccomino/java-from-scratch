public class SealErr { public static void main(String[] a) {} }
sealed interface Payment permits Cash {}
record Cash(long amount) implements Payment {}
record Bitcoin(long amount) implements Payment {}
sealed abstract class Content permits Film, Concert {}
class Film extends Content {}
non-sealed class Concert extends Content {}
class Jazz extends Concert {}
