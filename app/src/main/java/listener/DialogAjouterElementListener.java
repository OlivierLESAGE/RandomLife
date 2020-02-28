package listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.Element;
import model.ListeElement;

public class DialogAjouterElementListener implements DialogInterface.OnClickListener {
    private ListeElement list;
    private Activity app;
    private EditText text;
    private EditText number;

    public DialogAjouterElementListener(ListeElement list, Activity app, EditText text, EditText number) {
        this.list = list;
        this.app = app;
        this.text = text;
        this.number = number;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(this.text.getText().toString().isEmpty() || this.number.getText().toString().isEmpty()) {
            Toast.makeText(this.app.getApplicationContext(), "Veuillez entrer un nom et un coefficient.", Toast.LENGTH_SHORT).show();
        } else {
            this.list.addElement(new Element(this.text.getText().toString(), Integer.parseInt(this.number.getText().toString())));
        }
    }
}
