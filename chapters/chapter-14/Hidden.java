public class Hidden {
    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println(p.greet() + " " + p.describe() + " " + p.name);
        System.out.println(p.getClass().getSimpleName() + " " + (p instanceof Child) + " " + (p instanceof Parent) + " " + (p instanceof Object));
        System.out.println(p.getClass().getSuperclass().getSimpleName() + " " + Parent.class.getSuperclass().getName());
    }
}
class Parent {
    String name = "parent";
    static String greet() { return "hello from the parent"; }
    String describe() { return "instance of the parent"; }
}
class Child extends Parent {
    String name = "child";
    static String greet() { return "hello from the child"; }
    @Override String describe() { return "instance of the child"; }
}
