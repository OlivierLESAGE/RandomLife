package listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import fr.lesage.olivier.info503.R;
import model.ListeElement;

public class AddElementButtonListener implements View.OnClickListener {
    private Activity app;
    private ListeElement list;

    public AddElementButtonListener(Activity app, ListeElement list) {
        this.app = app;
        this.list = list;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.app);

        View mv = this.app.getLayoutInflater().inflate(R.layout.element_edit, null);

        mBuilder.setView(mv);

        mBuilder.setTitle(R.string.element_edit_add_title);

        EditText nom = (EditText) mv.findViewById(R.id.element_edit_name_input);
        EditText coeff = (EditText) mv.findViewById(R.id.element_edit_coefficient_input);

        mBuilder.setPositiveButton(R.string.annuler, new DialogAnnulerListener());
        mBuilder.setNegativeButton(R.string.ajouter, new DialogAjouterElementListener(this.list, this.app, nom, coeff));

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
