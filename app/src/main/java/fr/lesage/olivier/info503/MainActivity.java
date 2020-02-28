package fr.lesage.olivier.info503;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import listener.SystemListRandomAddListener;
import listener.SystemListRandomListViewListener;
import model.Element;
import model.ElementDeleteSystem;
import model.Sauvegarde;
import model.SystemListRandom;
import opengl.OpenGLMeshFromFile;

/**
 * Activité principale de l'application.
 *
 * Affiche la liste des "tirages" possibles.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation de la liste de "tirage" static.
        SystemListRandom.init(this);

        // Chargement de la sauvegarde
        Sauvegarde.load(this);

        // Initialisation du système de suppression
        ElementDeleteSystem.init();

        /*
            Affichage tu tutorial.
            Pour savoir si le tutoriel à déjà été affiché, un boolean est stocké dans les préférences de l'application.
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        // Si le boolean "tutorial_liste" n'éxiste pas ...
        if (prefs.getBoolean("tutorial_liste", true)) {
            // On affiche le tutoriel
            showTutoriel();
            // On met le boolean "tutorial_liste" a faux.
            edit.putBoolean("tutorial_liste", false);
            edit.commit();
        }

        // Gestion de l'interface
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.addSystemListButton);
        add.setOnClickListener(new SystemListRandomAddListener(this));

        ListView listView = (ListView) findViewById(R.id.systemListListView);
        listView.setAdapter(SystemListRandom.LIST.getAdapter());
        SystemListRandomListViewListener listener = new SystemListRandomListViewListener(this);
        listView.setOnItemClickListener(listener);
        listView.setOnItemLongClickListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // On sauvegarde le listes.
        Sauvegarde.save(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_voir_tuto:
                // Bouton permettant d'afficher le tutoriel.
                showTutoriel();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Méthode permettant de lancer l'activité "ListActivity".
     * @param position Index de la liste à éditer.
     */
    public void showActivityEditList(int position) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    /**
     * Méthode permettant de lancer l'activité "TirageActivity".
     * @param res Résultat du tirage.
     */
    public void showActivityTirage(Element res) {
        Intent intent = new Intent(this, TirageActivity.class);
        intent.putExtra("nom_element", res.getNom());
        startActivity(intent);
    }

    /**
     * Méthode permettant de lancer l'activité "TutorialActivity"?
     */
    private void showTutoriel() {
        Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
        intent.putExtra("subject", TutorialActivity.TUTORIAL_START);
        startActivity(intent);
    }
}
