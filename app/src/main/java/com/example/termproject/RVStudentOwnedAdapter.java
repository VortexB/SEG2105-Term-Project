package com.example.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVStudentOwnedAdapter extends RecyclerView.Adapter<RVStudentOwnedAdapter.ViewHolder>{
    Context context;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> descs = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();

    String thisStudent;

    public RVStudentOwnedAdapter(Context context, ArrayList<String> names, ArrayList<String> descs, ArrayList<String> days, ArrayList<String> times, String thisStudent) {
        this.context = context;
        this.names = names;
        this.descs = descs;
        this.days = days;
        this.times = times;
        this.thisStudent = thisStudent;
    }

    @Override
    public RVStudentOwnedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_owned_courses,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVStudentOwnedAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.desc.setText(descs.get(position));
        holder.day.setText(days.get(position));
        holder.time.setText(times.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.removeCourseFromStudent(thisStudent,dbHandler.findCourseByName(names.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView day;
        TextView desc;
        TextView time;
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.buttonDelete_StudentOwned);
            name = itemView.findViewById(R.id.textViewName_StudentOwned);
            time = itemView.findViewById(R.id.textViewHours_StudentOwned);
            day = itemView.findViewById(R.id.textViewDays_StudentOwned);
            desc = itemView.findViewById(R.id.textViewDesc_StudentOwned);
        }
    }
}
