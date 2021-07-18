package com.example.termproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAllClassesFragment extends Fragment {

    String studentName;
    View v;

    Button update;
    EditText search;
    public StudentAllClassesFragment(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_student_all_classes, container, false);
        search=v.findViewById(R.id.editTextSearch_AllStudent);
        update = v.findViewById(R.id.buttonUpdate_FragAllStudent);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(search.getText().toString());
            }
        });
        return updateView(null);
    }

    public View updateView(String search) {
        RecyclerView rv= v.findViewById(R.id.rvAllCourses_Student);
        DBHandler dbHandler = new DBHandler(v.getContext());
        ArrayList<Course> courses = dbHandler.searchCourses(search);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        for(Course c : courses){
            names.add(c.getName());
            days.add(c.getDays());
            times.add(c.getTimes());
        }
        RVStudentAllAdapter adapter = new RVStudentAllAdapter(v.getContext(),names,days,times,studentName);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }


}
