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
        getCategories();
    }

    /**
     * Konstruktor, bei welchem ein anderer Dateispeicherort gewählt werden kann
     * @param folderPath neuer Dateispeicherort
     */
    public FileAccess(String pFolderPath){
        folderPath = pFolderPath;
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

    public Question getQuestionsOfCategorie(String pCategorie){
        File file = new File("");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                // process the line.
            }
            // line is not visible here.
        } catch(Exception e){
            System.out.println("Error in: FileAccess.getQuestionsOfCategorie");
            System.exit(0);
            return null;
        }

        return null;
    }

    public void createQuestion(){

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
