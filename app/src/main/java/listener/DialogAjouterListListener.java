package listener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.lesage.olivier.info503.MainActivity;
import model.ListeElement;
import model.SystemListRandom;

public class DialogAjouterListListener implements DialogInterface.OnClickListener {
    private MainActivity app;
    private EditText text;

    public DialogAjouterListListener(MainActivity app, EditText text) {
        this.app = app;
        this.text = text;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(this.text.getText().toString().isEmpty()) {
            Toast.makeText(this.app.getApplicationContext(), "Veuillez entrer un nom.", Toast.LENGTH_SHORT).show();
        } else {
            int position = SystemListRandom.LIST.addList(new ListeElement(this.text.getText().toString(), this.app));
            this.app.showActivityEditList(position);
        }
    }
}
