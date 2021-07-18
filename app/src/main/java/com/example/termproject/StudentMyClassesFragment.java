package com.example.termproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentMyClassesFragment extends Fragment {

    String studentName;

    public StudentMyClassesFragment(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_student_owend_classes, container, false);
        RecyclerView rv = v.findViewById(R.id.rvOwnedCourses_Student);
        DBHandler dbHandler = new DBHandler(v.getContext());
        ArrayList<Course> courses = dbHandler.getCoursesStudent(studentName);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> days = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();
        ArrayList<String> descs = new ArrayList<>();
        for (Course c : courses) {
            if(c==null)continue;
            names.add(c.getName());
            descs.add(c.getCourseDescription());
            days.add(c.getDays());
            times.add(c.getTimes());
        }
        RVStudentOwnedAdapter adapter = new RVStudentOwnedAdapter(v.getContext(), names, descs,days, times,studentName);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }
}