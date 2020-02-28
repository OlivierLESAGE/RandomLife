package listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import model.Element;
import model.ListeElement;

public class DialogElementModifierListener implements DialogInterface.OnClickListener {
    private Element ele;
    private Activity app;
    private EditText text;
    private EditText number;

    public DialogElementModifierListener(Element ele, Activity app, EditText text, EditText number) {
        this.ele = ele;
        this.app = app;
        this.text = text;
        this.number = number;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(this.text.getText().toString().isEmpty() || this.number.getText().toString().isEmpty()) {
            Toast.makeText(this.app.getApplicationContext(), "Veuillez entrer un nom et un coefficient.", Toast.LENGTH_SHORT).show();
        } else {
            this.ele.setNom(this.text.getText().toString());
            this.ele.setCoeff(Integer.parseInt(this.number.getText().toString()));
        }
    }
}
