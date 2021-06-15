package com.example.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdminInstructorAdapter extends RecyclerView.Adapter<RVAdminInstructorAdapter.ViewHolder>{
    Context context;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();
    ArrayList<Boolean> approvedList = new ArrayList<>();

    public RVAdminInstructorAdapter(Context context, ArrayList<String> names, ArrayList<String> usernames, ArrayList<Boolean> approved) {
        this.context = context;
        this.names = names;
        this.usernames = usernames;
        this.approvedList = approved;
    }

    @Override
    public RVAdminInstructorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_instructor,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVAdminInstructorAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.username.setText(usernames.get(position));
        holder.approved.setActivated(approvedList.get(position));
        holder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = holder.approved.isChecked();
                dbHandler.changeApproveInstructor(holder.username.getText().toString(),result);
                System.out.println("CHANGED TO: "+result);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteUser(holder.username.getText().toString(),DBHandler.TABLE_INSTRUCTOR);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView username;
        CheckBox approved;
        Button deleteButton;
        ConstraintLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName_AdminInstructor);
            username = itemView.findViewById(R.id.textViewUsername_AdminInstructor);
            approved = itemView.findViewById(R.id.CBApproved_AdminInstructor);
            deleteButton = itemView.findViewById(R.id.buttonDelete_AdminInstructor);
        }
    }
}
