public class V {
    public static void main(String[] args) {
        var title = "A Night in the City";
        var seats = 120;
        var price = 9.50;
        var takings = seats * price;
        System.out.println(title + " " + seats + " " + price + " " + takings);
        var big = 120L;
        System.out.println(((Object) big).getClass().getSimpleName());
    }
}
