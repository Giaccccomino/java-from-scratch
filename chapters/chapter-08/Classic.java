public class Classic {
    public static void main(String[] args) {
        for (int day = 1; day <= 7; day++) {
            String name;
            switch (day) {
                case 1: name = "Monday"; break;
                case 2: name = "Tuesday"; break;
                case 3: name = "Wednesday"; break;
                case 4: name = "Thursday"; break;
                case 5: name = "Friday"; break;
                case 6:
                case 7: name = "weekend"; break;
                default: name = "?";
            }
            System.out.print(name + " ");
        }
        System.out.println();
        int screen = 2;
        switch (screen) {
            case 1:
                System.out.println("Screen 1: Dolby Atmos");
            case 2:
                System.out.println("Screen 2: 3D");
            case 3:
                System.out.println("Screen 3: small");
            default:
                System.out.println("End of list");
        }
    }
}
