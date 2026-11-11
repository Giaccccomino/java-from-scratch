public class Comp { public static void main(String[] a) {
  Ticket t = new VipTicket();
  t.getSeat();
  String s = (String) t;
} }
class Ticket {}
class VipTicket extends Ticket { String getSeat() { return "F12"; } }
