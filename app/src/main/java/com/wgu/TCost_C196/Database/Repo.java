package com.wgu.TCost_C196.Database;

import android.app.Application;
import com.wgu.TCost_C196.Entities.*;
import com.wgu.TCost_C196.DAO.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repo {
    private DAOForTerm mDAOForTerm;
    private DAOForCourse mDAOForCourse;
    private DAOForAssessments mDAOForAssessments;
    private List<Term>mAllTerms;
    private List<Course>mAllCourses;
    private List<Assessment>mAllAssessments;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repo(Application application){
        DatabaseForTerm db = DatabaseForTerm.getDatabase(application);
        mDAOForTerm = db.termDAO();

        DatabaseForCourse cdb = DatabaseForCourse.getDatabase(application);
            mDAOForCourse = cdb.courseDAO();


        DatabaseForAssessments adb = DatabaseForAssessments.getDatabase(application);
            mDAOForAssessments = adb.assessmentDAO();


    }

    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms= mDAOForTerm.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            mDAOForTerm.insert(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Term term){
        databaseExecutor.execute(()->{
            mDAOForTerm.update(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Term term){
        databaseExecutor.execute(()->{
            mDAOForTerm.delete(term);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses= mDAOForCourse.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course){
        databaseExecutor.execute(()->{
            mDAOForCourse.insert(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Course course){
        databaseExecutor.execute(()->{
            mDAOForCourse.update(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Course course){
        databaseExecutor.execute(()->{
            mDAOForCourse.delete(course);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments= mDAOForAssessments.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mDAOForAssessments.insert(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mDAOForAssessments.update(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mDAOForAssessments.delete(assessment);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
