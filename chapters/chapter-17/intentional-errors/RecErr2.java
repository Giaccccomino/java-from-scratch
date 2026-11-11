public class RecErr2 { public static void main(String[] a) { Movie m = new Movie("x", 1); m.title = "y"; } }
record Movie(String title, int year) { }
