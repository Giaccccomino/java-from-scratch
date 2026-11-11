public class Cinema {
    public static void main(String[] args) {
        Screen one = new Screen("Screen 1", 120);
        Screen two = new Screen("Screen 2");
        System.out.println(one);
        System.out.println(two);
        System.out.println(one.book(30) + " " + one.book(100) + " " + one.book(90));
        System.out.println(one + " full? " + one.isFull());
        System.out.println(one.book(-5));
        Screen[] screens = { one, two, new Screen("Screen 3", 60) };
        int total = 0;
        for (Screen s : screens) total += s.getFree();
        System.out.println("Free seats in the cinema: " + total);
    }
}

class Screen {
    String name;
    int capacity;
    int sold;

    Screen(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.sold = 0;
    }

    Screen(String name) {
        this(name, 100);
    }

    boolean book(int seats) {
        if (seats <= 0 || seats > getFree()) {
            return false;
        }
        sold += seats;
        return true;
    }

    int getFree() {
        return capacity - sold;
    }

    boolean isFull() {
        return getFree() == 0;
    }

    @Override
    public String toString() {
        return name + " [" + sold + "/" + capacity + "]";
    }
}
