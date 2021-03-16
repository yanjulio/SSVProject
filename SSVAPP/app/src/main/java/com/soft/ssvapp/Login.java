package com.soft.ssvapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.soft.ssvapp.DataRetrofit.DonneesFromApi;
import com.soft.ssvapp.DataRetrofit.UtilisateurRemote;
import com.soft.ssvapp.DataRetrofit.UtilisateurResponse;
import com.soft.ssvapp.Fragment_Menu.Navigation.Menus_All;
import com.soft.ssvapp.Utilisateurs.CreerUtilisateurAtivity;
import com.soft.ssvapp.Utilisateurs.GestionUtilisateur;
import com.soft.ssvapp.Utilisateurs.ModifierUtilisateur;
import com.soft.ssvapp.Utilisateurs.UtilisateurViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    UtilisateurViewModel viewModel;
    DonneesFromApi donneesFromApi;

    TextInputEditText edit_username;
    TextInputEditText edit_password;

    // SharePreference reading data from login
    SharedPreferences prefs;
    // sharePreference for writing data to login interface
    SharedPreferences.Editor editor;
    public static String USER_NAME = "USER_NAME";
    public static String PASS_WORD = "PASS_WORD";
    public static String IDUTILISATEUR = "IDUTILISATEUR";
    public static String NIVEAUUTILISATEUR = "NIVEAUUTILISATEUR";
    public static final String SERVICE_AFFECTER = "SERVICE_AFFECTER";
    public static final String COMPTE = "COMPTE";
    public static final String ID_UTILISATEUR = "ID_UILISATEUR";
    public static final String COMPTE_DECLARATION="COMPTE_DECLARATION";
    public static final String DESIGNATION_UTILISATEUR = "DESIGNATION_UT";
    public static final String FONCTION_UTILISATEUR = "FONCTION_UT";
    public static final String ACTIF = "ACTIF";

    // variables for the logins
    String username_prefs;
    String password_prefs;
    String niveauUtilisateur="";
    String service_affecter_prefs = "";
    int compte_prefs;
    int actif=0;
    int idUtilisateur =0;
    int compte_declration_prefs;
    String designationUt = "";
    String fonctionUt = "";

    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT > 0) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications",
                    "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

//        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                String msg = "Succesful";
//                if (!task.isSuccessful())
//                {
//                    msg = "Failed";
//                }
////                Toast.makeText(Login.this, msg, Toast.LENGTH_LONG).show();
//            }
//        });


        this.setTitle("Login");
        donneesFromApi = new DonneesFromApi(this);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);

        username_prefs = prefs.getString(USER_NAME, "");
        password_prefs = prefs.getString(PASS_WORD, "");
        niveauUtilisateur = prefs.getString(NIVEAUUTILISATEUR, "");
        service_affecter_prefs = prefs.getString(SERVICE_AFFECTER, "");
        idUtilisateur = prefs.getInt(ID_UTILISATEUR, 0);
        actif = prefs.getInt(ACTIF, 0);
        compte_prefs = prefs.getInt(COMPTE, 0);
        compte_declration_prefs = prefs.getInt(COMPTE_DECLARATION, 0);
        designationUt = prefs.getString(DESIGNATION_UTILISATEUR, "");
        fonctionUt = prefs.getString(FONCTION_UTILISATEUR, "");

        if (!niveauUtilisateur.equals(""))
        {
//            Toast.makeText(Login.this, "la preference est null see :" + niveauUtilisateur, Toast.LENGTH_LONG).show();
            FirebaseMessaging.getInstance().unsubscribeFromTopic(niveauUtilisateur).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "Succesful";
                    if (!task.isSuccessful())
                    {
                        msg = "Failed";
                    }
//                    Toast.makeText(Login.this, "Unsucribe message is " + msg, Toast.LENGTH_LONG).show();
                }
            });
        }

        progressBar = findViewById(R.id.progress_bar_login);
        final TextInputLayout inputLayout_password = findViewById(R.id.password_text_input);
        edit_username = findViewById(R.id.edit_username);
        edit_username.setText(username_prefs);

        edit_password = findViewById(R.id.edit_password);
        edit_password.setText(password_prefs);

        viewModel = ViewModelProviders.of(Login.this).get(UtilisateurViewModel.class);
