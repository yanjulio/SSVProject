package com.soft.ssvapp.Fragment_Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.soft.ssvapp.Fragment_Menu.Navigation.Navigation_Host;
import com.soft.ssvapp.R;

public class Menu_Principale extends AppCompatActivity implements Navigation_Host {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principale);
//        getSupportActionBar().hide();

//        if (savedInstanceState == null)
//        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.container, new DashBoard())
//                    .commit();
//        }
    }


    /*
    Allow the navigation throught fragments projects, caisse, fournisseur and client
     */

    @Override
    public void navigate_To(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment);
        if (addToBackStack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
