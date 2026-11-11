package com.aurora.cinema.app;

import com.aurora.cinema.model.Movie;
import com.aurora.cinema.model.Screen;
import java.util.List;
import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) {
        Movie m = new Movie("A Night in the City", 112);
        Screen s = new Screen("Screen 1", 120);
        System.out.println(m.title() + " in " + s.getName() + ", " + m.durationInHours() + " " + max(1, 2) + " " + List.of(args));
    }
}
