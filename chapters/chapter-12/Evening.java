public class Evening {
    public static void main(String[] args) {
        Movie movie = new Movie("A Night in the City", 112);
        Screen screen = new Screen("Screen 1", 120);
        Showing s1 = new Showing(movie, screen, "8:30 PM");
        Showing s2 = new Showing(movie, new Screen("Screen 2", 80), "10:45 PM");
        s1.sell(3);
        s2.sell(2);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println("Same movie? " + (s1.movie == s2.movie));
        movie.title = "A Night in the City (restored version)";
        System.out.println(s2.movie.title);
    }
}

class Movie {
    String title;
    int lengthMinutes;
    Movie(String title, int lengthMinutes) {
        this.title = title;
        this.lengthMinutes = lengthMinutes;
    }
}

class Screen {
    String name;
    int capacity;
    int sold;
    Screen(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}

class Showing {
    Movie movie;
    Screen screen;
    String time;
    int tickets;

    Showing(Movie movie, Screen screen, String time) {
        this.movie = movie;
        this.screen = screen;
        this.time = time;
    }

    void sell(int n) {
        tickets += n;
        screen.sold += n;
    }

    @Override
    public String toString() {
        return time + " - " + movie.title + " in " + screen.name + ", " + tickets
                + (tickets == 1 ? " ticket" : " tickets");
    }
}
