public class Builder {
    public static void main(String[] args) {
        Showing s = new Showing.Builder("A Night in the City", "20:30")
                .screen("Screen 2")
                .threeD(true)
                .language("original with subtitles")
                .build();
        System.out.println(s);
        System.out.println(new Showing.Builder("Skyline", "18:00").build());
        try { new Showing.Builder("Skyline", "25:00").build(); } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }
}

final class Showing {
    private final String movie, time, screen, language;
    private final boolean threeD;

    private Showing(Builder b) {
        this.movie = b.movie; this.time = b.time; this.screen = b.screen;
        this.language = b.language; this.threeD = b.threeD;
    }

    @Override public String toString() {
        return time + " " + movie + " - " + screen + (threeD ? ", 3D" : "") + ", " + language;
    }

    static final class Builder {
        private final String movie, time;
        private String screen = "Screen 1";
        private String language = "English";
        private boolean threeD = false;

        Builder(String movie, String time) { this.movie = movie; this.time = time; }

        Builder screen(String screen) { this.screen = screen; return this; }
        Builder language(String language) { this.language = language; return this; }
        Builder threeD(boolean threeD) { this.threeD = threeD; return this; }

        Showing build() {
            if (!time.matches("([01][0-9]|2[0-3]):[0-5][0-9]")) throw new IllegalStateException("invalid time: " + time);
            return new Showing(this);
        }
    }
}
