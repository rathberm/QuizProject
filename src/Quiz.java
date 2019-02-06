import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz extends FileAccess {

    private ArrayList<Question> questions = new ArrayList<>();

    private int right;
    private int wrong;
    private int amountQuestions;

    private String answer;
    private String category;

    /**
     * Standardkonstruktor
     */
    public Quiz() {
        right = 0;
        wrong = 0;
        answer = "";

        System.out.println("Hallo, willkommen bei unserem Quiz!");
        firstCheck();
        printCategories();
        category = askCategory();
        amountQuestions = askAmount();
        output();
    }

    /**
     * Gibt die Fragen aus und überprüft auf richtige Antworten
     */
    private void output() {
        boolean reset = false;
        int count = 1;

        questions = getQuestionsOfCategorie(category);
        //Sortiert die Fragen in der Liste zufällig neu
        Collections.shuffle(questions);

        for (int i = 0; i < amountQuestions; i++) {
            //Wenn der User mehr Fragen beantworten will als es Fragen in einer Kategorie gibt
            //wird i zurückgesetzt sobald i einmal bis questions.size durchgelaufen ist
            if (amountQuestions > questions.size() && i == questions.size() && reset == false) {
                i = amountQuestions - questions.size();
                reset = true;
            }
            //TODO Arraylist is only 9 elements long
            Question q = questions.get(i);
            System.out.println("Frage " + count + "/" + (amountQuestions));
            answer = queryUser(q.getQuestion());
            validateInput(answer);
            manageAnswer(q);
            count++;
        }
        System.out.println("Du hast das Quiz beendet.");
        System.out.println("Du hast " + getRight() + " Fragen richtig und " + getWrong() + " Fragen falsch beantwortet.");
    }

    private int askAmount() {
        String lenght;
        System.out.println("Erkannte Kategorie: " + category);
        lenght = queryUser("Wie viele Fragen möchtest du beantworten?");
        return Integer.parseInt(lenght);
    }

    //Funktioniert noch nicht
    private ArrayList<Question> mixQuestions() {
        double r;
        ArrayList<Question> geschichte = getQuestionsOfCategorie("Geschichte");
        ArrayList<Question> allgemeinwissen = getQuestionsOfCategorie("Allgemeinwissen");
        ArrayList<Question> mixed = new ArrayList<>();
        int cG = 0;
        int cA = 0;
        while (cG <= geschichte.size()) {
            r = Math.random();
            if (r <= 0.5 && cG <= geschichte.size()) {
                mixed.add(geschichte.get(cG));
                cG++;
            } else if (r >= 0.5 && cA <= allgemeinwissen.size()) {
                mixed.add(allgemeinwissen.get(cA));
                cA++;
            }
        }
        return mixed;
    }

    /**
     * @param q Die Frage die verwaltet werden soll
     */
    private void manageAnswer(Question q) {
        if (q.validateAnswer(answer)) {
            System.out.println("Das war richtig.");
            incRight();
        } else {
            System.out.println("Das war leider falsch.");
            System.out.println(q.getAnswer());
            incWrong();
        }
    }

    private void validateInput(String pInput) {
        if (pInput.matches("[A-z\\s0-9.]+")) {
            return;
        } else {
            System.out.println("Dies ist keine gültige Antwort!! Antworten bestehen aus Buchstaben oder Zahlen.");
            System.out.println("Das Quiz wird beendet, streng dich nächstes mal mehr an.");
            System.exit(0);
        }
    }

    /**
     * Frägt nach der Kategorie und überprüft ob die Eingabe eine Kategorie ist.
     *
     * @return Die eingegebene Kategorie.
     */
    private String askCategory() {
        String cat = queryUser("Zu welcher Kategorie möchtest du Fragen beantworten?");
        if (isCategory(cat)) {
            return cat;
        } else {
            System.out.println("Das ist keine gültige Kategorie, versuchs nochmal.");
            return askCategory();
        }
    }

    /**
     * Überprüft ob der übergebene String eine Kategorie ist oder nicht
     *
     * @param pCategory
     * @return True wenn ja, false wenn nicht.
     */
    private boolean isCategory(String pCategory) {
        boolean r = false;
        String[] categories = getCategories();
        for (int i = 0; i < categories.length; i++) {
            if (pCategory.equals(categories[i])) {
                r = true;
            }
        }
        return r;
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
     * Frägt den Benutzer ob er spielen will, wenn nein wird das Programm beendet.
     */
    private void firstCheck() {
        String answer = queryUser("Willst du Fragen beantworten?(Ja/Nein)").toLowerCase();
        if (answer.matches("[A-z]+")) {
            if (answer.contains("ja")) {
                System.out.println("Ok, los gehts!");
            } else if (answer.contains("nein")) {
                System.out.println("Ok, das Programm wird beendet.");
                System.exit(0);
            } else {
                System.out.println("Das war keine gültige Antwort, versuchs nochmal.");
                firstCheck();
            }
        } else {
            System.out.println("Das war keine gültige Antwort, versuchs nochmal.");
            firstCheck();
        }
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