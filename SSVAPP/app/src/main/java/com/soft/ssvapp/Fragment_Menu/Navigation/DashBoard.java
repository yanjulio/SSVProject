package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.anychart.AnyChartView;
import com.google.android.material.button.MaterialButton;
import com.soft.ssvapp.Fragment_Menu.Fill_Dashboard.Dashboard_Adapter;
import com.soft.ssvapp.Fragment_Menu.Fill_Dashboard.Dashboard_objet;
import com.soft.ssvapp.Fragment_Menu.Fill_Dashboard.Helper;
import com.soft.ssvapp.Fragment_Menu.chart;
import com.soft.ssvapp.R;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoard extends AppCompatActivity {

    public static ArrayList<Dashboard_objet> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fragment_dash_board);
//        getSupportActionBar().hide();
        setUpToolbar();
        AnyChartView anyChartView = findViewById(R.id.any_chart);

        // fill with false values
//        fill_list();
        new async_data(anyChartView).execute();

    }

    void fill_list()
    {
        arrayList.clear();
        for (int i=0; i < 5; i++)
        {
            arrayList.add(new Dashboard_objet("Titre", "Details", "12%"));
        }
    }

    private void setUpToolbar()
    {
        Toolbar toolbar = findViewById(R.id.app_bar);
        if (DashBoard.this != null) {
            DashBoard.this.setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DashBoard.this.finish();
                }
            });
        }
    }

    private class async_data extends AsyncTask<String, Void, String>
    {
        private AnyChartView anyChartView;
        private ProgressBar progressBar = findViewById(R.id.progress_bar_chart);

        async_data(AnyChartView anyChartView)
        {
            this.anyChartView = anyChartView;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ListView listView_dashboard = findViewById(R.id.list_dashboard);
            Dashboard_Adapter adapter = new Dashboard_Adapter(DashBoard.this, arrayList);
            listView_dashboard.setAdapter(adapter);
            Helper.getListViewSize(listView_dashboard, DashBoard.this, arrayList);

            new chart(DashBoard.this, this.anyChartView, this.progressBar);
        }

        @Override
        protected String doInBackground(String... strings) {
            fill_list();
            return null;
        }
    }
}
