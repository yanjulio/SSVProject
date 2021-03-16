package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Rapport.RapportEtatBesoinResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportDetailEtatBesoin.DetailBesoinRapport;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.RapportEtatDeBesoin.RapportEtatBesoinDataRemote.RapportEtatBesoinViewModel;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

public class EtatBesoinRapport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RapportEtatBesoinViewModel rapportEtatBesoinViewModel;
    ImageView imageView_start;
    TextView textView_start;
    String text_start = null;

    private int mYear, mMoth, mDay_of_Moth;
    private int annee, mois, jour;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    public static String date;

    ImageView imageView_end;
    TextView textView_end;
    String text_end = null;

    int click = 2;

//    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar;
    EtatBesoinRapportAdapter rapportAdapter;
    ListView listView_etatBesoin;
    public static ArrayList<RapportEtatBesoinResponse> list_load_size = new ArrayList<>();

    SharedPreferences prefs;
    String niveauUtilisateur = null;
    String username = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etat_besoin_rapport);
        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");
        username = prefs.getString(Login.USER_NAME, "");
        password = prefs.getString(Login.PASS_WORD, "");
        rapportEtatBesoinViewModel = ViewModelProviders.of(EtatBesoinRapport.this).get(RapportEtatBesoinViewModel.class);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"

        String current_time = df.format(to_number_format(c)); // for the end time
        Log.e("current_time ", current_time);

        calendar = getInstance();

        mYear = calendar.get(YEAR);
        mMoth = calendar.get(MONTH);
        mDay_of_Moth = calendar.get(DAY_OF_MONTH);

        textView_start = findViewById(R.id.text_date1_balance);
        String start_time = minus_30(c, df); // start time, we have change the thirty days to one
        textView_start.setText("" + start_time);
        imageView_start = findViewById(R.id.imageVie_pick_date1_balance);
        imageView_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_image();
                click = 1;
            }
        });

        textView_end = findViewById(R.id.text_date2_balance);
        textView_end.setText("" + current_time);
        imageView_end = findViewById(R.id.imageVie_pick_date2_balance);
        imageView_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_date_image();
                click = 2;
            }
        });


//        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        progressBar = findViewById(R.id.progress_bar_etat_besoin_rapport);
        rapportAdapter = new EtatBesoinRapportAdapter(EtatBesoinRapport.this, textView_start.getText().toString(),
                textView_end.getText().toString());
        listView_etatBesoin = findViewById(R.id.listView_etat_besoin_rapport);

        if (!textView_start.getText().toString().equals(null) && !textView_end.getText().toString().equals(null))
        {
            ListEtatBesoinPeriode(textView_start.getText().toString(), textView_end.getText().toString());
        }

        rapportEtatBesoinViewModel.getIsLoading().observe(EtatBesoinRapport.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public String minus_30(Calendar c,SimpleDateFormat df)
    {
        c.add(Calendar.DATE, -0);
        Date d = null;
        try {
            d = df.parse(df.format(c.getTime()));
        }catch (ParseException ex)
        {
            ex.printStackTrace();
        }
        String start_time = df.format(d);
        return start_time;
    }

    public Date to_number_format(Calendar calendar)
    {
        Date d = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"
        try {
            d = df.parse(d.toString()); // calendar.getTime().toString()
        }catch (ParseException ex)
        {
        }
        return d;
    }

    public void click_date_image()
    {
        datePickerDialog = DatePickerDialog.newInstance(EtatBesoinRapport.this, mYear, mMoth, mDay_of_Moth);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2d612c"));
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    public boolean is_textNotNull(TextView text)
    {
        return text.getText().toString() != null && text.getText().toString().length() >= 1;
    }

    public boolean is_date1(int a)
    {
        return a == 1;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        annee = year;
        mois = monthOfYear + 1;
        jour = dayOfMonth;

        if (jour > 9 && mois > 9)date = annee + "-" + mois + "-" + jour;
        if (jour > 9 && !(mois > 9))date = annee + "-0" + mois + "-" + jour;
        if (!(jour > 9) && mois > 9)date = annee + "-" + mois + "-0" + jour;
        if (!(jour > 9) && !(mois > 9))date = annee + "-0" + mois + "-0" + jour;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (is_date1(click))
        {
            textView_start.setText(""+date);

            if (is_textNotNull(textView_start))
            {
                text_start = textView_start.getText().toString();
                text_end = textView_end.getText().toString();
                if (text_end != null)
                {
                    ListEtatBesoinPeriode(text_start, text_end);
                    if (list_load_size.size() < 0)
                    {
                        Toast.makeText(EtatBesoinRapport.this,
                                "il y a pas des factures pour cette intervalle", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ListEtatBesoinPeriode(text_start, text_end);
                    }
                }
            }
        }
        else
        {
            textView_end.setText(""+date);
            if (is_textNotNull(textView_end))
            {
                text_end = textView_end.getText().toString();
                text_start = textView_start.getText().toString();
                if (text_start != null)
                {
                    ListEtatBesoinPeriode(text_start,text_end);
                    if (list_load_size.size() < 0)
                    {
                        Toast.makeText(EtatBesoinRapport.this,
                                "il y a pas des factures pour cette intervalle", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ListEtatBesoinPeriode(text_start, text_end);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK)
        {
            String date1 = data.getStringExtra(DetailBesoinRapport.DATE_1);
            String date2 = data.getStringExtra(DetailBesoinRapport.DATE_2);

            rapportEtatBesoinViewModel.getIsLoading().observe(EtatBesoinRapport.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            ListEtatBesoinPeriode(date1, date2);
        }
    }

    public void ListEtatBesoinPeriode(String date1, String date2)
    {
        rapportEtatBesoinViewModel.getEtatbesoin(date1, date2).observe(EtatBesoinRapport.this,
                new Observer<List<RapportEtatBesoinResponse>>() {
            @Override
            public void onChanged(List<RapportEtatBesoinResponse> rapportEtatBesoinResponses) {
                rapportAdapter.setEtatBesoin(rapportEtatBesoinResponses);
                listView_etatBesoin.setAdapter(rapportAdapter);
            }
        });
    }
}
