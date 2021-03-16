package com.soft.ssvapp.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Entity_Project.class, Entity_Besoin.class,
        Entity_DetailBesoin.class,
        Entity_Ligne.class, Entity_Compte.class,
        Entity_Operation.class, Entity_MvtCompte.class, Entity_GroupeCompte.class, Entity_Article.class}, version = 1,
        exportSchema = false)
public abstract class Kp_BatimentData extends RoomDatabase {

    private static Kp_BatimentData instance;
    public abstract tProjectDao projectDao();
    public abstract tEtatDeBesoinDao besoinDao();
    public abstract tDetailBesoinDao detailBesoinDao();
    public abstract tLigneDao ligneDao();
    public abstract tCompteDao compteDao();
    public abstract tMvtCompteDao mvtCompteDao();
    public abstract tOperationDao tOperationDao();
    public abstract tGroupeCompteDao  tGroupeCompteDao();
    public abstract tArticleDao tArticleDao();

    public static synchronized Kp_BatimentData getInstance(Context context)
    {
        if ((instance == null))
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Kp_BatimentData.class, "SSV_DataBase")
                    .fallbackToDestructiveMigration() // in on other link they use this "allowMainThreadQueries() "
                    .allowMainThreadQueries() // in on other link they use this "allowMainThreadQueries() "
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Your migration strategy here
//            database.execSQL("ALTER TABLE tDetailEtatBesoin ADD CONSTRAINT IdDetailEBOnline UNIQUE (id, name)");
//        }
//    };

    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsynckTask(instance).execute();
        }
    };

    private static class PopulateDbAsynckTask extends AsyncTask<Void, Void, Void>
    {
        private tEtatDeBesoinDao besoinDao;

        private PopulateDbAsynckTask(Kp_BatimentData db)
        {
            this.besoinDao = db.besoinDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            besoinDao.insert(new Entity_Besoin("codeEtatBeoin", "designationEtatDeBesoin",
//                    "codeProjet", "demandeur", "dateEmission", "dateValidation",
//                    "dateRequise", "ValiderPar", 0));
//            besoinDao.insert(new Entity_Besoin("codeEtatBeoin", "designationEtatDeBesoin",
//                    "codeProjet", "demandeur", "dateEmission", "dateValidation",
//                    "dateRequise", "ValiderPar", 0));
//            besoinDao.insert(new Entity_Besoin("codeEtatBeoin", "designationEtatDeBesoin",
//                    "codeProjet", "demandeur", "dateEmission", "dateValidation",
//                    "dateRequise", "ValiderPar", 0));
            return null;
        }
    }
}
