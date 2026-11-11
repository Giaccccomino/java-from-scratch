import com.fasterxml.jackson.databind.*;
import java.util.*;
public class J3 {
    record BookingRequest(String showing, List<String> seats) {
        BookingRequest {
            if (showing == null || showing.isBlank()) throw new IllegalArgumentException("missing showing");
            if (seats == null || seats.isEmpty()) throw new IllegalArgumentException("no seats requested");
            seats = List.copyOf(seats);
        }
    }
    public static void main(String[] a) {
        try { new ObjectMapper().readValue("{\"seats\":[\"C5\"]}", BookingRequest.class); }
        catch (Exception e) { System.out.println(e.getClass().getSimpleName() + " / cause: " + e.getCause()); }
        System.out.println(new ObjectMapper().createObjectNode().put("screen", 3).put("free", 37));
    }
}
