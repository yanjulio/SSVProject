package com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.ssvapp.DataRetrofit.Rapport.DetailRapportResponse;
import com.soft.ssvapp.DataRetrofit.Rapport.RapportRetrofitRepository;
import com.soft.ssvapp.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
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

public class DetailBalance extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView textView_titre_details_balance;
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

    RapportRetrofitRepository rapportRetrofitRepository;
    ProgressBar progressBar;
    public static ArrayList<Details_Balance_objet> arrayList_loadDetail = new ArrayList<>();
    int click = 2;
    public static String NUM_COMPTE = "num_compte";
    public static String DESIGANTION = "designation";
    String desingation;
    int num_compte;

    // linear details pour afficher la liste
    ScrollView scrollView_balance_list;
    ListView listView_balance_details;
    TextView text_credit_totale_balance;

    // textView pour l'initiale
    TextView textView_debit_initiale;
    TextView textView_credit_initiale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_balance);
        rapportRetrofitRepository = RapportRetrofitRepository.getInstance();
        progressBar = findViewById(R.id.progress_bar_detail_balance);

        scrollView_balance_list = findViewById(R.id.scrollView_down_balance);
        scrollView_balance_list.setVisibility(View.GONE);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd "); // 'T' HH:mm:ss z"

        String current_time = df.format(to_number_format(c)); // for the end time
        Log.e("current_time ", current_time);

        desingation = getIntent().getStringExtra(DESIGANTION);
        textView_titre_details_balance = findViewById(R.id.textView_titre_details_balance);
        textView_titre_details_balance.setText("Relevé de "+ desingation);
        num_compte = getIntent().getIntExtra(NUM_COMPTE, 0);

