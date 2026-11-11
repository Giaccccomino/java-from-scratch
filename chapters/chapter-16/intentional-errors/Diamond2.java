public class Diamond2 { public static void main(String[] a) {} }
interface Register { default String greeting() { return "r"; } }
interface Bar { default String greeting() { return "b"; } }
class BoxOffice implements Register, Bar { }
