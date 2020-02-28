package model;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fr.lesage.olivier.info503.R;

public class ListElementAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public ListElementAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return SystemListRandom.LIST.length();
    }

    @Override
    public Object getItem(int position) {
        return SystemListRandom.LIST.getList(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = this.inflater.inflate(R.layout.list_element_listed, null);

        ListeElement list = (ListeElement) getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.listTitle);
        TextView summary = (TextView) convertView.findViewById(R.id.listSummary);

        title.setText(list.getNom());
        summary.setText(list.getSummary());
        return convertView;
    }
}
