import java.util.*;
public class Cme {
    public static void main(String[] args) {
        List<String> titles = new ArrayList<>(List.of("Fog", "Lighthouse", "Hilltop", "Ice"));
        try {
            for (String t : titles) if (t.length() <= 4) titles.remove(t);
        } catch (ConcurrentModificationException e) { System.out.println("ConcurrentModificationException"); }
        System.out.println(titles);
        titles = new ArrayList<>(List.of("Fog", "Lighthouse", "Hilltop", "Ice"));
        Iterator<String> it = titles.iterator();
        while (it.hasNext()) if (it.next().length() <= 4) it.remove();
        System.out.println(titles);
        List<Integer> n = new ArrayList<>(List.of(1, 2, 2, 3));
        for (int i = 0; i < n.size(); i++) if (n.get(i) == 2) n.remove(i);
        System.out.println(n);
        ListIterator<String> li = titles.listIterator();
        while (li.hasNext()) { String t = li.next(); li.set(t + "!"); if (t.equals("Lighthouse")) li.add("Harbor"); }
        System.out.println(titles);
    }
}
