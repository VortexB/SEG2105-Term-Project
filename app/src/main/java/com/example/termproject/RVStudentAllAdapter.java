package com.example.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVStudentAllAdapter extends RecyclerView.Adapter<RVStudentAllAdapter.ViewHolder>{
    Context context;
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    String thisStudent;

    public RVStudentAllAdapter(Context context, ArrayList<String> names, ArrayList<String> days,ArrayList<String> times,String thisStudent) {
        this.context = context;
        this.names = names;
        this.days = days;
        this.times = times;
        this.thisStudent =thisStudent;

    }

    @Override
    public RVStudentAllAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_all_courses,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVStudentAllAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.day.setText(days.get(position));
        holder.hour.setText(times.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dbHandler.addClassToStudent(thisStudent, dbHandler.findCourseByName(names.get(position)))) {
                        Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Failed to Add Class", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context.getApplicationContext(), "Error in the System", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView day;
        TextView name;
        TextView hour;
        ConstraintLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_StudentAll);
            name = itemView.findViewById(R.id.textViewName_StudentAll);
            day = itemView.findViewById(R.id.textViewDays_StudentAll);
            hour = itemView.findViewById(R.id.textViewHours_StudentAll);

        }
    }
}
