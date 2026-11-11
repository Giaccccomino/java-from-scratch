public class Encapsulation {
    public static void main(String[] args) {
        Screen s = new Screen("Screen 1", 120);
        System.out.println(s.book(30) + " " + s.getSold() + " " + s.getFree());
        System.out.println(s.cancel(50) + " " + s.cancel(10) + " " + s);
        try {
            new Screen("Screen X", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        s.setName("Big Screen");
        System.out.println(s);
    }
}

class Screen {
    private String name;
    private final int capacity;
    private int sold;

    Screen(String name, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("invalid capacity: " + capacity);
        }
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public int getSold() { return sold; }
    public int getFree() { return capacity - sold; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        this.name = name;
    }

    public boolean book(int seats) {
        if (seats <= 0 || seats > getFree()) return false;
        sold += seats;
        return true;
    }

    public boolean cancel(int seats) {
        if (seats <= 0 || seats > sold) return false;
        sold -= seats;
        return true;
    }

    @Override
    public String toString() { return name + " [" + sold + "/" + capacity + "]"; }
}
