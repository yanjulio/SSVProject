package com.soft.ssvapp.Firebasefiles;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.soft.ssvapp.Fragment_Menu.Navigation.Etat_Besoin;
import com.soft.ssvapp.Fragment_Menu.Navigation.Operation_Cpte;
import com.soft.ssvapp.Login;
import com.soft.ssvapp.R;

import java.util.Random;

public class MyMessagingService extends FirebaseMessagingService {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String niveauUtilisateur ;
    String receiverUtilisateur;

    private static final String NOTIFICATION_CHANNEL_ID = "channel_os";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        AfficherNotification(getApplicationContext(),remoteMessage.getData()
                .get("message"),
//                remoteMessage.getData().get("title"),
                remoteMessage.getData().get("codeProjet"),
                remoteMessage.getData().get("designationProjet"),
                remoteMessage.getData().get("kind_list"), remoteMessage.getData().get("USERNAME_RECEIVER"),
                Integer.parseInt(getRandomNumberString()));

        Log.e("message", remoteMessage.getData().get("message"));
    }

    public String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void AfficherNotification(Context context, String messageBody,
//                                      String titre,
                                      String codeProjet, String designationProjet, String kind_list, String nomUtilisateur,int id_notifif){

        //CREATION DES NOTIFICATION
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Uri soundUri =
//                Uri.parse(
//                        ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+
//                                getApplicationContext().getPackageName() + "/" + R.raw.consequence); // todo: this is to make the sound you want
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
//            notificationChannel.setSound(soundUri, audioAttributes); // todo : this is to set the sound you want
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.icone_64)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icone_64))
                .setVibrate(new long[]{0, 1000}) // {0, 100, 100, 100, 100, 100}, this is what were there at the place of 1000
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.RED)
//                .setContentTitle(titre)
                .setAutoCancel(true)
                .setContentText(messageBody);

        prefs = getSharedPreferences("lOGIN_USER", MODE_PRIVATE);
        niveauUtilisateur = prefs.getString(Login.NIVEAUUTILISATEUR, "");

        // do this for the receiver notification from the admin
        editor = prefs.edit();
        editor.putString("USERNAME_RECEIVER", nomUtilisateur );
        editor.commit();

//        Intent appActivityIntent = null;

        if (niveauUtilisateur.equals("ADMIN"))
        {
            Log.e("Je suis dans ", "Utilisateur" + niveauUtilisateur + " et kindlist :" + kind_list );
            Intent appActivityIntent = new Intent(this, Operation_Cpte.class)
                    .putExtra(Operation_Cpte.CODE_PROJECT, codeProjet)
                    .putExtra(Operation_Cpte.DESIGNATIONPROJET, designationProjet)
                    .putExtra(Operation_Cpte.KIND_OPERATION, kind_list);
            PendingIntent contentAppActivityIntent =
                    PendingIntent.getActivity(
                            this,  // calling from Activity
                            0,
                            appActivityIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentAppActivityIntent);
        }
        else if (!niveauUtilisateur.equals("ADMIN"))
        {
            String username_receiver = prefs.getString("USERNAME_RECEIVER", "");
            Log.e("Je suis dans ", "Utilisateur" + username_receiver + " et kindlist :" + kind_list);
            Intent appActivityIntent = new Intent(this, Etat_Besoin.class)
                    .putExtra(Etat_Besoin.CODE_PROJECT, codeProjet)
                    .putExtra(Etat_Besoin.DESIGANTION_PROJET, designationProjet)
                    .putExtra(Etat_Besoin.BESOIN_KIND, kind_list);
            PendingIntent contentAppActivityIntent =
                    PendingIntent.getActivity(
                            this,  // calling from Activity
                            0,
                            appActivityIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(contentAppActivityIntent);
        }

        notificationManager.notify(Integer.parseInt(getRandomNumberString()), builder.build());
    }
}
