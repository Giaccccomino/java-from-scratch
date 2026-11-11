import java.util.*;
public class MyList {
    public static void main(String[] args) {
        DynamicList<String> l = new DynamicList<>();
        for (int i = 1; i <= 12; i++) l.add("S" + i);
        System.out.println(l.size() + " " + l.get(11) + " capacity " + l.capacity());
        System.out.println(l.remove(0) + " " + l.size() + " " + l.get(0));
        for (String s : l) System.out.print(s + " ");
        System.out.println();
        try { l.get(50); } catch (IndexOutOfBoundsException e) { System.out.println(e.getMessage()); }
    }
}
class DynamicList<E> implements Iterable<E> {
    private Object[] data = new Object[10];
    private int size = 0;

    void add(E e) {
        if (size == data.length) {
            int newLength = data.length + (data.length >> 1);
            data = Arrays.copyOf(data, newLength);
        }
        data[size++] = e;
    }

    @SuppressWarnings("unchecked")
    E get(int i) {
        Objects.checkIndex(i, size);
        return (E) data[i];
    }

    E remove(int i) {
        E old = get(i);
        System.arraycopy(data, i + 1, data, i, size - i - 1);
        data[--size] = null;
        return old;
    }

    int size() { return size; }
    int capacity() { return data.length; }

    @Override public Iterator<E> iterator() {
        return new Iterator<>() {
            int i = 0;
            public boolean hasNext() { return i < size; }
            public E next() { if (!hasNext()) throw new NoSuchElementException(); return get(i++); }
        };
    }
}