//        viewModel.init(Login.this);

        ProgressBar progressBar = findViewById(R.id.progress_bar_login);

        MaterialButton btn_connect = findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edit_username.getText().toString();
                String password = edit_password.getText().toString();
                if (!isPass_word_valid(edit_password.getText()))
                {
                    inputLayout_password.setError(""+getString(R.string.kp_error_password));
                    Log.e("Message error", "je ne suis pas d'accord");
                    Toast.makeText(Login.this, "Veillez Entrez vos identiants, en premier" , Toast.LENGTH_LONG).show();
                }
                else
                {
                    inputLayout_password.setError(null);
                    if (edit_username.getText().toString().equals(username_prefs)
                            && edit_password.getText().toString().equals(password_prefs)
                            && actif == 1)
                    {
                        finish();
                        startActivity(new Intent(Login.this, Menus_All.class));
                    }
                    else
                    {
                        UtilisateurRemote utilisateurRepository = UtilisateurRemote.getInstance();
                        Call<UtilisateurResponse> call_utilisateur =
                                utilisateurRepository.utilisateurConnexion()
                                        .Login(edit_username.getText().toString(), edit_password.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
//                        Toast.makeText(Login.this, "Nouveau utilisateur ", Toast.LENGTH_LONG);
                        call_utilisateur.enqueue(new Callback<UtilisateurResponse>() {
                            @Override
                            public void onResponse(Call<UtilisateurResponse> call, Response<UtilisateurResponse> response) {
                                if(response.isSuccessful())
                                {
                                    if (response.body().getNomUtilisateur()!=null
                                            && response.body().getMotDePassUtilisateur()!=null
                                            && response.body().getActif() == 0)
                                    {
                                        editor = prefs.edit();
                                        editor.putString(USER_NAME, edit_username.getText().toString());
                                        editor.putString(PASS_WORD, edit_password.getText().toString());
                                        editor.putInt(IDUTILISATEUR, response.body().getIdUtilisateur());
                                        editor.putString(NIVEAUUTILISATEUR, response.body().getNiveauUtilisateur());
                                        editor.putString(SERVICE_AFFECTER, response.body().getServiceAffe());
                                        editor.putInt(COMPTE, response.body().getCompte());
                                        editor.putInt(ID_UTILISATEUR, response.body().getIdUtilisateur());
                                        editor.putInt(COMPTE_DECLARATION, response.body().getCompteDeclaration());
                                        editor.putString(DESIGNATION_UTILISATEUR, response.body().getDesignationUt());
                                        editor.putString(FONCTION_UTILISATEUR, response.body().getFonctionUt());
                                        editor.putInt(ACTIF, response.body().getActif());
                                        editor.commit();
                                        finish();
                                        startActivity(new Intent(Login.this, Menus_All.class));
                                    }
                                }
                                else
                                {
                                    switch (response.code())
                                    {
                                        case 404:
                                            Toast.makeText(Login.this, "server not found. Usher", Toast.LENGTH_LONG).show();
                                            break;
                                        case 500:
                                            Toast.makeText(Login.this, "server broken. Usher",Toast.LENGTH_LONG).show();
                                            break;
                                        default:
                                            Toast.makeText(Login.this, "Unknown problem. Usher", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<UtilisateurResponse> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Connexion Problem.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        // clear the error once more than 3 characters are typed.
        edit_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPass_word_valid(edit_password.getText()))
                {
                    inputLayout_password.setError(null); // clear the error
                }
                return false;
            }
        });

        ImageView imageView_utilisateur = findViewById(R.id.image_gestion_compte_utilisateur);
        imageView_utilisateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Login.this, v);
                popupMenu.setOnMenuItemClickListener(Login.this);
                popupMenu.inflate(R.menu.menu_gestion_utilisateur);
                popupMenu.show();

                MenuItem menuItem_creer = popupMenu.getMenu().findItem(R.id.item_creer_utilisateur);
                MenuItem menuItem_modifier = popupMenu.getMenu().findItem(R.id.item_modifier_utilisateur);
                MenuItem menuItem_gestion_utilisateur = popupMenu.getMenu().findItem(R.id.item_gerer_utilisateur);
                if (service_affecter_prefs.equals("ADMIN"))
                {
//                    Toast.makeText(Login.this, "service_affecter : "
//                            + service_affecter_prefs, Toast.LENGTH_LONG).show();
                    menuItem_creer.setVisible(false);
                    menuItem_modifier.setVisible(true);
                    menuItem_gestion_utilisateur.setVisible(true);
                }
                else if (service_affecter_prefs.equals("")
                        && (username_prefs.equals("") || password_prefs.equals("")))
                {
                    menuItem_creer.setVisible(true);
                    menuItem_modifier.setVisible(false);
                }
                else
                {
                    menuItem_creer.setVisible(false);
                }
            }
        });

    }

    private boolean isPass_word_valid(Editable editable)
    {
        return editable != null && editable.length() >= 3;
    }

    public void setIdentifiant(UtilisateurResponse utilisateurResponse)
    {
        edit_username.setText("" + utilisateurResponse.getNomUtilisateur());
        edit_password.setText(""+ utilisateurResponse.getMotDePassUtilisateur());
    }

    public void ModifierDialog(Boolean loading)
    {
        if (loading)
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
        }
    }

//    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings_serveur, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.serveur_settings:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_creer_utilisateur:
                startActivityForResult(new Intent(Login.this, CreerUtilisateurAtivity.class), 14);
                break;
            case R.id.item_modifier_utilisateur:
                UtilisateurResponse utilisateurResponse =
                        new UtilisateurResponse(idUtilisateur, username_prefs, password_prefs, designationUt,
                                fonctionUt, service_affecter_prefs, compte_prefs, actif);
                ModifierUtilisateur modifier = new ModifierUtilisateur(utilisateurResponse);
                modifier.show(getSupportFragmentManager(), "modifier");
                break;
            case R.id.item_gerer_utilisateur:
                startActivity(new Intent(Login.this, GestionUtilisateur.class));
                break;
        }

        return false;
    }
}
