package fr.lesage.olivier.info503;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import listener.AddElementButtonListener;
import listener.ConfirmationElementAnnulerListener;
import listener.DialogAnnulerListener;
import listener.DialogSupprimerListListener;
import listener.ListActivityBackButtonListener;
import listener.ListListViewListener;
import listener.DialogListRenommerListener;
import model.ConfirmationPopUp;
import model.ElementDeleteSystem;
import model.ListeElement;
import model.Sauvegarde;
import model.SystemListRandom;

/**
 * Acitivité permettant d'affiché le contenu d'une liste aisin que le modifier.
 */
public class ListActivity extends AppCompatActivity implements Observer {
    /**
     * Liste d'élément qui va être affichée et modifiée.
     */
    private ListeElement list;

    /**
     * Popup de confirmation de suppression.
     */
    private ConfirmationPopUp popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*
            Affichage tu tutorial.
            Pour savoir si le tutoriel à déjà été affiché, un boolean est stocké dans les préférences de l'application.
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        // Si le boolean "tutorial_ele" n'éxiste pas ...
        if (prefs.getBoolean("tutorial_ele", true)) {
            // On affiche le tutoriel
            showTutoriel();
            // On met le boolean "tutorial_ele" a faux.
            edit.putBoolean("tutorial_ele", false);
            edit.commit();
        }

        // On récupère la liste à affiché et modifié.
        Intent intent = getIntent();
        this.list = SystemListRandom.LIST.getList(intent.getIntExtra("position", 0));

        /*
            On ajoute l'activité en tant qu'observatrice du système de suppression.
            Cela permet de changer la vue lorsque l'on selectionne des élèments.
          */
        ElementDeleteSystem.addObserverStatic(this);
        ElementDeleteSystem.setActivity(this);

        // Gestion de l'interface
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle(list.getNom());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new ListActivityBackButtonListener(this.list, this));

        ListView listView = (ListView) findViewById(R.id.element_listView);
        listView.setAdapter(list.getAdapter());

        ListListViewListener listener = new ListListViewListener(this.list, this);
        listView.setOnItemClickListener(listener);
        listView.setOnItemLongClickListener(listener);

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.floating_button_add_element);
        add.setOnClickListener(new AddElementButtonListener(this, this.list));

        Button annuler = findViewById(R.id.confirmation_button);
        annuler.setOnClickListener(new ConfirmationElementAnnulerListener());

        this.popUp = new ConfirmationPopUp((LinearLayout) findViewById(R.id.confirmation_layout), annuler, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // On sauvegarde les modifications faites sur la liste.
        Sauvegarde.save(getApplicationContext());
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        // On désélectionne tous les élèments.
        this.list.unselectAllElement();

        // On retire l'activité des observateurs du système de suppression.
        ElementDeleteSystem.deleteObserverStatic(this);
        ElementDeleteSystem.removeActivity();
        // On efface tous les élèments en cours de suppression.
        ElementDeleteSystem.clearDeletedElement();
    }

    @Override
    public void onBackPressed() {
        // Si des élèments sont sélectionné alors on les désélectionne
        if(ElementDeleteSystem.isiSelectMode()) this.list.unselectAllElement();
        // Sinon on ferme l'activité.
        else super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu, menu);
        prepareOptionMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);

        prepareOptionMenu(menu);

        return true;
    }

    /**
     * Méthode permettant de gérer l'afichage des boutons du menu lors de la sélection d"élèments.
     * @param menu Le menu de l'activité.
     */
    private void prepareOptionMenu(Menu menu) {
        menu.findItem(R.id.toolbarlist_rename_button).setVisible(!ElementDeleteSystem.isiSelectMode());
        menu.findItem(R.id.toolbarlist_delete_button).setVisible(!ElementDeleteSystem.isiSelectMode());
        menu.findItem(R.id.toolbarlist_delete_elment_button).setVisible(ElementDeleteSystem.isiSelectMode());
        menu.findItem(R.id.list_voir_tuto).setVisible(!ElementDeleteSystem.isiSelectMode());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbarlist_rename_button:
                // Bouton permettant de renommer une liste.

                // Ouverture d'une dialog qui permet de renommer la liste.
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

                View mv = getLayoutInflater().inflate(R.layout.list_edit, null);

                mBuilder.setView(mv);

                mBuilder.setTitle(R.string.list_edit_modify_title);

                EditText nom = (EditText) mv.findViewById(R.id.nom_list_input);
                nom.setText(this.list.getNom());

                mBuilder.setPositiveButton(R.string.annuler, new DialogAnnulerListener());
                mBuilder.setNegativeButton(R.string.renommer, new DialogListRenommerListener(this, nom, this.list));

                AlertDialog dialog = mBuilder.create();
                dialog.show();
                break;
            case R.id.toolbarlist_delete_button:
                // Bouton permettant de supprimer une liste.

                // Ouverture d'une dialog d econfirmation
                AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(this);


                mBuilder2.setTitle(getResources().getString(R.string.confirmation_liste_title));
                mBuilder2.setMessage(getResources().getString(R.string.confirmation_liste_message));

                mBuilder2.setPositiveButton(R.string.annuler, new DialogAnnulerListener());
                mBuilder2.setNegativeButton(R.string.supprimer, new DialogSupprimerListListener(this, this.list));

                AlertDialog dialog1 = mBuilder2.create();
                dialog1.setCanceledOnTouchOutside(true);
                dialog1.show();
                break;
            case R.id.toolbarlist_delete_elment_button:
                // Bouton permettant de supprimer tous les élèments sélectionnés.

                this.list.removeAllSelectedElement();
                ElementDeleteSystem.deleteAllSelectedElement();
                break;
            case R.id.list_voir_tuto:
                // Bouton permettant d'afficher le tutoriel.
                showTutoriel();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Méthode permettant de lancer l'activité "TutorialActivity"?
     */
    private void showTutoriel() {
        Intent intent = new Intent(ListActivity.this, TutorialActivity.class);
        intent.putExtra("subject", TutorialActivity.TUTORIAL_ELEMENT);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        ActionBar toolbar = getSupportActionBar();
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.floating_button_add_element);
        // Si une selection est en cours ...
        if (ElementDeleteSystem.isiSelectMode()) {
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
            toolbar.setTitle(getResources().getString(R.string.suppression_element));
            button.setEnabled(false);
            button.setClickable(false);
            button.setVisibility(View.GONE);
            button.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }
        else {
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            toolbar.setTitle(list.getNom());
            ((FloatingActionButton) findViewById(R.id.floating_button_add_element)).setEnabled(true);
            button.setEnabled(true);
            button.setClickable(true);
            button.setVisibility(View.VISIBLE);
            button.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
        }
        invalidateOptionsMenu();
    }

    /**
     * Méthode permettant d'afficher la popup permettant d'annuler une suppression.
     */
    public void showConfirmationLayout() {
        this.popUp.showLayout();
    }

    /**
     * Méthode permettant de cacher la popup permettant d'annuler une suppression.
     */
    public void hideConfirmationLayout() {
        this.popUp.hideLayout();
    }
}
