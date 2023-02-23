package com.wgu.TCost_C196.DAO;

import androidx.room.*;

import com.wgu.TCost_C196.Entities.Course;

import java.util.List;

@Dao
public interface DAOForCourse {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<Course> getAllCourses();
}
