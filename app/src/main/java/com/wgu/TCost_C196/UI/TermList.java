package com.wgu.TCost_C196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wgu.TCost_C196.Adapters.Adapter_Course;
import com.wgu.TCost_C196.Adapters.Adapter_Term;
import com.wgu.TCost_C196.Database.Repo;
import com.wgu.TCost_C196.Entities.Course;
import com.wgu.TCost_C196.Entities.Term;
import com.wgu.tcost_c196.R;
import java.util.ArrayList;
import java.util.List;


public class TermList extends AppCompatActivity {

    private Repo repo;
    Term currentTerms;
    private RecyclerView recyclerView;
    private int numTerms;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repo =new Repo(getApplication());
        List<Term> allTerms = repo.getAllTerms();

        RecyclerView recyclerView=findViewById(R.id.TermRecyclerView);
        final Adapter_Term adapterTerm =new Adapter_Term(this);
        recyclerView.setAdapter(adapterTerm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterTerm.setTerms(allTerms);
    }
    @Override
    protected void onResume() {
        super.onResume();
        id=getIntent().getIntExtra("termID",-1);
        RecyclerView recyclerView=findViewById(R.id.TermRecyclerView);
        final Adapter_Term adapterTerm =new Adapter_Term(this);
        recyclerView.setAdapter(adapterTerm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Term> filteredCourses=new ArrayList<>();

        numTerms=repo.getAllTerms().size();
        adapterTerm.setTerms(repo.getAllTerms());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;


            case R.id.refresh:
                refreshTermList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recyclerview, menu);
        return true;
    }

    public void enterCourses(View view) {
        Intent intent = new Intent(TermList.this, CourseList.class);
        if(currentTerms!=null) intent.putExtra("termID",currentTerms.getTermID());
        startActivity(intent);
    }

    public void startDate(View view) {
    }

    private void refreshTermList(){
        recyclerView = findViewById(R.id.TermRecyclerView);
        final Adapter_Term adapter=new Adapter_Term(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Term> filteredTerms = new ArrayList<>();
        List<Term> allTerms = repo.getAllTerms();
        /*
        for (TermEntity t : allTerms) {
            if (t.getTermID() == id)
                filteredTerms.add(t);
        }

         */
        numTerms = filteredTerms.size();
        adapter.setTerms(allTerms);
    }



}

