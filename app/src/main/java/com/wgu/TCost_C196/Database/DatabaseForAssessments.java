package com.wgu.TCost_C196.Database;

import android.content.Context;

import androidx.room.*;

import com.wgu.TCost_C196.Entities.*;
import com.wgu.TCost_C196.DAO.*;
@Database(entities = {Assessment.class}, version= 4, exportSchema = false)
public abstract class DatabaseForAssessments extends RoomDatabase {
    public abstract DAOForAssessments assessmentDAO();

    public static volatile DatabaseForAssessments INSTANCE;

    static DatabaseForAssessments getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseForAssessments.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseForAssessments.class, "AssessmentDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
