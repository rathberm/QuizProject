import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {
    private String folderPath;

    /**
     * Standardkonstruktor
     */
    public FileAccess(){
        folderPath = System.getProperty("user.home") + "\\AppData\\Roaming\\QuizProject";
        getQuestionsOfCategorie("C1");
        firstCheck();
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
    public Question[] getQuestionsOfCategorie(String pCategorie){
        ArrayList<String> fileContent = getFileContent(pCategorie);
        Question[] questions = getQuestionsOfFileContent(fileContent);

        return questions;
    }

    public void createQuestion(){

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
     * @param fileContent Dateiinhalt
     * @return Fragenarray
     */
    private Question[] getQuestionsOfFileContent(ArrayList<String> fileContent){
        for (int i = 0; i < fileContent.size(); i++){

        }

        return null;
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
    public File[] getFilesOfFolder() {
        File folder = new File(folderPath);
        if (folder.isDirectory()){
            return folder.listFiles();
        } else {
            System.out.println("Error in: FileAccess.ListFilesInFolder");
            System.exit(0);
            return null;
        }
    }
}
