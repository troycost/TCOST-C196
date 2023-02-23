package com.wgu.TCost_C196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.wgu.TCost_C196.Database.Repo;
import com.wgu.tcost_c196.R;
import com.wgu.TCost_C196.Entities.*;


public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repo repo = new Repo(getApplication());
        Term term = new Term(1,"Term 1", "01/03/23","05/31/23" );
        repo.insert(term);
        Course course = new Course(1, "C957", "01/03/23","05/30/23","in-progress","Elon Musk","404-444-4444","elon@TroyU.edu", "test notes", 1);
        repo.insert(course);
        Assessment assessment = new Assessment(1,"Performance Assessment ", "Algebra Exam", "01/30/23","5/30/23",1);
        repo.insert(assessment);
    }


    public void enterButton(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);

    }
}