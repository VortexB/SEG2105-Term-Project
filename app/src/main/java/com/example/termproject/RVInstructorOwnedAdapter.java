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

public class RVInstructorOwnedAdapter extends RecyclerView.Adapter<RVInstructorOwnedAdapter.ViewHolder>{
    Context context;
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> caps = new ArrayList<>();
    ArrayList<String> descs = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();

    public RVInstructorOwnedAdapter(Context context, ArrayList<String> names, ArrayList<String> codes,ArrayList<String> descs,ArrayList<String> dates,ArrayList<Integer> caps) {
        this.context = context;
        this.names = names;
        this.codes = codes;
        this.caps=caps;
        this.dates=dates;
        this.descs=descs;
    }

    @Override
    public RVInstructorOwnedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instructor_owned_courses,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVInstructorOwnedAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.code.setText(codes.get(position));
        holder.cap.setText(String.valueOf(caps.get(position)));
        holder.desc.setText(descs.get(position));
        if(dates.get(position)!=null) {
            String[] spiltDates_Days_Times = dates.get(position).split("/");
            if (spiltDates_Days_Times.length > 1) {
                holder.days.setText(spiltDates_Days_Times[0]);
                holder.times.setText(spiltDates_Days_Times[1]);
            }
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.removeInstructorFromCourse(codes.get(position));
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.addDetailsToCourse(codes.get(position),Integer.parseInt(holder.cap.getText().toString()),holder.desc.getText().toString(),holder.days.getText().toString()+"/"+holder.times.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView code;
        TextView name;
        EditText cap;
        EditText desc;
        EditText days;
        EditText times;
        Button update;
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.buttonDelete_OwnedInstructor);
            update = itemView.findViewById(R.id.buttonUpdate_OwnedInstructor);
            name = itemView.findViewById(R.id.textViewName_InstructorOwned);
            code = itemView.findViewById(R.id.textViewCode_InstructorOwned);
            cap = itemView.findViewById(R.id.editTextSearch_AllInstructor);
            desc = itemView.findViewById(R.id.editTextDescription_OwnedInstructor);
            days = itemView.findViewById(R.id.editTextDays_OwnedInstructor);
            times = itemView.findViewById(R.id.editTextTimes_OwnedInstructor);
        }
    }
}
