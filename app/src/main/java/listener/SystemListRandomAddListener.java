package listener;

import android.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import fr.lesage.olivier.info503.MainActivity;
import fr.lesage.olivier.info503.R;

public class SystemListRandomAddListener implements View.OnClickListener {
    private MainActivity app;

    public SystemListRandomAddListener(MainActivity app) {
        this.app = app;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.app);

        View mv = this.app.getLayoutInflater().inflate(R.layout.list_edit, null);

        mBuilder.setView(mv);

        mBuilder.setTitle(R.string.list_edit_add_title);

        EditText nom = (EditText) mv.findViewById(R.id.nom_list_input);

        mBuilder.setPositiveButton(R.string.annuler, new DialogAnnulerListener());
        mBuilder.setNegativeButton(R.string.ajouter, new DialogAjouterListListener(this.app, nom));

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
