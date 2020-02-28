package listener;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toolbar;

import fr.lesage.olivier.info503.ListActivity;
import fr.lesage.olivier.info503.R;
import model.Element;
import model.ElementDeleteSystem;
import model.ListeElement;

public class ListListViewListener implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListeElement list;
    private ListActivity app;

    public ListListViewListener(ListeElement list, ListActivity app) {
        this.list = list;
        this.app = app;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Element ele = (Element) parent.getItemAtPosition(position);
        if(!ElementDeleteSystem.isiSelectMode()) {
            createDialog(ele);
        } else {
            selectElement(ele);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Element ele = (Element) parent.getItemAtPosition(position);
        selectElement(ele);
        //DeleteSystem.poubelle.addElement(this.list.getElement(position), position, this.list);
        //this.list.deleteElement(position);

        return true;
    }

    private void selectElement(Element ele) {
        if(!ele.isSelected()) {
            ele.select();
        }
        else {
            ele.unselect();
        }
    }

    private void createDialog(Element ele) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this.app);

        View mv = this.app.getLayoutInflater().inflate(R.layout.element_edit, null);

        mBuilder.setView(mv);

        mBuilder.setTitle(R.string.element_edit_modify_title);

        EditText nom = (EditText) mv.findViewById(R.id.element_edit_name_input);
        nom.setText(ele.getNom());
        EditText coeff = (EditText) mv.findViewById(R.id.element_edit_coefficient_input);
        coeff.setText(Integer.toString(ele.getCoeff()));

        mBuilder.setPositiveButton(R.string.annuler, new DialogAnnulerListener());
        mBuilder.setNegativeButton(R.string.modifier, new DialogElementModifierListener(ele, this.app, nom, coeff));

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}
