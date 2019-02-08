import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Quiz kümmert sich um alle Ausgaben in der Konsole
 *
 * @author Moritz Rathberger
 */
public class Quiz {

    private ArrayList<Question> questions = new ArrayList<>();

    private int right;
    private int wrong;
    private int amountQuestions;

    private String answer;
    private String category;

    private FileAccess fileAccess;
    private ShortAnswers shortAnswers;

    /**
     * Standardkonstruktor
     */
    public Quiz() {
        right = 0;
        wrong = 0;
        answer = "";
        fileAccess = new FileAccess();
        shortAnswers = new ShortAnswers();

        System.out.println("Hallo, willkommen bei unserem Quiz!");
        firstCheck();
        printCategories();
        category = askCategory();
        amountQuestions = askAmount();
        output();

        System.out.println("Du hast das Quiz beendet.");
        System.out.println("Du hast " + getRight() + " Fragen richtig und " + getWrong() + " Fragen falsch beantwortet.");
    }

    /**
     * Gibt die Fragen aus und überprüft auf richtige Antworten
     */
    private void output() {
        int rightAnswered = 0;

        if (category.toLowerCase().equals("zufaellig")) {
            questions = mixQuestions();
        } else {
            questions = fileAccess.getQuestionsOfCategorie(category);
        }

        //Sortiert die Fragen in der Liste zufällig neu
        Collections.shuffle(questions);

        for (int i = 0; i < amountQuestions; i++) {
            Question q = questions.get(i);

            System.out.println("Frage " + (i + 1) + "/" + (amountQuestions));
            answer = queryUser(q.getQuestion());
            rightAnswered += manageAnswer(q);

        }
        //Updated die Einträge in der entsprechenden .txt
        fileAccess.changeStatsOfQuestionsInCategorie(questions, false);

        fileAccess.addHistoryEntry(rightAnswered, amountQuestions);
    }

    /**
     * @param q Die Frage die verwaltet werden soll
     */
    private int manageAnswer(Question q) {
        if (answer.equals("exit")) {
            firstCheck();
        } else {
            q.increaseQuestioned();
            if (q.validateAnswer(answer)) {
                System.out.println("Das war richtig.");
                System.out.println("---------------------------------------------------------------------------------");
                q.increaseRightAnswered();
                incRight();
                return 1;
            } else {
                System.out.println("Das war leider falsch.");
                System.out.println(q.getAnswer());
                System.out.println("---------------------------------------------------------------------------------");
                incWrong();
                return 0;
            }
        }
        return 0;
    }

    private int askAmount() {
        String length;
        System.out.println("Erkannte Kategorie: " + category);
        length = queryUser("Wie viele Fragen möchtest du beantworten?");
        if (length.equals("exit")) {
            firstCheck();
        }

        if (!length.matches("[-0-9]+")) {
            System.out.println("Das ist keine Zahl, versuchs nochmal.");
            return askAmount();
        } else if (Integer.parseInt(length) > 10) {
            System.out.println("Die Zahl ist zu groß, gib eine Zahl zwischen 0 und 10 ein.");
            return askAmount();
        } else if (Integer.parseInt(length) <= 0) {
            System.out.println("Diese Zahl ist zu klein, gib eine Zahl größer null ein.");
            return askAmount();
        } else {
            return Integer.parseInt(length);
        }
    }


    /**
     * Frägt nach der Kategorie und überprüft ob die Eingabe eine Kategorie ist.
     *
     * @return Die eingegebene Kategorie.
     */
    private String askCategory() {
        String cat = queryUser("Zu welcher Kategorie möchtest du Fragen beantworten?");

        if (cat.equals("exit")) {
            firstCheck();
        }
        if (isCategory(cat)) {
            return cat;
        } else {
            System.out.println("Das ist keine gültige Kategorie, versuchs nochmal.");
            return askCategory();
        }
    }

    /**
     * Verwaltet die User Eingabe von neuen Fragen
     */
    private void createOwnQuestion() {
        String pCategory;
        String question;
        String answerWord;
        String answerSentence;
        String output = "";
        String[] categories = fileAccess.getCategories();
        System.out.println("Ok, zuerst brauchen wir eine Kategorie.");
        System.out.println("Du kannst einer bereits bestehenden Kategorie Fragen hinzufügen oder eine neue Kategorie erstellen.");
        System.out.println("Bestehende Kategorien: ");

        //Gibt die Namen der Kategorien aus
        for (String element : categories) {
            output = output + element + " ";
        }
        System.out.println(output);
        pCategory = queryUser("Wunschkategorie: ");
        question = queryUser("Bitte formuliere jetzt deine Frage: ");
        answerWord = queryUser("Jetzt musst du die Antwort für deine Frage eingeben: ");
        answerSentence = queryUser("Bitte gib jetzt den Antwortsatz ein: ");

        String str = queryUser("Willst du die Frage " + question + " der Kategorie " + pCategory + " mit der Antwort " + answerWord + " und dem Antwortsatz " + answerSentence + " wirklich speichern?(ja/nein)").toLowerCase();

        if (str.equals("ja") && str.matches("[A-z]+")) {
            fileAccess.createQuestion(pCategory, question, answerWord, answerSentence);
            System.out.println("Frage erfolgreich gespeichert.");
            firstCheck();
        } else {
            System.out.println("Ok, Frage erstellen wird abgebrochen.");
            firstCheck();
        }
    }

