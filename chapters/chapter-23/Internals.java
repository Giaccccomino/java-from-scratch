import java.util.*;
public class Internals {
    static int bucket(Object k, int n) { int h = k.hashCode(); return (h ^ (h >>> 16)) & (n - 1); }
    public static void main(String[] args) {
        for (String k : List.of("Screen 1", "Screen 2", "Screen 3", "Fog", "Harbor", "Echo", "Aa", "BB"))
            System.out.printf("%-9s hash=%11d bucket=%2d%n", k, k.hashCode(), bucket(k, 16));
        Deque<String> stack = new ArrayDeque<>();
        stack.push("A"); stack.push("B"); stack.push("C");
        System.out.println(stack.pop() + " " + stack.peek() + " " + stack);
        Queue<String> queue = new ArrayDeque<>();
        queue.offer("Smith"); queue.offer("Johnson"); queue.offer("Brown");
        System.out.println(queue.poll() + " " + queue.peek() + " " + queue);
        Queue<String> empty = new ArrayDeque<>();
        System.out.println(empty.poll() + " " + empty.peek());
        try { empty.remove(); } catch (NoSuchElementException e) { System.out.println("remove on an empty queue: NoSuchElementException"); }
        try { queue.offer(null); } catch (NullPointerException e) { System.out.println("ArrayDeque rejects null"); }
        record Request(String customer, int priority) {}
        PriorityQueue<Request> pq = new PriorityQueue<>(Comparator.comparingInt(Request::priority));
        pq.add(new Request("Smith", 3)); pq.add(new Request("VIP Johnson", 1)); pq.add(new Request("Brown", 2)); pq.add(new Request("Davis", 3));
        System.out.println(pq);
        while (!pq.isEmpty()) System.out.print(pq.poll().customer() + " ");
        System.out.println();
        PriorityQueue<Integer> num = new PriorityQueue<>(List.of(5, 1, 4, 2, 3));
        System.out.println(num + " -> first " + num.peek());
        Map<String, Integer> lru = new LinkedHashMap<>(16, 0.75f, true) {
            @Override protected boolean removeEldestEntry(Map.Entry<String, Integer> e) { return size() > 3; }
        };
        lru.put("A", 1); lru.put("B", 2); lru.put("C", 3); lru.get("A"); lru.put("D", 4);
        System.out.println(lru);
        TreeMap<Integer, String> sched = new TreeMap<>(Map.of(1530, "Skyline", 1800, "River", 2030, "Hilltop", 2245, "Lighthouse"));
        System.out.println(sched.ceilingEntry(1900) + " " + sched.floorKey(1900) + " " + sched.headMap(1800, true) + " " + sched.firstEntry() + " " + sched.descendingMap());
        Map<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < 5; i++) hm.put("k" + i, i);
        System.out.println(hm);
        EnumSet<java.time.DayOfWeek> weekend = EnumSet.of(java.time.DayOfWeek.SATURDAY, java.time.DayOfWeek.SUNDAY);
        System.out.println(weekend);
    }
}
