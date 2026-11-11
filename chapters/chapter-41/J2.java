import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.*;

public class J2 {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Customer(
            @JsonProperty("full_name") String name,
            String email,
            @JsonIgnore String password,
            @JsonFormat(pattern = "MM/dd/yyyy") LocalDate birthDate) {}

    static class Screen {                 // a traditional class
        private String name;
        private int capacity;
        public Screen() {}                // Jackson needs it to create the object
        public Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public boolean isLarge() { return capacity > 100; }
    }

    static class NoConstructor {
        private final String name;
        public NoConstructor(String name) { this.name = name; }
        public String getName() { return name; }
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper m = new ObjectMapper().registerModule(new JavaTimeModule());
        Customer c = new Customer("Julia Ferris", null, "secret", LocalDate.of(1990, 6, 15));
        System.out.println(m.writeValueAsString(c));
        System.out.println(m.readValue("{\"full_name\":\"Mark\",\"birthDate\":\"03/12/1998\",\"password\":\"x\"}", Customer.class));

        System.out.println(m.writeValueAsString(new Screen("Screen 1", 120)));
        Screen s = m.readValue("{\"name\":\"Screen 4\",\"capacity\":60}", Screen.class);
        System.out.println(s.getName() + " " + s.getCapacity());
        try { m.readValue("{\"name\":\"Screen 4\",\"capacity\":60,\"large\":false}", Screen.class); }
        catch (Exception e) { System.out.println(e.getClass().getSimpleName()); }

        try { m.readValue("{\"name\":\"x\"}", NoConstructor.class); }
        catch (Exception e) { System.out.println(e.getMessage().lines().findFirst().get()); }

        record Note(String movie, String note, double price) {}
        System.out.println(m.writeValueAsString(new Note("Laurel & Hardy \"Pack Up Your Troubles\"", "line1\nline2", 9.5)));

        ObjectMapper snake = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        record Ticket(String seatCode, long priceCents) {}
        System.out.println(snake.writeValueAsString(new Ticket("C5", 950)));
    }
}
