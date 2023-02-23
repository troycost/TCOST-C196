package com.wgu.TCost_C196.Adapters;

import android.content.*;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import com.wgu.TCost_C196.Entities.*;
import com.wgu.tcost_c196.R;
import com.wgu.TCost_C196.UI.AssessmentDetails;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_Assessment extends RecyclerView.Adapter<Adapter_Assessment.AssessmentViewHolder>{
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private final TextView assessmentItemView2;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.textView7);
            assessmentItemView2=itemView.findViewById(R.id.textView8);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("type", current.getType());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }

    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public Adapter_Assessment(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @NotNull
    @Override

    public AssessmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(com.wgu.tcost_c196.R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AssessmentViewHolder holder, int position) {

        if(mAssessments!=null){
            final Assessment current = mAssessments.get(position);
            holder.assessmentItemView2.setText(current.getTitle());
            holder.assessmentItemView.setText(Integer.toString(current.getAssessmentID()));
        }
        else {
            holder.assessmentItemView2.setText("No Assessment Title");
            holder.assessmentItemView.setText("No Assessment ID");
        }
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssessments!= null)
            return mAssessments.size();
        else return 0;
    }
}
