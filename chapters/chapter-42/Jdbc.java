import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class Jdbc {
    record Showing(int id, String movie, String screen, LocalDateTime start, int priceCents) {}

    static List<Showing> showingsOn(Connection conn, LocalDate day) throws SQLException {
        String sql = """
                SELECT sh.id, sh.movie, sc.name, sh.starts_at, sh.price_cents
                FROM showings sh JOIN screens sc ON sc.id = sh.screen_id
                WHERE sh.starts_at >= ? AND sh.starts_at < ?
                ORDER BY sh.starts_at
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, day.atStartOfDay());
            ps.setObject(2, day.plusDays(1).atStartOfDay());
            try (ResultSet rs = ps.executeQuery()) {
                List<Showing> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new Showing(
                            rs.getInt("id"),
                            rs.getString("movie"),
                            rs.getString("name"),
                            rs.getObject("starts_at", LocalDateTime.class),
                            rs.getInt("price_cents")));
                }
                return result;
            }
        }
    }

    static int newScreen(Connection conn, String name, int capacity) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO screens (name, capacity) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, capacity);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getInt(1);
            }
        }
    }

    static int book(Connection conn, int showing, String customer, List<String> seats) throws SQLException {
        String sql = "INSERT INTO bookings (showing_id, customer, seat) VALUES (?, ?, ?)";
        boolean autoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String seat : seats) {
                ps.setInt(1, showing);
                ps.setString(2, customer);
                ps.setString(3, seat);
                ps.addBatch();
            }
            int[] rows = ps.executeBatch();
            conn.commit();
            return rows.length;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommit);
        }
    }

    // Runs schema.sql (statements separated by ";") on an empty database.
    static void loadSchema(Connection conn, Path file) throws SQLException, IOException {
        String text = Files.readString(file).lines()
                .filter(line -> !line.strip().startsWith("--"))
                .collect(Collectors.joining("\n"));
        try (Statement st = conn.createStatement()) {
            for (String sql : text.split(";")) {
                if (!sql.isBlank()) st.execute(sql);
            }
        }
    }

    // java -cp h2.jar:. Jdbc                       (H2 in memory, the default)
    // java -cp postgresql.jar:. Jdbc jdbc:postgresql://localhost/aurora user password
    public static void main(String[] args) throws Exception {
        String url = args.length > 0 ? args[0] : "jdbc:h2:mem:aurora";
        String user = args.length > 1 ? args[1] : "sa";
        String password = args.length > 2 ? args[2] : "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            loadSchema(conn, Path.of("schema.sql"));
            for (Showing s : showingsOn(conn, LocalDate.of(2026, 10, 30))) System.out.println(s);
            System.out.println(book(conn, 1, "Luke Davis", List.of("E1", "E2")));
            System.out.println(newScreen(conn, "Screen 4", 60));
            try {
                book(conn, 1, "Paul Miller", List.of("E3", "C5"));   // C5 is already taken: everything is cancelled
            } catch (SQLException e) {
                System.out.println("booking cancelled: " + e.getSQLState());
            }
        }
    }
}