//        calendar = getInstance();
//
//        mYear = calendar.get(YEAR);
//        mMoth = calendar.get(MONTH);
//        mDay_of_Moth = calendar.get(DAY_OF_MONTH);

        textView_start = findViewById(R.id.text_date1_balance);
        String start_time = minus_30(c, df); // start time
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

        if (!textView_start.getText().toString().equals(null) && !textView_end.getText().toString().equals(null))
        {
            scrollView_balance_list.setVisibility(View.VISIBLE);
            loadData(num_compte, textView_start.getText().toString(), textView_end.getText().toString());
        }

        final ImageView imageView_exit = findViewById(R.id.imageview_exit_detail_balance);
        imageView_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public String minus_30(Calendar c,SimpleDateFormat df)
    {
        c.add(Calendar.DATE, -7);
        Date d = null;
        try {
            d = df.parse(df.format(c.getTime()));
            Log.e("first time default ", d.toString());
        }catch (ParseException ex)
        {
            ex.printStackTrace();
            Log.v("Exception", ex.getLocalizedMessage());
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
            Log.v("Exception", ex.getLocalizedMessage());
        }
        return d;
    }

    public boolean is_textNotNull(TextView text)
    {
        return text.getText().toString() != null && text.getText().toString().length() >= 1;
    }

    public void click_date_image()
    {
        datePickerDialog = DatePickerDialog.newInstance(DetailBalance.this, mYear, mMoth, mDay_of_Moth);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.showYearPickerFirst(false);
        datePickerDialog.setAccentColor(Color.parseColor("#2d612c"));
        datePickerDialog.setTitle("Selectionner la date");
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
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
                    loadData(num_compte, text_start, text_end);
                    if (arrayList_loadDetail.size() < 0)
                    {
                        scrollView_balance_list.setVisibility(View.GONE);
                        Toast.makeText(DetailBalance.this,
                                "il y a pas des factures pour cette intervalle", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        scrollView_balance_list.setVisibility(View.VISIBLE);
                        loadData(num_compte, text_start, text_end);
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
                    // todo : call the list load
                    loadData(num_compte, text_start, text_end);
                    if (arrayList_loadDetail.size() < 0)
                    {
                        scrollView_balance_list.setVisibility(View.GONE);
                        Toast.makeText(DetailBalance.this,
                                "il y a pas des factures pour cette intervalle", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        scrollView_balance_list.setVisibility(View.VISIBLE);
                        loadData(num_compte, text_start, text_end);
                    }
                }
            }
        }
    }

    public double setDebit_totale(ArrayList<Details_Balance_objet> list)
    {
        double debit = 0;
        for (int i = 0; i < list.size(); i++)
        {
            if (!list.get(i).getDebit().equals(""))
            {
                debit += Double.valueOf(list.get(i).getDebit());
            }
        }
        return debit;
    }

    public double setCredit(ArrayList<Details_Balance_objet> list)
    {
        double credit = 0;
        for (int position = 0 ; position < list.size(); position++)
        {
            if (!list.get(position).getCredit().equals(""))
            {
                credit += Double.valueOf(list.get(position).getCredit());
            }
        }
        return credit;
    }

    public void loadData(int num_compte, String date1, String date2)
    {
        Call<List<DetailRapportResponse>> call_detail =
                rapportRetrofitRepository.rapportConnexion().balanceDetail(num_compte, date1, date2);
        progressBar.setVisibility(View.VISIBLE);
        call_detail.enqueue(new Callback<List<DetailRapportResponse>>() {
            @Override
            public void onResponse(Call<List<DetailRapportResponse>> call, Response<List<DetailRapportResponse>> response) {
                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    arrayList_loadDetail.clear();
                    for (int a = 0; a < response.body().size(); a++)
                    {
                        Details_Balance_objet balance_objet = new Details_Balance_objet(
                                response.body().get(a).getDateOperation(),
                                response.body().get(a).getDesignationCompte(),
                                response.body().get(a).getLibelle() + "/" + response.body().get(a).getNumOperation(),
                                String.valueOf(response.body().get(a).getDebit()),
                                String.valueOf(response.body().get(a).getCredit()),
                                String.valueOf(response.body().get(a).getSolde()),
                                response.body().get(a).getNumOperation(),
                                Integer.valueOf(response.body().get(a).getNumCompte()));
                        arrayList_loadDetail.add(balance_objet);
                    }
                    TextView textView_credit_solde_balance = findViewById(R.id.text_credit_solde_balance);
                    TextView textView_debit_totale_balance = findViewById(R.id.text_debit_totale_balance);
                    text_credit_totale_balance = findViewById(R.id.text_credit_totale_balance);

                    textView_credit_initiale = findViewById(R.id.txt_totale_credit_initale);
                    textView_debit_initiale = findViewById(R.id.txt_totale_debit_initale);

                    listView_balance_details = findViewById(R.id.list_details_balance);
                    Details_Balance_Adapter adapter = new Details_Balance_Adapter(DetailBalance.this, date1, date2);
                    adapter.setDetailBalance(arrayList_loadDetail);
                    listView_balance_details.setAdapter(adapter);

                    if (arrayList_loadDetail.size() != 0)
                    {
                        textView_credit_solde_balance.setText(""+
                                new DecimalFormat("##.##").format(Double.valueOf(arrayList_loadDetail.get(0).getSolde())));
                        textView_debit_totale_balance.setText(""+
                                new DecimalFormat("##.##").format(setDebit_totale(arrayList_loadDetail)));
                        text_credit_totale_balance.setText("" +
                                new DecimalFormat("##.##").format(setCredit(arrayList_loadDetail)));

                        double last_solde = Double.valueOf(arrayList_loadDetail.get(0).getSolde());
                        double credit = setCredit(arrayList_loadDetail);
                        double debit = setDebit_totale(arrayList_loadDetail);

                        double initiale = last_solde + (credit - debit);

                        if (initiale > 0)
                        {
                            textView_debit_initiale.setText(""+ new DecimalFormat("##.##").format(initiale));
                        }
                        else
                        {
                            textView_credit_initiale.setText("" + new DecimalFormat("##.##").format(initiale));
                        }

                    }
                    else
                    {
                        textView_credit_solde_balance.setText("0");
                        text_credit_totale_balance.setText("0");
                        textView_debit_totale_balance.setText("0");
                        textView_debit_initiale.setText("0");
                        textView_credit_initiale.setText("0");
                    }
                }
                else
                {
                    switch (response.code())
                    {
                        case 404:
                            Toast.makeText(DetailBalance.this, "server is not found.", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(DetailBalance.this, "server broken.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(DetailBalance.this, "Unknown problem.", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DetailRapportResponse>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetailBalance.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
