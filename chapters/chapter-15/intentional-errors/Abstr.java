public class Abstr { public static void main(String[] a) { Content c = new Content(); } }
abstract class Content { abstract long price(); void f(); }
class Short extends Content { }
class Empty { abstract void g(); }
