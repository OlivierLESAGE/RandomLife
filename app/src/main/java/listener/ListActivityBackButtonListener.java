package listener;

import android.app.Activity;
import android.view.View;

import model.ElementDeleteSystem;
import model.ListeElement;

public class ListActivityBackButtonListener implements View.OnClickListener {
    private ListeElement list;
    private Activity app;

    public ListActivityBackButtonListener(ListeElement list, Activity app) {
        this.list = list;
        this.app = app;
    }

    @Override
    public void onClick(View v) {
        this.app.onBackPressed();
    }
}
