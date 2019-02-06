import java.io.*;
import java.util.ArrayList;

public class FileAccess {
    private String folderPath;

    /**
     * Standardkonstruktor
     */
    public FileAccess(){
        folderPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "questions";
        firstCheck();
        //getQuestionsOfCategorie("bla0");
        //createQuestion("bla0", "bla1", "bla2", "bla3");
    }

    /**
     * Konstruktor, bei welchem ein anderer Dateispeicherort gewählt werden kann
     * @param pFolderPath neuer Dateispeicherort
     */
    public FileAccess(String pFolderPath){
        folderPath = pFolderPath;
        firstCheck();
    }

    /**
     * Checkt ob der entsprechende Ordner mit den Fragen existiert, falls dieser existiert wird auch überprüft ob überhaupt Kategorien vorhanden sind.
     */
    private void firstCheck(){
        boolean error = false;
        if (folderPath.isEmpty() || folderPath.equals("") || folderPath == null){
            error = true;
        }
        if (new File(folderPath).exists() == false){
            error = true;
        }
        if (getFilesOfFolder().length == 0){
            error = true;
        }

        if (error == true){
            System.out.println("Error in: FileAccess.firstCheck => Failed");
            System.exit(0);
        }
    }

    /**
     * Gibt eine Liste der bereits existierenden / verfügbaren Kategorien zurück
     * @return liste an zu wählenden Kategorien
     */
    public String[] getCategories(){
        File[] files = getFilesOfFolder();
        String[] categories = new String[files.length];

        for (int i = 0; i < files.length; i++){
            if (files[i].getName().contains(".")){
                categories[i] = files[i].getName().replaceFirst("[.][^.]+$", "");
            } else {
                categories[i] = files[i].getName();
            }
        }

        return categories;
    }

    /**
     * Gibt die Fragen zur entsprechenden Kategorie zurück
     * @param pCategorie Kategorie
     * @return Fragen zur Kategorie
     */
    //TODO Fix: The returned ArrayList holds only 9 elements instead of 10
    public ArrayList<Question> getQuestionsOfCategorie(String pCategorie){
        ArrayList<String> fileContent = getFileContent(pCategorie);
        return getQuestionsOfFileContent(fileContent, pCategorie);
    }

    /**
     * Erstellt einen neuen Eintrag mit einer neuen Frage
     */
    public void createQuestion(String pCategorie, String pQuestion, String pAnswerWords, String pAnswerSentence){
        try{
            File file = getFileOfCategorie(pCategorie);
            if (file == null){
                file = new File(folderPath + System.getProperty("file.separator") +  pCategorie + ".txt");
                file.createNewFile();

                FileWriter fw = new FileWriter(file, true);
                fw.write( "#0/0-" + pQuestion + System.lineSeparator() + pAnswerWords + System.lineSeparator() + pAnswerSentence + System.lineSeparator());
                fw.close();
            } else {
                FileWriter fw = new FileWriter(file, true);
                fw.write( System.lineSeparator() + "#0/0-" + pQuestion + System.lineSeparator() + pAnswerWords + System.lineSeparator() + pAnswerSentence + System.lineSeparator());
                fw.close();
            }
        } catch (Exception e){
            System.out.println("Error in: FileAccess.createQuestion");
            System.exit(0);
            return;
        }

    }

