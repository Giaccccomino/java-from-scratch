public class Dangling {
    public static void main(String[] args) {
        int age = 30;
        boolean student = false;
        if (age < 26)
            if (student)
                System.out.println("Student discount");
        else
            System.out.println("Full price");
        System.out.println("Done");
    }
}
