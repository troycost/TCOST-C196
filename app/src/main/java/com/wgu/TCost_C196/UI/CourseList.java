package com.wgu.TCost_C196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.wgu.TCost_C196.Adapters.Adapter_Course;
import com.wgu.TCost_C196.Database.Repo;
import com.wgu.TCost_C196.Entities.*;
import com.wgu.tcost_c196.R;

import java.util.*;
public class CourseList extends AppCompatActivity {
        private Repo repo;
        EditText editTitle;
        EditText editStart;
        EditText editEnd;
        Term currentTerm;
        public static int numCourses;
        RecyclerView recyclerView;
        private int id;
        List<Term> allTerms;
        private Course currentCourses;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_course_list);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            id=getIntent().getIntExtra("termID", -1);
            repo = new Repo(getApplication());
            allTerms= repo.getAllTerms();
            for(Term t:allTerms){
                if(t.getTermID()==id)currentTerm=t;
            }
            editTitle=findViewById(R.id.editTextTermTitle);
            editStart=findViewById(R.id.editTextTermStartDate);
            editEnd=findViewById(R.id.editTextTermEndDate);
            if(currentTerm!=null){
                editTitle.setText(currentTerm.getTermTitle());
                editStart.setText(currentTerm.getStartDate());
                editEnd.setText(currentTerm.getEndDate());
            }
            RecyclerView recyclerView=findViewById(R.id.CourseRecyclerView);
            final Adapter_Course adapterCourse =new Adapter_Course(this);
            recyclerView.setAdapter(adapterCourse);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses=new ArrayList<>();
            for(Course c: repo.getAllCourses()){
                if(c.getTermID()==id)filteredCourses.add(c);
            }

            numCourses=filteredCourses.size();
            adapterCourse.setCourses(filteredCourses);



        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;


                case R.id.delete:
                    if (numCourses == 0) {
                        repo.delete(currentTerm);
                        Toast.makeText(getApplicationContext(), "Term was successfully deleted.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CourseList.this, TermList.class);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "This term cannot be deleted because of associated courses", Toast.LENGTH_LONG).show();
                    }
                    return true;

                case R.id.refresh:
                    refreshCourseList();
                    return true;



            }
            return super.onOptionsItemSelected(item);
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_term, menu);
            return true;
        }

        public void saveCourse(View view){

        }


        public void enterAssessments(View view) {
            Intent intent=new Intent(CourseList.this, AssessmentList.class);
            if(currentCourses!=null) intent.putExtra("courseID",currentCourses.getTermID());
            intent.putExtra("termID", id);
            startActivity(intent);
        }



        private void refreshCourseList(){
            recyclerView = findViewById(R.id.CourseRecyclerView);
            final Adapter_Course adapter=new Adapter_Course(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses = new ArrayList<>();
            List<Course> allCourses = repo.getAllCourses();
            for (Course c : allCourses) {
                if (c.getTermID() == id)
                    filteredCourses.add(c);
            }
            numCourses = filteredCourses.size();
            adapter.setCourses(filteredCourses);
        }



    @Override
    protected void onResume() {
        super.onResume();
        id=getIntent().getIntExtra("termID",-1);
        RecyclerView recyclerView=findViewById(R.id.CourseRecyclerView);
        final Adapter_Course adapterCourse =new Adapter_Course(this);
        recyclerView.setAdapter(adapterCourse);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses=new ArrayList<>();
        for(Course c: repo.getAllCourses()){
            if(c.getTermID()==id)filteredCourses.add(c);
        }
        numCourses=filteredCourses.size();
        adapterCourse.setCourses(filteredCourses);
        for(Term term:repo.getAllTerms()) {
            if (term.getTermID() == id){
                editTitle.setText(term.getTermTitle());
                editStart.setText(term.getStartDate());
                editEnd.setText(term.getEndDate());


            }
        }
    }



    public void saveTerm(View view) {

            if(id==-1){
//                id=allTerms.get(allTerms.size()-1).getTermID();
                Term t = new Term(0,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
                repo.insert(t);
            }
            else {
                Term t= new Term(id,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
                repo.update(t);
            }
            Intent intent= new Intent(CourseList.this,TermList.class);
            startActivity(intent);
        }



}
