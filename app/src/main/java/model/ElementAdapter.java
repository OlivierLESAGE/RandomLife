package model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fr.lesage.olivier.info503.MainActivity;
import fr.lesage.olivier.info503.R;

public class ElementAdapter extends BaseAdapter {
    private ListeElement list;
    private Activity app;

    public ElementAdapter(ListeElement list, Activity app) {
        this.list = list;
        this.app = app;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.getElement(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = this.app.getLayoutInflater().inflate(R.layout.element_listed, null);

        Element ele = (Element) getItem(position);

        if(ele.isSelected()) convertView.setBackgroundColor(this.app.getResources().getColor(R.color.selected_item));

        TextView title = (TextView) convertView.findViewById(R.id.ElementName);
        TextView coeff = (TextView) convertView.findViewById(R.id.ElementCoeff);

        title.setText(ele.getNom());
        coeff.setText(this.app.getResources().getString(R.string.coefficient_list_view, ele.getCoeff()));
        return convertView;
    }
}
