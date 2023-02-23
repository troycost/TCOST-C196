package com.wgu.TCost_C196.Database;


import android.content.Context;
import androidx.room.*;
import com.wgu.TCost_C196.Entities.*;
import com.wgu.TCost_C196.DAO.*;

@Database(entities = {Course.class}, version= 4, exportSchema = false)
public abstract class DatabaseForCourse extends RoomDatabase {
    public abstract DAOForCourse courseDAO();

    public static volatile DatabaseForCourse INSTANCE;

    static DatabaseForCourse getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseForCourse.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseForCourse.class, "CourseDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
