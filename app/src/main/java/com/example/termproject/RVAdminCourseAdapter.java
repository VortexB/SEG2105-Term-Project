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

public class RVAdminCourseAdapter extends RecyclerView.Adapter<RVAdminCourseAdapter.ViewHolder>{
    Context context;
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();

    public RVAdminCourseAdapter(Context context, ArrayList<String> names, ArrayList<String> codes) {
        this.context = context;
        this.names = names;
        this.codes = codes;

    }

    @Override
    public RVAdminCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_course,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVAdminCourseAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.code.setText(codes.get(position));
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.changeCourse(codes.get(position),holder.code.getText().toString(),holder.name.getText().toString());
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteCourse(holder.code.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EditText code;
        EditText name;
        Button updateButton;
        Button deleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.editTextName_AdminCourse);
            code = itemView.findViewById(R.id.editTextCode_AdminCourse);
            updateButton = itemView.findViewById(R.id.buttonUpdate_AdminCourse);
            deleteButton = itemView.findViewById(R.id.buttonDelete_AdminCourse);
        }
    }
}
