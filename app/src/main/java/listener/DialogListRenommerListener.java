package listener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import fr.lesage.olivier.info503.ListActivity;
import model.ListeElement;

public class DialogListRenommerListener implements DialogInterface.OnClickListener {
    private ListActivity app;
    private EditText nom;
    private ListeElement list;

    public DialogListRenommerListener(ListActivity app, EditText nom, ListeElement list) {
        this.app = app;
        this.nom = nom;
        this.list = list;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(!this.nom.getText().toString().isEmpty()) {
            this.list.setNom(this.nom.getText().toString());
            this.app.getSupportActionBar().setTitle(this.list.getNom());
        } else {
            Toast.makeText(this.app.getBaseContext(), "Veuillez entrer un nom", Toast.LENGTH_LONG).show();
        }
    }
}
