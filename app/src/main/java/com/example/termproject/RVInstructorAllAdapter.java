package com.example.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVInstructorAllAdapter extends RecyclerView.Adapter<RVInstructorAllAdapter.ViewHolder>{
    Context context;
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> instructors = new ArrayList<>();
    String thisInstructor;

    public RVInstructorAllAdapter(Context context, ArrayList<String> names, ArrayList<String> codes,ArrayList<String> instructors,String thisInstructor) {
        this.context = context;
        this.names = names;
        this.codes = codes;
        this.instructors = instructors;
        this.thisInstructor =thisInstructor;

    }

    @Override
    public RVInstructorAllAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instructor_all_courses,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVInstructorAllAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.code.setText(codes.get(position));
        holder.instructor.setText(instructors.get(position));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instructors.get(position).equals("Available!")){

                    dbHandler.addInstructorToCourse(codes.get(position),thisInstructor);
                }
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
        TextView instructor;
        ConstraintLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_InstructorAll);
            name = itemView.findViewById(R.id.textViewName_InstructorAll);
            code = itemView.findViewById(R.id.textViewCode_InstructorAll);
            instructor = itemView.findViewById(R.id.textViewInstructor_InstructorAll);

        }
    }
}
