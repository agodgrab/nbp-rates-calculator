package nbp;

public class MainClass {

    public static void main(String[] args) {

        Validator validator = new Validator(args);
        validator.validate();

        Runner runner = new Runner();
        runner.run();
    }
}
