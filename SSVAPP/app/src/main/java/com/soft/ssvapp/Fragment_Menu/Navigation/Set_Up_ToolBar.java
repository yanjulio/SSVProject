package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.soft.ssvapp.R;

public class Set_Up_ToolBar {

    private View view;
    private Context context;
    private View id_view;
    private AppCompatActivity activity;

    public Set_Up_ToolBar(View view, AppCompatActivity activity, Context context, View id_view)
    {
        this.view = view;
        this.context = context;
        this.id_view = id_view;
        this.activity = activity;
        setUpToolbar(this.view, this.activity);
    }

    private void setUpToolbar(final View view, final AppCompatActivity activity)
    {
        Toolbar toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activ = (AppCompatActivity)activity;
        if (activ != null)
        {
            activ.setSupportActionBar(toolbar);
        }

        toolbar.setNavigationOnClickListener(new NavigationIconclickListener(
                this.context,
                this.id_view,
                new AccelerateDecelerateInterpolator(),
                this.context.getResources().getDrawable(R.drawable.ic_baseline_menu_24),
                this.context.getResources().getDrawable(R.drawable.ic_baseline_clear_24)));
    }
}
