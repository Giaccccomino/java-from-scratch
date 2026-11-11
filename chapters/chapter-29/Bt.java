public class Bt { public static void main(String[] a) {
  for (int n : new int[]{16, 20, 22, 24}) {
    String s = "a".repeat(n) + "!";
    long t = System.nanoTime(); boolean ok = s.matches("(a|a?)+b"); 
    long t2 = System.nanoTime(); boolean ok2 = s.matches("(\\w+\\s?)+$");
    System.out.println(n + " -> " + ok + " " + (t2-t)/1_000_000 + " ms; " + ok2 + " " + (System.nanoTime()-t2)/1_000_000 + " ms");
  }
}}
