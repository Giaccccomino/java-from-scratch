import java.util.*;
import java.util.stream.*;

public class Opt {
    record Card(String code, int points) {}
    record Customer(String name, Card card, String email) {
        Optional<Card> cardOpt() { return Optional.ofNullable(card); }
        Optional<String> emailOpt() { return Optional.ofNullable(email); }
    }
    static final Map<String, Customer> customers = Map.of(
        "smith", new Customer("John Smith", new Card("AUR-001", 120), "john@example.com"),
        "johnson", new Customer("Anna Johnson", null, null));

    static Optional<Customer> find(String id) { return Optional.ofNullable(customers.get(id)); }

    public static void main(String[] args) {
        Optional<String> full = Optional.of("Skyline");
        Optional<String> empty = Optional.empty();
        System.out.println(full + " " + empty + " " + full.isPresent() + " " + empty.isEmpty());
        try { Optional.of(null); } catch (NullPointerException e) { System.out.println("Optional.of(null): NPE"); }
        try { empty.get(); } catch (NoSuchElementException e) { System.out.println("get on empty: " + e.getMessage()); }
        System.out.println(empty.orElse("no movie") + " " + full.orElse("no movie"));
        System.out.println(empty.orElseGet(() -> "computed"));
        try { empty.orElseThrow(); } catch (NoSuchElementException e) { System.out.println("orElseThrow: " + e.getMessage()); }
        try { find("brown").orElseThrow(() -> new IllegalArgumentException("unknown customer: brown")); } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        full.ifPresent(t -> System.out.println("Found " + t));
        empty.ifPresentOrElse(t -> System.out.println(t), () -> System.out.println("nothing"));

        for (String id : List.of("smith", "johnson", "brown")) {
            int points = find(id).flatMap(Customer::cardOpt).map(Card::points).orElse(0);
            String email = find(id).flatMap(Customer::emailOpt).filter(e -> e.contains("@")).orElse("(no email)");
            String name = find(id).map(Customer::name).map(String::toUpperCase).orElse("?");
            System.out.println(id + ": " + name + ", points " + points + ", " + email);
        }
        Optional<Optional<Card>> nested = find("smith").map(Customer::cardOpt);
        System.out.println(nested);
        System.out.println(find("smith").or(() -> find("johnson")).map(Customer::name).get());
        List<String> ids = List.of("smith", "brown", "johnson");
        System.out.println(ids.stream().map(Opt::find).flatMap(Optional::stream).map(Customer::name).toList());
        OptionalInt max = IntStream.of().max();
        System.out.println(max + " " + IntStream.of(3, 9).max().getAsInt());
        String a = null;
        System.out.println(Objects.requireNonNullElse(a, "default"));
        String r = full.orElse(expensive());
        String r2 = full.orElseGet(Opt::expensive);
    }
    static String expensive() { System.out.println("expensive() called!"); return "x"; }
}
