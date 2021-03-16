package com.soft.ssvapp.Fragment_Menu.Fill_Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soft.ssvapp.Fragment_Menu.Navigation.DashBoard;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class Dashboard_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Dashboard_objet> arrayList_adapter;
    private int size;

    public Dashboard_Adapter(Context context, List<Dashboard_objet> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.arrayList_adapter = new ArrayList<Dashboard_objet>();
        this.arrayList_adapter.addAll(list);
    }

    class Hold
    {
        TextView title_hold, details_hold, pourcentage_hold;
    }

    @Override
    public int getCount() {
        return DashBoard.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return DashBoard.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Hold hold;

        if (convertView == null)
        {
            hold = new Hold();
            convertView = inflater.inflate(R.layout.model_dashboard, null);
            hold.title_hold = convertView.findViewById(R.id.text_title_dashboard);
            hold.details_hold = convertView.findViewById(R.id.text_details_dashboard);
            hold.pourcentage_hold = convertView.findViewById(R.id.text_pourcentage_dashboard);
            convertView.setTag(hold);
        }
        else
        {
            hold = (Hold)convertView.getTag();
        }

        hold.title_hold.setText(""+DashBoard.arrayList.get(position).getTitle());
        hold.details_hold.setText(""+DashBoard.arrayList.get(position).getDetails());
        hold.pourcentage_hold.setText(""+DashBoard.arrayList.get(position).getPourcentage());

        size = convertView.getScrollBarSize();

        return convertView;
    }
}
