import java.util.ArrayList;
import java.util.Scanner;

public class Quiz extends FileAccess {


    //private Question questions[];
    private ArrayList<Question> questions = new ArrayList<>();

    private int right;
    private int wrong;

    private String name;
    private String answer;
    private String category;

    /**
     * Standardkonstruktor
     */
    public Quiz() {

        right = 0;
        wrong = 0;
        answer = "";

        setName(queryUser("Hallo, wie heißt du?"));
        validateName(getName());
        printCategories();
        output();
    }

    /**
     * Gibt die Fragen aus und überprüft auf richtige Antworten
     */
    private void output() {
        category = queryUser("Zu welcher Kategorie willst du Fragen beantworten?");
        questions = getQuestionsOfCategorie(category);
        System.out.println(questions.get(1));

        System.out.println("Erkannte Kategorie: " + category);
        System.out.println("Anzahl der Fragen in dieser Kategorie: " + questions.size());
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Frage " + (i + 1) + "/" + questions.size());
            answer = queryUser(q.toString());
            validateInput(answer);
            manageAnswer(q);
        }
        System.out.println("Du hast das Quiz beendet.");
        System.out.println("Du hast " + getRight() + " Fragen richtig und " + getWrong() + " Fragen falsch beantwortet.");
    }


    /**
     * @param q Die Frage die verwaltet werden soll
     */
    private void manageAnswer(Question q) {
        if (q.validateAnswer(answer)) {
            System.out.println("Das war richtig, " + name);
            incRight();
        } else {
            System.out.println("Das war leider falsch.");
            System.out.println("Die richtige Antwort wäre " + q.getAnswer() + " gewesen.");
            incWrong();
        }
    }

    /**
     * Überprüft ob der Name nur aus Buchstaben besteht.
     *
     * @param pName Der vom User eingegebene Name
     * @return True wenn der Name ok ist, false wenn nicht
     */
    private boolean validateName(String pName) {
        if (pName.matches("[A-z]+")) {
            return true;
        } else {
            System.out.println("Dies ist kein Name!! Namen bestehen aus einem Wort und nur Buchstaben.");
            System.exit(0);
            return false;
        }
    }

    private void validateInput(String pInput) {
        if (pInput.matches("[A-z\\s0-9]+")) {
            return;
        } else {
            System.out.println("Dies ist keine gültige Antwort!! Antworten bestehen aus Buchstaben oder Zahlen.");
            System.out.println("Das Quiz wird beendet, streng dich nächstes mal mehr an.");

            System.exit(0);
        }
    }

    /**
     * Gibt alle Kategorien in der Konsole aus
     */
    private void printCategories() {
        String[] categories = getCategories();
        String output = "Du kannst zwischen folgenden Kategorien wählen: ";
        for (String element : categories) {
            output = output + element + " ";
        }
        System.out.println(output);
    }

    /**
     * Fragt den User mit der Nachricht "Text" nach einem Input
     *
     * @param pText Die Nachricht mit der nach dem Input gefragt wird
     * @return Die Eingabe des Users
     */
    private String queryUser(String pText) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(pText);
        String input = scanner.nextLine();
        return input;
    }

    private String getName() {
        return name;
    }

    private void setName(String pName) {
        this.name = pName;
    }

    private int getRight() {
        return right;
    }

    private void incRight() {
        this.right++;
    }

    private int getWrong() {
        return wrong;
    }

    private void incWrong() {
        this.wrong++;
    }
}