    private ArrayList<Question> smartQuestions() {
        ArrayList<Question> arrl = fileAccess.getQuestionsOfCategorie(category);

        for (int i = 0; i < arrl.size(); i++) {

        }

return arrl;
    }

    /**
     * Verbindet alle Kategorien in einer Liste
     *
     * @return Eine Liste die alle Fragen der Kategorien Geschichte und Allgemeinwissen enthält
     */
    private ArrayList<Question> mixQuestions() {
        String[] categories = fileAccess.getCategories();
        ArrayList<Question> allQuestions = fileAccess.getQuestionsOfCategorie("Geschichte");

        for (int i = 0; i < categories.length; i++) {
            ArrayList<Question> notAllQuestions = fileAccess.getQuestionsOfCategorie(categories[i]);
            allQuestions.addAll(notAllQuestions);
        }
        Collections.shuffle(allQuestions);

        return allQuestions;
    }

    /**
     * Überprüft ob der übergebene String eine Kategorie ist oder nicht
     *
     * @param pCategory
     * @return True wenn ja, false wenn nicht.
     */
    private boolean isCategory(String pCategory) {
        boolean r = false;
        String[] categories = fileAccess.getCategories();
        for (int i = 0; i < categories.length; i++) {
            if (pCategory.equals(categories[i]) || pCategory.equals("zufaellig")) {
                r = true;
            }
        }
        return r;
    }

    /**
     * Gibt alle Kategorien in der Konsole aus
     */
    private void printCategories() {
        String[] categories = fileAccess.getCategories();
        String output = "Du kannst zwischen folgenden Kategorien wählen: ";
        for (String element : categories) {
            output = output + element + " ";
        }
        output = output + "Zufaellig";
        System.out.println(output);
    }

    /**
     * Frägt den Benutzer ob er spielen will, wenn nein wird das Programm beendet.
     */
    private void firstCheck() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Hauptmenue:");
        String answer = queryUser("Willst du Fragen beantworten, selber Fragen erstellen, Fragen stellen, Historie einsehen oder nichts von alldem??(beantworten/erstellen/stellen/historie/nichts)").toLowerCase();
        if (answer.matches("[A-z]+")) {
            if (answer.contains("beantworten")) {
                System.out.println("Ok, los gehts!");
                System.out.println("Du kommst durch die Eingabe von \"exit\" wieder zurueck zur Auswahl.");
            } else if (answer.contains("nichts")) {
                System.out.println("Ok, das Programm wird beendet.");
                System.exit(0);
            } else if (answer.contains("erstellen")) {
                createOwnQuestion();
            } else if (answer.contains("stellen")) {
                askWolfram();
            } else if (answer.contains("historie")){
                showHisto();
            } else if (answer.equals("exit")) {
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

    private void askWolfram() {
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Jetzt kannst du Fragen stellen die von Wolfram Alpha beantwortet werden.");
        System.out.println("Aber Achtung: Du musst die fragen auf Englisch stellen!");
        System.out.println("Alternativ kommst du durch die eingabe von \"exit\" wieder zurueck zur Auswahl.");
        String userInput = queryUser("Was möchtest du wissen?");
        if (userInput.equals("exit")) {
            firstCheck();
        } else {
            String answer = shortAnswers.queryWolfram(userInput);
            if (!answer.equals("-1")) {
                System.out.println(answer);
            }
            answer = answer.replaceAll("[,-/#]", "");
            fileAccess.createQuestion("WolframShortAnswer", userInput, answer, answer);
            askWolfram();
        }
    }

    private void showHisto(){
        ArrayList<String> histo = fileAccess.getHistory();
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------");
        for (int i = 0; i < histo.size(); i++){
            System.out.println(histo.get(i));
        }
        System.out.println();
        firstCheck();
    }

    private boolean sort(){
        Question temp;
        boolean sorted = true;

        for (int i = 0; i < questions.size() - 1; i++){
            if (questions.get(i).getPercentage() > questions.get(i+1).getPercentage()){
                temp = questions.get(i+1);
                questions.set(i+1, questions.get(i));
                questions.set(i, temp);
                sorted = false;
            }
        }
        return sorted;
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

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}