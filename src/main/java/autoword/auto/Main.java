package autoword.auto;

public class Main {
    public static void main(String[] args) {

        Automaton automaton = new Automaton("./src/main/resources/example.docx", -65536, "./src/main/resources");
        automaton.start();
    }
}
