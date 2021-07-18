package com.example.termproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class InstructorMyClassesFragment extends Fragment {

    String instructorName;

    public InstructorMyClassesFragment(String instructorName) {
        this.instructorName = instructorName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_instructor_owned_classes, container, false);
        RecyclerView rv= v.findViewById(R.id.rvOwnedCourses_Instructor);
        DBHandler dbHandler = new DBHandler(v.getContext());
        ArrayList<Course> courses = dbHandler.getCoursesInstructor(instructorName);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<Integer> caps = new ArrayList<>();
        ArrayList<String> descs = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        for(Course c : courses){
            names.add(c.getName());
            codes.add(c.getCourseCode());
            caps.add(c.getStudentCapacity());
            descs.add(c.getCourseDescription());
            dates.add(c.getDates());
        }
        RVInstructorOwnedAdapter adapter = new RVInstructorOwnedAdapter(v.getContext(),names,codes,descs,dates,caps);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }
}

