// Compiles, but "java Hello" fails at run time: the method is called Main, not main (Section 2.7).
public class Hello {
    public static void Main(String[] args) {
        System.out.println("Hello");
    }
}
