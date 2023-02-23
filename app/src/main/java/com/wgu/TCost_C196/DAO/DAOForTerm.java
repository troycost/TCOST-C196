package com.wgu.TCost_C196.DAO;

import androidx.room.*;

import com.wgu.TCost_C196.Entities.Term;

import java.util.List;

@Dao
public interface DAOForTerm {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM term_table ORDER BY termID ASC")
    List<Term> getAllTerms();

    @Query("DELETE FROM term_table")
    void deleteAllTerms();


}
