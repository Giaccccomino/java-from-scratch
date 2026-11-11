import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        Movie[] movies = {
            new Movie("A Night in the City", 112),
            new Movie("The Big River", 95),
            new Movie("Skyline", 128)
        };
        Arrays.sort(movies);
        System.out.println(Arrays.toString(movies));

        Discount students = price -> price * 80 / 100;
        Discount wednesday = price -> Math.min(price, 600);
        Discount none = new Discount() {
            @Override public long apply(long price) { return price; }
        };
        for (Discount d : new Discount[]{ students, wednesday, none }) {
            System.out.print(d.apply(950) + " ");
        }
        System.out.println();
        System.out.println(students.then(wednesday).apply(950));
    }
}

class Movie implements Comparable<Movie> {
    private final String title;
    private final int length;
    Movie(String title, int length) { this.title = title; this.length = length; }
    @Override public int compareTo(Movie other) { return Integer.compare(length, other.length); }
    @Override public String toString() { return title + " (" + length + ")"; }
}

@FunctionalInterface
interface Discount {
    long apply(long priceCents);

    default Discount then(Discount next) {
        return p -> next.apply(apply(p));
    }
}
