public class Schedule {
    public static void main(String[] args) {
        Movie m1 = new Movie();
        m1.title = "A Night in the City";
        m1.lengthMinutes = 112;
        Movie m2 = new Movie();
        m2.title = "The Big River";
        m2.lengthMinutes = 95;
        System.out.println(m1.title + " runs " + m1.lengthInHours());
        System.out.println(m2.title + " runs " + m2.lengthInHours());
    }
}

class Movie {
    String title;
    int lengthMinutes;

    String lengthInHours() {
        return lengthMinutes / 60 + "h " + lengthMinutes % 60 + "min";
    }
}
