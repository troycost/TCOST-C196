package com.wgu.TCost_C196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.TCost_C196.Entities.Course;
import com.wgu.tcost_c196.R;
import com.wgu.TCost_C196.UI.AssessmentList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_Course extends RecyclerView.Adapter<Adapter_Course.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private final TextView courseItemView2;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.textView5);
            courseItemView2=itemView.findViewById(R.id.textView6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, AssessmentList.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }

    }
    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public Adapter_Course(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @NotNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseViewHolder holder, int position) {
        if(mCourses!=null){
            final Course current = mCourses.get(position);
            holder.courseItemView2.setText(current.getCourseTitle());
            holder.courseItemView.setText(Integer.toString(current.getCourseID()));
        }
        else {
            holder.courseItemView2.setText("No Course Title");
            holder.courseItemView.setText("No Course ID");
        }

    }

    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourses!= null)
            return mCourses.size();
        else return 0;
    }
}
