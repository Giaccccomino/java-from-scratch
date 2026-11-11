import java.net.*; import java.net.http.*;
public class Cause { public static void main(String[] a) throws Exception {
 try { HttpClient.newHttpClient().send(HttpRequest.newBuilder(URI.create("http://boxoffice.invalid/")).build(), HttpResponse.BodyHandlers.ofString()); }
 catch (Exception e) { System.out.println(e + " / cause: " + e.getCause()); } } }
