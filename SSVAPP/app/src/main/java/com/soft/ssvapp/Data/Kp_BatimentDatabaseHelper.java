package com.soft.ssvapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Kp_BatimentDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "kp_batiment";
    private static final int DB_VERSION = 1;

    private String tProject = "CREATE TABLE `tProject` (\n" +
            "\t`IdProject`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`CodeProject`\tTEXT ,\n" +
            "\t`DesignationProject`\tTEXT ,\n" +
            "\t`DateDebut`\tTEXT ,\n" +
            "\t`DateFin`\tTEXT ,\n" +
            "\t`Lieu`\tTEXT ,\n" +
            "\t`ChefDuProjet`\tTEXT ,\n" +
            "\t`etat`\tINTEGER\n" +
            ");";

    private String tLigne = "CREATE TABLE `tLigne` (\n" +
            "\t`IdLigne`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`CodeLigne`\tTEXT,\n" +
            "\t`CodeProject`\tTEXT,\n" +
            "\t`DesignationLIgne`\tTEXT\n" +
            ");";

    private String tEtatDeBesoin = "CREATE TABLE `tEtatDeBesoin` (\n" +
            "\t`IdEtatDuBesoin`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`CodeEtatdeBesoin`\tTEXT,\n" +
            "\t`DesignationEtatDeBesion`\tTEXT,\n" +
            "\t`CodeProject`\tTEXT,\n" +
            "\t`Etat`\tINTEGER,\n" +
            "\t`Demandeur`\tTEXT,\n" +
            "\t`DateEmision`\tTEXT,\n" +
            "\t`DateRequise`\tTEXT,\n" +
            "\t`DateValidation`\tTEXT,\n" +
            "\t`ValiderPar`\tTEXT\n" +
            ");";

    private String tDetailEtatBesoin = "CREATE TABLE `tDetailEtatBesoin` (\n" +
            "\t`IdDetailEB`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t`CodeEtatdeBesoin`\tTEXT,\n" +
            "\t`CodeLigne`\tTEXT,\n" +
            "\t`LibelleDetail`\tTEXT,\n" +
            "\t`Qte`\tREAL,\n" +
            "\t`Pu`\tREAL\n" +
            ");";

    Kp_BatimentDatabaseHelper (Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static Kp_BatimentDatabaseHelper getInstance(Context context)
    {
        return new Kp_BatimentDatabaseHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        updateMyDatabase(db, 0, DB_VERSION);
        db.execSQL(tProject);
        db.execSQL(tLigne);
        db.execSQL(tEtatDeBesoin);
        db.execSQL(tDetailEtatBesoin);

//        for (int a =0; a < 3; a++)
//        {
//            db = this.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("CodeProject", "PR1");
//            contentValues.put("DesignationProject", "Yan masua this is the first essai");
//            contentValues.put("Lieu", "Beni");
//            db.insert("tProject", null, contentValues);
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(tProject);
        db.execSQL(tLigne);
        db.execSQL(tEtatDeBesoin);
        db.execSQL(tDetailEtatBesoin);
        onCreate(db);
//        updateMyDatabase(db, oldVersion, newVersion); // to upgrade the structure if changed

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    // to update or to maintain the structure of my Database
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion < 1)
        {
            db.execSQL(tProject);
            db.execSQL(tLigne);
            db.execSQL(tEtatDeBesoin);
            db.execSQL(tDetailEtatBesoin);
        }
        if (oldVersion == 2)
        {
            db.execSQL(tProject); // to create the tProjet table
            db.execSQL(tLigne); // to create the tLigne table
            db.execSQL(tEtatDeBesoin); // to create the tEtatDeBesoin
            db.execSQL(tDetailEtatBesoin); // to create the tDetailEtatBesoin
//            db.execSQL("ALTER TABLE tProjet RENAME TO tProject");
            // code to run

            for (int a =0; a < 3; a++)
            {
                db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("CodeProject", "PR1");
                contentValues.put("DesignationProject", "Yan masua this is the first essai");
                contentValues.put("Lieu", "Beni");
                db.insert("tProject", null, contentValues);
            }
        }
    }

    // insert the tProjet values
    public int insert_tProjet(String CodeProjet, String DesignationProjet, String DateDebut, String DateFin,
                              String Lieu, String ChefDuProjet, int etat)
    {
        long value = 0;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues projet_values = new ContentValues();
            projet_values.put("CodeProject", CodeProjet);
            projet_values.put("DesignationProject", DesignationProjet);
            projet_values.put("DateDebut", DateDebut);
            projet_values.put("DateFin", DateFin);
            projet_values.put("Lieu", Lieu);
            projet_values.put("ChefDuProjet", ChefDuProjet);
            projet_values.put("etat", etat);
            value = db.insert("tProject", null, projet_values);
        }catch (SQLException ex)
        {
            Log.e("Erreur while inserting ", ex.toString());
        }
        return (int)value;
    }

    // get projects
    public Cursor select_tProjets()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try{
//            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("select * from tProject ", null);
        }catch (SQLException ex)
        {
            Log.e("Erreur while selecting", ex.toString());
        }
        return cursor;
    }
}
