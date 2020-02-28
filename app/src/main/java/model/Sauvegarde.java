package model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.lesage.olivier.info503.R;

/**
 * Classe permettant de sauvegarder et charger les listes.
 */
public class Sauvegarde {
    /**
     * Boolean permettant de sacoir si il est nécessaire de sauvegardé.
     */
    private static  boolean haveToBeSaved = false;

    /**
     * Méthode permettant de sauvegarder les listes.
     * @param context Activité dnas laquelle est appelée la méthode.
     */
    public static void save(Context context) {
        // Si il est nécessaire de sauvegarder ...
        if (Sauvegarde.haveToBeSaved) {
            if (SystemListRandom.LIST.length() > 0) FileIOHelper.writeToFile(context, context.getString(R.string.save_file_path), SystemListRandom.LIST.toString());
            else FileIOHelper.writeToFile(context, context.getString(R.string.save_file_path), "");
            Sauvegarde.haveToBeSaved = false;
        }
    }

    /**
     * Méthode permettant de dire qu'il est nécessaire de sauvegarder.
     */
    public static void haveToBeSaved() {
        haveToBeSaved = true;
    }

    /**
     * Méthode permettatn de charger les listes.
     *
     * Si aucune liste n'est sauvegardé, alors des listes sont crées automatiquement.
     * @param app
     */
    public static void load(Activity app) {
        // On supprime toutes les listes qu'il y a dans la mémoire.
        SystemListRandom.LIST.clear();

        Context context = app.getApplicationContext();
        // On récupère le contenu de la sauvegarde.
        String file = FileIOHelper.readFromFile(context, context.getString(R.string.save_file_path));
        // Si le fichier était pas vide ...
        if (!file.isEmpty()) {
            // On parcours le comme un fichier JSON.
            try {
                JSONArray jsonArray = new JSONArray(file);
                JSONArray jsonArrayElement;
                JSONObject jsonObject;
                JSONObject jsonElement;
                ListeElement list;

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    list = new ListeElement(jsonObject.getString("nom"), app);
                    jsonArrayElement = jsonObject.getJSONArray("liste");

                    for (int j = 0; j < jsonArrayElement.length(); j++) {
                        jsonElement = jsonArrayElement.getJSONObject(j);
                        list.addElement(new Element(jsonElement.getString("nom"), jsonElement.getInt("coeff")));
                    }

                    SystemListRandom.LIST.addList(list);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else  {
            ListeElement list = new ListeElement("Dé", app);
            list.addElement(new Element("1", 1));
            list.addElement(new Element("2", 1));
            list.addElement(new Element("3", 1));
            list.addElement(new Element("4", 1));
            list.addElement(new Element("5", 1));
            list.addElement(new Element("6", 1));
            SystemListRandom.LIST.addList(list);

            list = new ListeElement("Pièce", app);
            list.addElement(new Element("Pile", 1));
            list.addElement(new Element("Face", 1));
            SystemListRandom.LIST.addList(list);

            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(app).edit();
            edit.putBoolean("tutorial_liste", true);
            edit.putBoolean("tutorial_ele", true);
            edit.commit();
        }
    }
}
