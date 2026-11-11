public class Schedule {
    public static void main(String[] args) {
        Content[] evening = {
            new Film("A Night in the City", 112, "M. Ferris"),
            new Concert("City Symphony Orchestra", 90, 2),
            new Film("The Big River", 95, "L. Bass")
        };
        int minutes = 0;
        long takings = 0;
        for (Content c : evening) {
            System.out.println(c.card());
            minutes += c.getLengthMinutes();
            takings += c.priceCents() * 100;
        }
        System.out.printf("Total length: %d min, expected takings with 100 viewers: $%.2f%n", minutes, takings / 100.0);
    }
}

abstract class Content {
    private final String title;
    private final int lengthMinutes;

    protected Content(String title, int lengthMinutes) {
        this.title = title;
        this.lengthMinutes = lengthMinutes;
    }

    public String getTitle() { return title; }
    public int getLengthMinutes() { return lengthMinutes; }

    public abstract long priceCents();

    protected abstract String details();

    public final String card() {
        return String.format("%-24s %3d min  $%6.2f  %s",
                title, lengthMinutes, priceCents() / 100.0, details());
    }
}

class Film extends Content {
    private final String director;

    Film(String title, int lengthMinutes, String director) {
        super(title, lengthMinutes);
        this.director = director;
    }

    @Override public long priceCents() { return 950; }
    @Override protected String details() { return "directed by " + director; }
}

class Concert extends Content {
    private final int intermissions;

    Concert(String title, int lengthMinutes, int intermissions) {
        super(title, lengthMinutes + 15 * intermissions);
        this.intermissions = intermissions;
    }

    @Override public long priceCents() { return 1800; }
    @Override protected String details() { return "live, " + intermissions + " intermissions"; }
}
