package com.wgu.TCost_C196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import com.wgu.TCost_C196.Adapters.Adapter_Assessment;
import com.wgu.TCost_C196.Adapters.Adapter_Course;
import com.wgu.TCost_C196.Adapters.Adapter_Term;
import com.wgu.TCost_C196.Database.Repo;
import com.wgu.TCost_C196.Entities.*;
import java.text.*;
import com.wgu.tcost_c196.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentList extends AppCompatActivity {
    private Repo repo;
    Course currentCourses;
    public static int numAssessment;
    RecyclerView recyclerView;
    public static int termID;
    private int id;
    List<Course> allCourses;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editName;
    EditText editPhone;
    EditText editEmail;
    EditText editNotes;





        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_assesment_list);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            id=getIntent().getIntExtra("courseID", -1);
            termID=getIntent().getIntExtra("termID", -1);
            repo = new Repo(getApplication());
            allCourses= repo.getAllCourses();
            for(Course c:allCourses){
                if(c.getCourseID()==id)currentCourses=c;
            }
            editTitle=findViewById(R.id.editTextCourseTitle);
            editStart=findViewById(R.id.editTextCourseStartDate);
            editEnd=findViewById(R.id.editTextCourseEndDate);
            editStatus=findViewById(R.id.editTextCourseStatus);
            editName=findViewById(R.id.editTextCourseInstructorName);
            editPhone=findViewById(R.id.editTextCourseInstructorPhone);
            editEmail=findViewById(R.id.editTextCourseEmail);
            editNotes=findViewById(R.id.editTextCourseNotes);
            if(currentCourses!=null){
                editTitle.setText(currentCourses.getCourseTitle());
                editStart.setText(currentCourses.getStartDate());
                editEnd.setText(currentCourses.getEndDate());
                editStatus.setText(currentCourses.getStatus());
                editName.setText(currentCourses.getInstructorName());
                editPhone.setText(currentCourses.getInstructorPhone());
                editEmail.setText(currentCourses.getInstructorEmail());
                editNotes.setText(currentCourses.getNotes());
            }
            RecyclerView recyclerView=findViewById(R.id.AssessmentRecyclerView);
            final Adapter_Assessment adapterAssessment =new Adapter_Assessment(this);
            recyclerView.setAdapter(adapterAssessment);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Assessment> filteredAssessments =new ArrayList<>();
            for(Assessment a: repo.getAllAssessments()){
                if(a.getCourseID()==id)filteredAssessments.add(a);
            }
            numAssessment=filteredAssessments.size();
            adapterAssessment.setAssessments(filteredAssessments);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch(item.getItemId()){
                case android.R.id.home:
                    this.finish();
                    return true;

                case R.id.refresh:
                    refreshAssessmentList();
                    return true;

                case R.id.delete:
                    repo.delete(currentCourses);
                    Toast.makeText(getApplicationContext(), "Course deleted successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AssessmentList.this, CourseList.class);
                    finish();

                case R.id.notifystart:
                    String courseDateStart= editStart.getText().toString();
                    String courseTitle = editTitle.getText().toString();
                    String dateFormat = "MM/dd/yy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                    Date date = null;
                    try {
                        date=simpleDateFormat.parse(courseDateStart);
                    } catch (ParseException e){
                        e.printStackTrace();
                    }
                    Long trig = date.getTime();
                    Intent intent1 = new Intent(AssessmentList.this , MyReceiver.class);
                    intent1.putExtra("key", "COURSE " +  courseTitle + " STARTS " + courseDateStart);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert,intent1,0 );
                    AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent);
                    return true;

                case R.id.notifyend:
                    String courseDateEnd= editEnd.getText().toString();
                    courseTitle = editTitle.getText().toString();
                    dateFormat = "MM/dd/yy";
                    simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                    date = null;
                    try {
                        date=simpleDateFormat.parse(courseDateEnd);
                    } catch (ParseException e){
                        e.printStackTrace();
                    }
                    trig = date.getTime();
                    Intent intent2 = new Intent(AssessmentList.this , MyReceiver.class);
                    intent2.putExtra("key", "COURSE " + courseTitle + " ENDS " + courseDateEnd);
                    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(AssessmentList.this, ++MainActivity.numAlert,intent2,0 );
                    AlarmManager alarmManager2 =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent2);
                    return true;

                case R.id.share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TITLE, "NOTES");
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Course " + editTitle.getText().toString() + " notes" +" : "+ editNotes.getText().toString());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_courses, menu);
            return true;
        }

        public void enterAssessmentsDetails(View view) {
            Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
            if(currentCourses!=null) intent.putExtra("courseID",currentCourses.getCourseID());
            intent.putExtra("courseID", id);
            startActivity(intent);
        }


        public void saveCourse(View view) {
            if(id==-1){
//                id=allCourses.get(allCourses.size()-1).getCourseID();
                Course newCourse = new Course(0,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(), editStatus.getText().toString(), editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), editNotes.getText().toString(), termID);
                repo.insert(newCourse);
            }
            else {
                Course oldCourse = new Course(id,editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(), editStatus.getText().toString(), editName.getText().toString(), editPhone.getText().toString(), editEmail.getText().toString(), editNotes.getText().toString(), termID);
                repo.update(oldCourse);
            }
            Intent intent= new Intent(AssessmentList.this,CourseList.class);
            finish();
        }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        id=getIntent().getIntExtra("CourseID",-1);
//        RecyclerView recyclerView=findViewById(R.id.AssessmentRecyclerView);
//        final Adapter_Assessment adapterAssessment =new Adapter_Assessment(this);
//        recyclerView.setAdapter(adapterAssessment);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<Assessment> filterAssessment=new ArrayList<>();
//        for(Assessment assessment: repo.getAllAssessments()){
//            if(assessment.getAssessmentID()==id)filterAssessment.add(assessment);
//        }
//        numAssessment=filterAssessment.size();
//        adapterAssessment.setAssessments(filterAssessment);
//    }
//@Override
//protected void onResume() {
//    super.onResume();
//    id=getIntent().getIntExtra("CourseID",-1);
//    RecyclerView recyclerView=findViewById(R.id.AssessmentRecyclerView);
//    final Adapter_Assessment adapter =new Adapter_Assessment(this);
//    recyclerView.setAdapter(adapter);
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    List<Assessment> filterAssessment=new ArrayList<>();
//
//    numAssessment=repo.getAllAssessments().size();
//    adapter.setAssessments(repo.getAllAssessments());
//}


        private void refreshAssessmentList(){
            recyclerView = findViewById(R.id.AssessmentRecyclerView);
            final Adapter_Assessment adapter=new Adapter_Assessment(this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Assessment> filteredAssessments = new ArrayList<>();
            List<Assessment> allAssessments = repo.getAllAssessments();
            for (Assessment a : allAssessments) {
                if (a.getCourseID() == id)
                    filteredAssessments.add(a);
            }
            numAssessment = filteredAssessments.size();
            adapter.setAssessments(filteredAssessments);
        }


    }