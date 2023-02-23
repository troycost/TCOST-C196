package com.wgu.TCost_C196.DAO;


import androidx.room.*;


import com.wgu.TCost_C196.Entities.*;

import java.util.List;

@Dao
public interface DAOForAssessments {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();
}
