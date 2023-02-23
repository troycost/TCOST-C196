package com.wgu.TCost_C196.Database;

import android.content.Context;
import androidx.room.*;
import com.wgu.TCost_C196.Entities.*;
import com.wgu.TCost_C196.DAO.*;

@Database(entities = {Term.class}, version= 4, exportSchema = false)
public abstract class DatabaseForTerm extends RoomDatabase {
    public abstract DAOForTerm termDAO();

    public static volatile DatabaseForTerm INSTANCE;

    static DatabaseForTerm getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseForTerm.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseForTerm.class, "TC-Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