    /**
     * Gibt den Inhalt der Datei zurück, welche mit der entsprechenden Kategorie in relation steht
     * @param pCategorie Kategorie
     * @return Dateiinhalt als Array (Zeile für Zeile)
     */
    private ArrayList<String> getFileContent(String pCategorie){
        File file = getFileOfCategorie(pCategorie);
        ArrayList<String> fileContent = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                fileContent.add(line);
            }
        } catch(Exception e){
            System.out.println("Error in: FileAccess.getQuestionsOfCategorie");
            System.exit(0);
        }
        return fileContent;
    }

    /**
     * Konvertiert den dateiinhalt zu Objekten des Typs Question
     * @param pFileContent Dateiinhalt
     * @return Fragenarray
     */
    private ArrayList<Question> getQuestionsOfFileContent(ArrayList<String> pFileContent, String pCategorie){
        ArrayList<Question> questions = new ArrayList<>();
        String question = "";
        String answer = "";
        String[] answerWords = new String[]{""};
        int rightAnswered = 0;
        int questioned = 0;

        for (int i = 0; i < pFileContent.size(); i++){
            if (i % 4 == 0){ //Zeile 1
                question = getQuestion(pFileContent.get(i));
                rightAnswered = getRightAnswers(pFileContent.get(i));
                questioned = getQuestioned(pFileContent.get(i));
            } else if (i % 4 == 1){ //Zeile 2
                answerWords = pFileContent.get(i).split(" ");
            } else if (i % 4 == 2){ //Zeile 3
                answer = pFileContent.get(i);
            } else { //leerzeile
                questions.add(new Question(pCategorie, question, answerWords, answer, rightAnswered, questioned));
            }
        }
        questions.add(new Question(pCategorie, question, answerWords, answer, rightAnswered, questioned));

        return questions;
    }

    private String getQuestion(String pStr) {
        int qBeginning = -1;
        for(int i = 0; i < pStr.length(); i++){
            if (pStr.charAt(i) == '-'){
                qBeginning = i + 1;
            }
        }

        if (qBeginning == -1){
            System.out.println("Error in: FileAccess.getQuestion");
            System.exit(0);
            return null;
        } else {
            return pStr.substring(qBeginning);
        }
    }

    private int getRightAnswers(String pStr){
        int qEnd = -1;
        for(int i = 0; i < pStr.length(); i++){
            if (pStr.charAt(i) == '/'){
                qEnd = i;
            }
        }

        if (qEnd == -1){
            System.out.println("Error in: FileAccess.getRightAnswer[1]");
            System.exit(0);
            return -1;
        } else {
            try {
                return Integer.parseInt(pStr.substring(1, qEnd));
            } catch (Exception e){
                System.out.println("Error in: FileAccess.getRightAnswer[2]");
                System.exit(0);
                return -1;
            }
        }
    }

    private int getQuestioned(String pStr){
        int qBeginning = -1;
        for(int i = 0; i < pStr.length(); i++){
            if (pStr.charAt(i) == '/'){
                qBeginning = i + 1;
            }
        }

        int qEnd = -1;
        for(int i = 0; i < pStr.length(); i++){
            if (pStr.charAt(i) == '-'){
                qEnd = i;
            }
        }

        if (qEnd == -1){
            System.out.println("Error in: FileAccess.getQuestioned[1]");
            System.exit(0);
            return -1;
        } else {
            try {
                return Integer.parseInt(pStr.substring(qBeginning, qEnd));
            } catch (Exception e){
                System.out.println("Error in: FileAccess.getQuestioned[2]");
                System.exit(0);
                return -1;
            }
        }
    }

    /**
     * Gibt die Datei zurück, welche zur Kategorie passt
     * @param pCategorie Kategorie
     * @return Datei der entsprechenden Kategorie
     */
    private File getFileOfCategorie(String pCategorie){
        for (int i = 0; i < getCategories().length; i++){
            if (pCategorie.toLowerCase().equals(getCategories()[i].toLowerCase())){
                return getFilesOfFolder()[i];
            }
        }

        return null;
    }

    /**
     * Gibt eine Liste der im Ordner befindlichen Dateien zurück
     * @return Array der Dateinamen
     */
    private File[] getFilesOfFolder() {
        File folder = new File(folderPath);
        if (folder.isDirectory()){
            return folder.listFiles();
        } else {
            System.out.println("Error in: FileAccess.getFilesOfFolder");
            System.exit(0);
            return null;
        }
    }
/*
    public boolean changeStatsOfQuestionInCategorie(ArrayList<Question> questions){
        boolean succeeded = true;

        for (int i = 0; i < questions; i ++){
            questions.get(i);
        }

        return succeeded;
    }
    */
}
