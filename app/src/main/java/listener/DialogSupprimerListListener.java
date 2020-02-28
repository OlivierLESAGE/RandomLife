package listener;

import android.app.Activity;
import android.content.DialogInterface;

import model.ListeElement;
import model.SystemListRandom;

public class DialogSupprimerListListener implements DialogInterface.OnClickListener{
    private Activity app;
    private ListeElement list;

    public DialogSupprimerListListener(Activity app, ListeElement list) {
        this.app = app;
        this.list = list;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SystemListRandom.LIST.removeList(this.list);
        this.app.finish();
    }
}
