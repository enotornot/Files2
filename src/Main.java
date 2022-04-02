import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    private static final String pathToSave = "D://Games/savegames/";
    private static List<String> listFilesInDirectory = new ArrayList<String>();

    public static void main(String[] args) {


        GameProgress save1 = new GameProgress(515, 1532, 70, 5.1);
        GameProgress save2 = new GameProgress(15, 60, 70, 0.5);
        GameProgress save3 = new GameProgress(1000, 2700, 71, 10);


        saveGame("save1.date", save1);
        saveGame("save2.date", save2);
        saveGame("save3.date", save3);


        zipFiles(pathToSave, listFilesInDirectory);


        filesDelNoZip(listFilesInDirectory);

    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(pathToSave + path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);

            listFilesInDirectory.add(pathToSave + path);
            System.out.println("Файл " + path + " добавлен в список сохранений.");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> listFilesInDirectory) {
        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream(pathToSave + "gameProgressSave.zip"))) {
            for (String currentSave : listFilesInDirectory) {
                File fileToZip = new File(currentSave);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл " + currentSave + " добавлен в архив.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void filesDelNoZip(List<String> listFilesInDirectory) {
        for (String currentSave : listFilesInDirectory) {
            File fileToDel = new File(currentSave);
            if(fileToDel.delete()){
                System.out.println("Удален файл " + fileToDel + ".");
            } else {
                System.out.println("Не удалось удалить файл " + fileToDel + ".");
            }
        }
    }
}