public class Diamond {
    public static void main(String[] args) { System.out.println(new BoxOffice().greeting()); }
}
interface Register { default String greeting() { return "Welcome to the register"; } }
interface Bar { default String greeting() { return "Welcome to the bar"; } }
class BoxOffice implements Register, Bar {
    @Override public String greeting() { return Register.super.greeting() + " and " + Bar.super.greeting().toLowerCase(); }
}
