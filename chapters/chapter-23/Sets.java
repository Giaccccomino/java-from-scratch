import java.util.*;

public class Sets {
    public static void main(String[] args) {
        List<String> viewers = List.of("Smith", "Johnson", "Brown", "Smith", "Davis", "Johnson");
        Set<String> hash = new HashSet<>(viewers);
        Set<String> linked = new LinkedHashSet<>(viewers);
        Set<String> tree = new TreeSet<>(viewers);
        System.out.println(hash + "\n" + linked + "\n" + tree);
        System.out.println(hash.add("Brown") + " " + hash.add("Miller") + " " + hash.contains("Davis") + " " + hash.size());

        Set<String> screen1 = new TreeSet<>(List.of("Skyline", "River", "Hilltop"));
        Set<String> screen2 = new TreeSet<>(List.of("River", "Lighthouse", "Skyline", "Harbor"));
        Set<String> union = new TreeSet<>(screen1); union.addAll(screen2);
        Set<String> inter = new TreeSet<>(screen1); inter.retainAll(screen2);
        Set<String> diff = new TreeSet<>(screen1); diff.removeAll(screen2);
        System.out.println(union + " " + inter + " " + diff);

        TreeSet<Integer> times = new TreeSet<>(List.of(1530, 1800, 2030, 2245));
        System.out.println(times.first() + " " + times.last() + " " + times.ceiling(1900) + " " + times.floor(1900) + " " + times.higher(2030) + " " + times.headSet(2030) + " " + times.tailSet(2030) + " " + times.descendingSet());

        Map<String, Integer> capacities = new HashMap<>();
        capacities.put("Screen 1", 120);
        capacities.put("Screen 2", 80);
        capacities.put("Screen 3", 60);
        System.out.println(capacities.put("Screen 2", 85) + " " + capacities.get("Screen 2") + " " + capacities.get("Screen 9") + " " + capacities.getOrDefault("Screen 9", 0));
        System.out.println(capacities.containsKey("Screen 1") + " " + capacities.containsValue(60) + " " + capacities.size() + " " + capacities.remove("Screen 3") + " " + capacities);
        capacities.putIfAbsent("Screen 4", 40);
        capacities.putIfAbsent("Screen 1", 999);
        System.out.println(new TreeMap<>(capacities));

        Map<String, Integer> count = new TreeMap<>();
        String[] sold = {"Skyline", "River", "Skyline", "Hilltop", "Skyline", "River"};
        for (String m : sold) count.merge(m, 1, Integer::sum);
        System.out.println(count);

        Map<String, List<String>> byDay = new TreeMap<>();
        byDay.computeIfAbsent("sat", k -> new ArrayList<>()).add("Skyline");
        byDay.computeIfAbsent("sat", k -> new ArrayList<>()).add("River");
        byDay.computeIfAbsent("sun", k -> new ArrayList<>()).add("Hilltop");
        System.out.println(byDay);

        for (Map.Entry<String, Integer> e : count.entrySet()) System.out.print(e.getKey() + "=" + e.getValue() + "; ");
        System.out.println();
        count.forEach((k, v) -> System.out.print(k + ":" + v + " "));
        System.out.println();
        System.out.println(count.keySet() + " " + count.values());
        Map<String, Integer> fixed = Map.of("mon", 950, "wed", 600);
        try { fixed.put("tue", 950); } catch (UnsupportedOperationException e) { System.out.println("Map.of is immutable"); }
        int seats = 0;
        try { seats = capacities.get("Screen 99"); } catch (NullPointerException e) { System.out.println("NPE from unboxing the result of get"); }
    }
}
