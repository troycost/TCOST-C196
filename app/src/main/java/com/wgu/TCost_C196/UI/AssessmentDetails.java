package com.wgu.TCost_C196.UI;

import static com.wgu.TCost_C196.UI.MainActivity.numAlert;

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
import com.wgu.TCost_C196.Database.Repo;
import com.wgu.TCost_C196.Entities.*;
import com.wgu.tcost_c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class AssessmentDetails extends AppCompatActivity {

    private Repo repo;
    private int id;
    private int courseID;

    public static int numAssessment;
    List<Assessment> allAssessments;
    Assessment currentAssessment;
    EditText editType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        id=getIntent().getIntExtra("assessmentID", -1);
        courseID=getIntent().getIntExtra("courseID", -1);
        repo = new Repo(getApplication());
        allAssessments= repo.getAllAssessments();
        for(Assessment a:allAssessments){
            if(a.getAssessmentID()==id)currentAssessment=a;
        }
        editType=findViewById(R.id.editTextAssessmentType);
        editTitle=findViewById(R.id.editTextAssessmentTitle);
        editStart=findViewById(R.id.editTextAssessmentStartDate);
        editEnd=findViewById(R.id.editTextAssessmentEndDate);
        if(currentAssessment!=null){
            editType.setText(currentAssessment.getType());
            editTitle.setText(currentAssessment.getTitle());
            editStart.setText(currentAssessment.getStartDate());
            editEnd.setText(currentAssessment.getEndDate());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.delete:
                repo.delete(currentAssessment);
                Toast.makeText(getApplicationContext(), "Assessment deleted successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                finish();

            case R.id.notifystart:
                String assessmentDateStart= editStart.getText().toString();
                String assessmentTitle = editTitle.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                Date date = null;
                try {
                    date=simpleDateFormat.parse(assessmentDateStart);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long trig = date.getTime();
                Intent intent1 = new Intent(AssessmentDetails.this , MyReceiver.class);
                intent1.putExtra("key", assessmentTitle + " STARTS TODAY " + assessmentDateStart);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AssessmentDetails.this, ++numAlert,intent1,0 );
                AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent);
                return true;

            case R.id.notifyend:
                String assessmentDateEnd= editEnd.getText().toString();
                assessmentTitle = editTitle.getText().toString();
                dateFormat = "MM/dd/yy";
                simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
                date = null;
                try {
                    date=simpleDateFormat.parse(assessmentDateEnd);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                trig = date.getTime();
                intent1 = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent1.putExtra("key", assessmentTitle +" ENDS TODAY "+ assessmentDateEnd);
                pendingIntent = PendingIntent.getBroadcast(AssessmentDetails.this, ++numAlert, intent1, 0);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trig,pendingIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessments, menu);
        return true;
    }

    public void saveAssessment(View view) {
        if(id==-1){
//            id=allAssessments.get(allAssessments.size()-1).getAssessmentID();
            Assessment newAssessment = new Assessment(0,editType.getText().toString(), editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(), courseID);
            repo.insert(newAssessment);
        }
        else {
            Assessment updateAssessment = new Assessment(id, editType.getText().toString(),editTitle.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(), courseID);
            repo.update(updateAssessment);
        }
        Intent intent= new Intent(AssessmentDetails.this,AssessmentList.class);
        finish();
    }

}