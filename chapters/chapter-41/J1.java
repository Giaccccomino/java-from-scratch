import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.exc.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.*;
import java.util.*;

public class J1 {
    enum Format { TWO_D, THREE_D }
    record Showing(String movie, String screen, LocalDateTime start, Format format, int minutes) {}
    record Booking(int id, String customer, List<String> seats, long totalCents) {}

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Booking b = new Booking(4821, "Julia Ferris", List.of("C5", "C6"), 1900);
        String json = mapper.writeValueAsString(b);
        System.out.println(json);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(b));

        Booking read = mapper.readValue(json, Booking.class);
        System.out.println(read);
        System.out.println(read.equals(b));

        // java.time without the module
        Showing s = new Showing("The Deep Blue", "Screen 2", LocalDateTime.of(2026, 10, 30, 21, 15), Format.THREE_D, 168);
        try { mapper.writeValueAsString(s); }
        catch (Exception e) { System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage().lines().findFirst().get()); }

        ObjectMapper m2 = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String js = m2.writeValueAsString(s);
        System.out.println(js);
        System.out.println(m2.readValue(js, Showing.class));

        // unknown property
        String withExtra = "{\"id\":1,\"customer\":\"Mark\",\"seats\":[\"A1\"],\"totalCents\":950,\"note\":\"near the aisle\"}";
        try { mapper.readValue(withExtra, Booking.class); }
        catch (UnrecognizedPropertyException e) { System.out.println(e.getOriginalMessage()); }
        ObjectMapper lenient = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println(lenient.readValue(withExtra, Booking.class));
        System.out.println(mapper.readValue("{\"id\":7}", Booking.class));

        // a list
        String list = "[{\"id\":1,\"customer\":\"Mark\",\"seats\":[\"A1\"],\"totalCents\":950},{\"id\":2,\"customer\":\"Anna\",\"seats\":[],\"totalCents\":0}]";
        List<Booking> all = mapper.readValue(list, new TypeReference<List<Booking>>() {});
        System.out.println(all.size() + " " + all.get(1).customer());
        List<?> raw = mapper.readValue(list, List.class);
        System.out.println(raw.get(0).getClass());

        // malformed JSON
        try { mapper.readValue("{\"id\":1,", Booking.class); }
        catch (com.fasterxml.jackson.core.JsonProcessingException e) { System.out.println(e.getClass().getSimpleName() + ": " + e.getOriginalMessage()); }
        try { mapper.readValue("{\"id\":\"four\"}", Booking.class); }
        catch (MismatchedInputException e) { System.out.println(e.getClass().getSimpleName() + ": " + e.getOriginalMessage()); }

        // tree model
        JsonNode root = mapper.readTree("{\"screen\":3,\"free\":37,\"next\":[{\"movie\":\"The Deep Blue\",\"time\":\"21:15\"}]}");
        System.out.println(root.get("free").asInt() + " " + root.path("next").get(0).path("movie").asText());
        System.out.println(root.path("missing").asText("none") + " " + root.path("missing").isMissingNode());
    }
}
