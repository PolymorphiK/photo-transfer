import views.GUIView;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0 && args[0].startsWith("~")) {
            args[0] = System.getProperty("user.home") + args[0].substring(1);
        }
        new Thread(new Driver(args)).start();

        //new App().content(new GUIView());
    }
}
