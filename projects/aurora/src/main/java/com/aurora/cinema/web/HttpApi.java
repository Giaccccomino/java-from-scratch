package com.aurora.cinema.web;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.SortedSet;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Translates HTTP + JSON into calls to the service, and exceptions into status codes. */
public class HttpApi {
    private static final System.Logger LOG = System.getLogger(HttpApi.class.getName());
    private static final Pattern SEATS = Pattern.compile("/api/showings/([\\w-]+)/seats");
    private static final Pattern ONE_BOOKING = Pattern.compile("/api/bookings/([\\w-]+)");

    private final BookingService service;
    private final ObjectMapper json = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private HttpServer server;

    public HttpApi(BookingService service) { this.service = service; }

    record SeatStatus(String showing, int free, SortedSet<Seat> taken) {}
    record ApiError(int status, String error) {}

    public int start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/api/", this::handle);
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());   // Section 39.7
        server.start();
        return server.getAddress().getPort();
    }

    public void stop() { server.stop(0); }

    private void handle(HttpExchange s) throws IOException {
        String method = s.getRequestMethod();
        String path = s.getRequestURI().getPath();
        try {
            Matcher m;
            if (method.equals("GET") && path.equals("/api/showings")) {
                respond(s, 200, service.schedule());
            } else if (method.equals("GET") && (m = SEATS.matcher(path)).matches()) {
                String id = m.group(1);
                respond(s, 200, new SeatStatus(id, service.freeSeats(id), service.takenSeats(id)));
            } else if (method.equals("POST") && path.equals("/api/bookings")) {
                BookingRequest r = json.readValue(s.getRequestBody(), BookingRequest.class);
                Booking b = service.book(r);
                s.getResponseHeaders().set("Location", "/api/bookings/" + b.code());
                respond(s, 201, b);
            } else if (method.equals("DELETE") && (m = ONE_BOOKING.matcher(path)).matches()) {
                respond(s, 200, service.cancel(m.group(1)));
            } else {
                respond(s, 404, new ApiError(404, "no such resource: " + method + " " + path));
            }
        } catch (NotFoundException e) {
            respond(s, 404, new ApiError(404, e.getMessage()));
        } catch (SeatTakenException e) {
            respond(s, 409, new ApiError(409, e.getMessage()));
        } catch (RuleException e) {
            respond(s, 422, new ApiError(422, e.getMessage()));
        } catch (JsonProcessingException e) {
            Throwable cause = e.getCause() instanceof RuleException r ? r : e;
            respond(s, 400, new ApiError(400, "invalid request: " +
                    (cause instanceof RuleException ? cause.getMessage() : e.getOriginalMessage())));
        } catch (RuntimeException e) {
            LOG.log(System.Logger.Level.ERROR, "unexpected error on " + method + " " + path, e);
            respond(s, 500, new ApiError(500, "internal error"));
        }
    }

    private void respond(HttpExchange s, int code, Object body) throws IOException {
        byte[] b = json.writeValueAsBytes(body);
        s.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        s.sendResponseHeaders(code, b.length);
        try (OutputStream out = s.getResponseBody()) { out.write(b); }
    }
}
