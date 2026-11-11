public class Guard {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Guard.java <seats> <price>");
            System.exit(1);
        }
        int seats = Integer.parseInt(args[0]);
        double price = Double.parseDouble(args[1]);
        if (seats <= 0) {
            System.err.println("The number of seats must be positive.");
            System.exit(2);
        }
        System.out.printf("Total: $%.2f%n", seats * price);
    }
}
