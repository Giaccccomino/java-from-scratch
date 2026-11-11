import java.util.*;

public class Lists {
    public static void main(String[] args) {
        List<String> movies = new ArrayList<>();
        movies.add("A Night in the City");
        movies.add("The Big River");
        movies.add("Skyline");
        movies.add(1, "Hilltop");
        System.out.println(movies + " " + movies.size());
        System.out.println(movies.get(0) + " | " + movies.indexOf("Skyline") + " " + movies.contains("River") + " " + movies.isEmpty());
        movies.set(2, "The Big River (restored)");
        movies.remove("Hilltop");
        String removed = movies.remove(0);
        System.out.println(removed + " -> " + movies);
        movies.addAll(List.of("Riverside", "Lighthouse", "Skyline"));
        System.out.println(movies + " lastIndexOf Skyline=" + movies.lastIndexOf("Skyline"));
        Collections.sort(movies);
        System.out.println(movies);
        movies.sort(Comparator.reverseOrder());
        System.out.println(movies);
        List<String> part = movies.subList(1, 3);
        System.out.println(part);
        part.clear();
        System.out.println(movies);
        movies.removeIf(t -> t.startsWith("L"));
        System.out.println(movies);
        movies.replaceAll(String::toUpperCase);
        System.out.println(movies);
        for (int i = 0; i < movies.size(); i++) System.out.print(i + ":" + movies.get(i) + " ");
        System.out.println();
        movies.forEach(t -> System.out.print("[" + t + "] "));
        System.out.println();

        List<String> fixed = List.of("mon", "tue");
        try { fixed.add("wed"); } catch (UnsupportedOperationException e) { System.out.println("List.of is immutable: " + e); }
        try { List.of("a", null); } catch (NullPointerException e) { System.out.println("List.of rejects null"); }
        String[] arr = {"b", "a", "c"};
        List<String> view = Arrays.asList(arr);
        view.set(0, "z");
        System.out.println(Arrays.toString(arr));
        try { view.add("d"); } catch (UnsupportedOperationException e) { System.out.println("Arrays.asList has a fixed size"); }
        List<String> copy = new ArrayList<>(fixed);
        copy.add("wed");
        List<String> ro = Collections.unmodifiableList(copy);
        copy.add("thu");
        System.out.println(ro + " " + List.copyOf(copy));
        String[] back = copy.toArray(new String[0]);
        System.out.println(back.length);
        List<Integer> numbers = new ArrayList<>(List.of(5, 3, 8, 1));
        System.out.println(Collections.max(numbers) + " " + Collections.min(numbers) + " " + Collections.frequency(List.of(1, 2, 1), 1));
        Collections.reverse(numbers); System.out.println(numbers);
        Collections.swap(numbers, 0, 3); System.out.println(numbers);
        System.out.println(Collections.nCopies(3, "-") + " " + List.of(1, 2).equals(new ArrayList<>(List.of(1, 2))) + " " + List.of(1, 2).equals(new LinkedList<>(List.of(1, 2))));
        System.out.println(movies.getFirst() + " " + movies.getLast() + " " + movies.reversed());
    }
}
