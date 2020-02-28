package model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe permettant de lire et écrire dans un fichier.
 */
public class FileIOHelper {
    /**
     * Méthode permettant d'écrire dans une fichier.
     * @param context Activité dans laquelle est appelée la méthode.
     * @param fileName Nom du fichier.
     * @param text String à écrire.
     */
    public static final void writeToFile(Context context, String fileName, String text) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de lire dans un fichier.
     * @param context Activité dans laquelle est appelée la méthode.
     * @param fileName Nom du fichier.
     * @return Le contenu du fichier.
     */
    public static final String readFromFile(Context context, String fileName) {
        String res = "";
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while((lines = bufferedReader.readLine()) != null) {
                res += lines;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Méthode permettant de lire dans un fichier stocké dans les ressources.
     * @param context Activité dans laquelle est appelée la méthode.
     * @param id ID du fichier.
     * @return Le contenu du fichier.
     */
    public static final String readFromRAW(Context context, int id) {
        String res = "";
        try {
            InputStream inputStream = context.getResources().openRawResource(id);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while((lines = bufferedReader.readLine()) != null) {
                res += lines + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
