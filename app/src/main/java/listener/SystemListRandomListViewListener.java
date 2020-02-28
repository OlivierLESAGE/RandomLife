package listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import fr.lesage.olivier.info503.MainActivity;
import model.ListeElement;

public class SystemListRandomListViewListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private MainActivity activity;

    public SystemListRandomListViewListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListeElement list = (ListeElement) parent.getItemAtPosition(position);
        if (!list.isEmpty())
            if (list.size() >= 2)
                if (list.coefficientDifferentZero())
                    this.activity.showActivityTirage(list.getRandomElement());
                else Toast.makeText(this.activity, "Tous les coefficient sont à 0 !", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this.activity, "Il faut au moins 2 élèment pour faire un tirage.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this.activity, "Cette liste est vide.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        this.activity.showActivityEditList(position);
        return true;
    }
}
