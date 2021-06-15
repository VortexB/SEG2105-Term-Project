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

public class RVAdminStudentAdapter extends RecyclerView.Adapter<RVAdminStudentAdapter.ViewHolder>{
    Context context;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> usernames = new ArrayList<>();

    public RVAdminStudentAdapter(Context context, ArrayList<String> names, ArrayList<String> usernames) {
        this.context = context;
        this.names = names;
        this.usernames = usernames;
    }

    @Override
    public RVAdminStudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_student,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVAdminStudentAdapter.ViewHolder holder, int position) {
        DBHandler dbHandler = new DBHandler(context);
        holder.name.setText(names.get(position));
        holder.username.setText(usernames.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.deleteUser(holder.username.getText().toString(),DBHandler.TABLE_STUDENT);
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
        Button deleteButton;
        ConstraintLayout parent;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName_AdminStudent);
            username = itemView.findViewById(R.id.textViewUsername_AdminStudent);
            deleteButton = itemView.findViewById(R.id.buttonDelete_AdminStudent);
        }
    }
}
