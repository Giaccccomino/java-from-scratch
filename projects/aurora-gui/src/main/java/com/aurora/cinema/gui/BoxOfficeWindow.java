package com.aurora.cinema.gui;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.service.*;
import com.aurora.cinema.store.InMemoryStore;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.*;

/** The graphical box office: it uses the same BookingService as Chapter 45. */
public class BoxOfficeWindow extends JFrame {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("EEE M/d h:mm a", Locale.US);
    private static final Color FREE = new Color(0xC8E6C9), TAKEN = new Color(0xEF9A9A);

    private final BookingService service;
    private final InMemoryStore store;
    private final JList<Showing> list;
    private final JPanel map = new JPanel();
    private final Map<Seat, JToggleButton> buttons = new TreeMap<>();     // sorted: A1, A2, ... (Section 45.3)
    private final JTextField name = new JTextField(14), email = new JTextField(16);
    private final JLabel total = new JLabel("No seats selected");
    private final JLabel status = new JLabel(" ");
    private final JButton book = new JButton("Book");

    public BoxOfficeWindow(BookingService service, InMemoryStore store) {
        super("Aurora Cinema – Box Office");
        this.service = service;
        this.store = store;

        list = new JList<>(service.schedule().toArray(new Showing[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer((l, s, index, selected, focus) -> {          // how to draw each row
            JLabel label = new JLabel(TIME.format(s.start()) + "  " + s.movie());
            label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            label.setOpaque(true);
            label.setBackground(selected ? l.getSelectionBackground() : l.getBackground());
            label.setForeground(selected ? l.getSelectionForeground() : l.getForeground());
            return label;
        });
        list.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) showMap(); });

        JPanel screen = new JPanel(new BorderLayout());
        JLabel canvas = new JLabel("SCREEN", SwingConstants.CENTER);
        canvas.setOpaque(true);
        canvas.setBackground(Color.DARK_GRAY);
        canvas.setForeground(Color.WHITE);
        screen.add(canvas, BorderLayout.NORTH);
        screen.add(map, BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Name:"));
        form.add(name);
        form.add(new JLabel("Email:"));
        form.add(email);
        form.add(book);
        form.add(total);
        book.addActionListener(e -> book());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(form, BorderLayout.CENTER);
        bottom.add(status, BorderLayout.SOUTH);
        status.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        add(new JScrollPane(list), BorderLayout.WEST);
        add(screen, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 480);
        list.setSelectedIndex(0);                                          // show the first showing right away
    }

    private void showMap() {
        Showing s = list.getSelectedValue();
        Screen screen = store.screen(s.screenId()).orElseThrow();
        Set<Seat> taken = service.takenSeats(s.id());
        map.removeAll();
        buttons.clear();
        map.setLayout(new GridLayout(screen.rows(), screen.seatsPerRow(), 3, 3));
        for (int r = 0; r < screen.rows(); r++) {
            for (int n = 1; n <= screen.seatsPerRow(); n++) {
                Seat seat = new Seat((char) ('A' + r), n);
                JToggleButton b = new JToggleButton(seat.toString());
                b.setMargin(new Insets(1, 1, 1, 1));
                b.setFont(b.getFont().deriveFont(10f));
                b.setFocusPainted(false);
                boolean isTaken = taken.contains(seat);
                b.setEnabled(!isTaken);                                    // taken seats can't be chosen
                b.setBackground(isTaken ? TAKEN : FREE);
                b.addItemListener(e -> updateTotal());
                buttons.put(seat, b);
                map.add(b);
            }
        }
        map.revalidate();                                                  // recompute the layout
        map.repaint();                                                     // and redraw
        updateTotal();
        status.setText(s.movie() + " – " + screen.name() + ": " + service.freeSeats(s.id()) + " seats free");
    }

    private List<String> selected() {
        return buttons.entrySet().stream()
                .filter(e -> e.getValue().isSelected())
                .map(e -> e.getKey().toString()).toList();
    }

    private void updateTotal() {
        List<String> selected = selected();
        if (selected.isEmpty()) { total.setText("No seats selected"); return; }
        long cents = new Prices().total(list.getSelectedValue(), selected.size());
        total.setText(selected.size() + " seats: " + String.format(Locale.US, "$%.2f", cents / 100.0));
    }

    private void book() {
        BookingRequest request;
        try {
            request = new BookingRequest(list.getSelectedValue().id(), name.getText(), email.getText(), selected());
        } catch (AuroraException e) {                          // empty name, wrong email, no seats
            status.setText("Error: " + e.getMessage());
            return;
        }
        book.setEnabled(false);                                // no double clicks while waiting
        status.setText("Booking in progress...");
        new SwingWorker<Booking, Void>() {
            @Override protected Booking doInBackground() throws Exception {      // in a worker thread
                Thread.sleep(800);                             // simulates the call to a slow server
                return service.book(request);
            }
            @Override protected void done() {                                    // back in the EDT
                book.setEnabled(true);
                try {
                    Booking b = get();
                    showMap();
                    status.setText("Booking " + b.code() + " confirmed: " + b.seats()
                            + ", total " + String.format(Locale.US, "$%.2f", b.totalCents() / 100.0));
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;      // ExecutionException, Section 38.8
                    status.setText("Error: " + cause.getMessage());
                    showMap();                                 // someone may have taken the seats in the meantime
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        InMemoryStore store = new InMemoryStore()
                .add(new Screen("S2", "Screen 2", 8, 10)).add(new Screen("S3", "Screen 3", 5, 9))
                .add(new Showing("SP1", "The Deep Blue", "S2", LocalDateTime.of(2026, 10, 30, 21, 15), 950))
                .add(new Showing("SP4", "Éclair at Dawn", "S3", LocalDateTime.of(2026, 10, 31, 21, 0), 950));
        BookingService service = new BookingService(store, new Prices(), demoClock());
        service.book(new BookingRequest("SP1", "Mark Brown", "mark@example.com", List.of("D4", "D5", "D6")));
        SwingUtilities.invokeLater(() -> new BoxOfficeWindow(service, store).setVisible(true));
    }

    /** As in DemoAurora (Section 45.7): New York time, moved back to Sunday, October 25, 2026, 8 p.m. */
    static Clock demoClock() {
        ZoneId newYork = ZoneId.of("America/New_York");
        Clock real = Clock.system(newYork);
        Instant start = ZonedDateTime.of(2026, 10, 25, 20, 0, 0, 0, newYork).toInstant();
        return Clock.offset(real, Duration.between(real.instant(), start));
    }
}
