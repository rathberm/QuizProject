import java.util.Arrays;
import java.util.Scanner;

public class Quiz extends FileAccess {


    private Question questions[];

    private int right;
    private int wrong;

    private String name;
    private String answer;
    private String category;

    public Quiz() {


        right = 0;
        wrong = 0;
        answer = "";

        setName(queryUser("Hallo, wie heißt du?"));
        validateName(getName());
        printCategorys();
        output();

       // questions = getQuestionsOfCategorie(category);

    }

    /**
     * Überprüft ob Fragen zu der eingegebenen Kategorie exisitieren
     *
     * @param input
     * @return True wenn ja, false wenn nein
     */
    /*
    private boolean checkCategory(String input) {
        for (int i = 0; i < questions.size(); i++) {
            if (input.contains(questions.get(i).getCategory().toLowerCase())) {
                category = questions.get(i).getCategory();
                System.out.println("ye");
                return true;
            }
        }
        return false;
    }
    */


    /**
     * Gibt die Fragen aus und überprüft auf richtige Antworten
     */
    private void output() {
        category = queryUser("Zu welcher Kategorie willst du Fragen beantworten?").toLowerCase();
        questions = getQuestionsOfCategorie(category);
            System.out.println("Erkannte Kategorie: " + category);
            System.out.println("Anzahl der Fragen in dieser Kategorie: " + questions.length);
            for (int i = 0; i < questions.length; i++) {
                Question q = questions[i];
                System.out.println("Frage " + (i + 1) + "/" + questions.length);
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
     * Gibt alle Kategorien in der Konsole aus
     */
    private void printCategorys() {
        String[] categories = getCategories();
        String output = "Du kannst zwischen folgenden Kategorien wählen: " + Arrays.toString(categories);

        System.out.println(output);
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

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
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