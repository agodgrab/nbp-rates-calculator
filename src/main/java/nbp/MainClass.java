package nbp;

public class MainClass {

    public static void main(String[] args) {

        Request.INSTANCE.setCurrency(args[0]);
        Request.INSTANCE.setStart(args[1]);
        Request.INSTANCE.setEnd(args[2]);

        Runner runner = new Runner();
        runner.run();
    }
}
