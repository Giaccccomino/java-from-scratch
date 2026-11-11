public class EnumR { enum Conf { INSTANCE }
 public static void main(String[] a) throws Exception {
  var k = Conf.class.getDeclaredConstructors()[0]; k.setAccessible(true);
  try { k.newInstance("ANOTHER", 1); } catch (Exception e) { System.out.println(e); } } }
