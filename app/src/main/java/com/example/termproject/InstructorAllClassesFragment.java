package com.example.termproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class InstructorAllClassesFragment extends Fragment {

    String instructorName;
    View v;

    Button update;
    EditText search;
    public InstructorAllClassesFragment(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_instructor_all_classes, container, false);
        search=v.findViewById(R.id.editTextSearch_AllInstructor);
        update = v.findViewById(R.id.buttonUpdate_FragAllInstructor);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView(search.getText().toString());
            }
        });
        return updateView(null);
    }

    public View updateView(String search) {
        RecyclerView rv= v.findViewById(R.id.rvAllCourses_Instructor);
        DBHandler dbHandler = new DBHandler(v.getContext());
        ArrayList<Course> courses = dbHandler.searchCourses(search);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> instructors = new ArrayList<>();
        for(Course c : courses){
            names.add(c.getName());
            codes.add(c.getCourseCode());
            if(c.getInstructor()!=null) {
                instructors.add(c.getInstructor().getLastName() + "," + c.getInstructor().getFirstName());
            }else{
                instructors.add("Available!");
            }
        }
        RVInstructorAllAdapter adapter = new RVInstructorAllAdapter(v.getContext(),names,codes,instructors,instructorName);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }


}