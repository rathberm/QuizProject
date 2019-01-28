import java.io.File;

public class FileAccess {

    public FileAccess(String folderPath){
        System.out.println(System.getProperty("user.home"));
    }

    public String[] getCategories(){
        return null;
    }

    public Question getQuestionsOfCategorie(){
        return null;
    }

    public void createQuestion(){

    }

    public void listFilesForFolder(File folder) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
}
