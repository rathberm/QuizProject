import java.util.ArrayList;
import java.util.Scanner;

public class Quiz {
    private Question q1;
    private Question q2;
    private Question q3;

    private ArrayList<Question> questions = new ArrayList<>();

    private int right;
    private int wrong;

    private String name;
    private String answer;
    private String category;

    public Quiz() {
        initQuestions();
        setName(queryUser("Hallo, wie heißt du?"));
        validateName(getName());

        output();

        right = 0;
        wrong = 0;
        answer = "";
    }

    /**
     * Überprüft ob Fragen zu der eingegebenen Kategorie exisitieren
     *
     * @param input
     * @return True wenn ja, false wenn nein
     */
    private boolean checkCategory(String input) {
        boolean categoryExists = false;
        for (int i = 0; i < questions.size(); i++) {
            if (input.contains(questions.get(i).getCategory().toLowerCase())) {
                category = questions.get(i).getCategory();
                categoryExists = true;
            } else {
                categoryExists = false;
            }
        }
        return categoryExists;
    }

    /**
     * Legt die Fragen Objekte an und fügt sie der Liste hinzu
     */
    private void initQuestions() {
        q1 = new Question("Wie hoch ist der Eifelturm? ", "300", "Geographie");
        q2 = new Question("Was ist die Hauptstadt von Schweden?", "Stockholm", "Geographie");
        q3 = new Question("Wer war amerikanischer Präsident vor Obama?", "Bush", "Geographie");
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
    }

    /**
     * Gibt die Fragen aus und überprüft auf richtige Antworten
     */
    private void output() {
        if (checkCategory(queryUser("Zu welcher Kategorie willst du Fragen beantworten?").toLowerCase())) {
            System.out.println("Erkannte Kategorie: " + category);

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);

                System.out.println("Frage " + (i + 1) + "/" + questions.size());
                answer = queryUser(q.getQuestion()).toLowerCase();
                validateInput(answer);

                if (q.validateAnswer(answer)) {
                    System.out.println("Das war richtig, " + name);
                    incRight();
                } else {
                    System.out.println("Das war leider falsch..");
                    System.out.println("Die richtige Antwort wäre " + q.getAnswer() + " gewesen.");
                    incWrong();
                }
            }
            System.out.println("Du hast das Quiz beendet.");
            System.out.println("Du hast " + getRight() + " Fragen richtig und " + getWrong() + " Fragen falsch beantwortet");
        } else {
            System.out.println("Zu dieser Kategorie haben wir leider keine Fragen.");
            System.exit(0);
        }
    }

    private int findQuestion(String category) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getCategory().equals(category)) {
                return i;
            } else {
                return -1;
            }

        }
        return -1;
    }


    /**
     * Überprüft ob der Name nur aus Buchstaben besteht.
     *
     * @param name Der vom User eingegebene Name
     * @return True wenn der Name ok ist, false wenn nicht
     */
    private boolean validateName(String name) {
        if (name.matches("[A-z]+")) {
            return true;
        } else {
            System.out.println("Dies ist kein Name!! Namen bestehen aus einem Wort und nur Buchstaben.");
            System.exit(0);
            return false;
        }
    }

    private void validateInput(String input) {
        if (input.matches("[A-z\\s0-9]+")) {
            return;
        } else {
            System.out.println("Dies ist keine gültige Antwort!! Antworten bestehen aus Buchstaben oder Zahlen.");
            System.out.println("Das Quiz wird beendet, streng dich nächstes mal mehr an.");

            System.exit(0);
        }
    }

    /**
     * Fragt den User mit der Nachricht "Text" nach einem Input
     *
     * @param text Die Nachricht mit der nach dem Input gefragt wird
     * @return Die Eingabe des Users
     */
    private String queryUser(String text) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(text);
        String input = scanner.nextLine();
        return input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRight() {
        return right;
    }

    public void incRight() {
        this.right++;
    }

    public int getWrong() {
        return wrong;
    }

    public void incWrong() {
        this.wrong++;
    }
}