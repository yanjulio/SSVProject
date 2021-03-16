package com.soft.ssvapp.Firebasefiles;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnvoiNotification extends AsyncTask<Void, Void, Void> {
    private String title;
    private String message;
    private String codeProjet;
    private String designationProjet;
    private String kind_list;
    private String nomUtilisateur;
    private String senderToken;
    private String recieverToken;

    public EnvoiNotification(String title, String message, String codeProjet,
                             String designationBesoin, String kind_list, String nomUtilisateur, String senderToken, String recieverToken){
        this.title = title;
        this.message = message;
        this.codeProjet = codeProjet;
        this.designationProjet = designationBesoin;
        this.kind_list = kind_list;
        this.senderToken = senderToken;
        this.recieverToken = recieverToken;
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("title", title);
            jsonObject.put("message", message);
            jsonObject.put("codeProjet", this.codeProjet);
            jsonObject.put("designationProjet", this.designationProjet);
            jsonObject.put("kind_list", kind_list);
            jsonObject.put("USERNAME_RECEIVER", nomUtilisateur);
            jsonObject.put("fcm_token", senderToken);

            JSONObject mainObject = new JSONObject();
            //mainObject.put("to", recieverToken);
            mainObject.put("to", "/topics/"+recieverToken);
            mainObject.put("data", jsonObject);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization",
                    "key=AAAAnBqG1wI:APA91bGs-RdfDNmXT_jJses30ddlztLnDKFfOwA88yqyr6QeahLr9oDFegFkCUbP6QxLLPqlJZ12smXQtimHBGPRHOCaAM8YL35G_b4e0On3fhDM-r0zbRHmTUrGuA0bjCMrkIfW7Cep");
            connection.setDoOutput(true);

            Log.e("sent", mainObject.toString());
            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(mainObject.toString());
            dStream.flush();
            dStream.close();

            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseOutput = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                responseOutput.append(line);
            }
            bufferedReader.close();
            Log.e("output", responseOutput.toString());
        } catch (Exception e) {
            Log.e("output", e.toString());
            e.printStackTrace();
        }

        return null;
    }
}
