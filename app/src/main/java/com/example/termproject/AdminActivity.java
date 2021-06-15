package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    Button instructorButton;
    Button studentButton;
    Button courseButton;
    RecyclerView usersRecyclerView;
    EditText codeEditText;
    EditText nameEditText;
    Button addCourseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();

        instructorButton = (Button) findViewById(R.id.buttonInstructor_AdminPage);
        studentButton = (Button) findViewById(R.id.buttonStudent_AdminPage);
        courseButton = (Button) findViewById(R.id.buttonCourse_AdminPage);
        addCourseButton = (Button) findViewById(R.id.buttonAddCourse_AdminPage);
        usersRecyclerView= (RecyclerView) findViewById(R.id.RVUsers_AdminPage);
        codeEditText = (EditText) findViewById(R.id.editTextCode_AdminPage);
        nameEditText = (EditText) findViewById(R.id.editTextName_AdminPage);

        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInstructors();
            }
        });
        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStudents();
            }
        });
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCourses();
            }
        });
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCourse();
            }
        });

    }

    private void loadStudents() {
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<Student> students = dbHandler.getStudents();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        for(Student s : students){
            names.add(s.getFirstName()+" "+ s.getLastName());
            usernames.add(s.getUsername());
        }
        RVAdminStudentAdapter adapter = new RVAdminStudentAdapter(this,names,usernames);
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadInstructors() {
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<Instructor> instructors = dbHandler.getInstructors();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Boolean> isApproved = new ArrayList<>();
        for(Instructor i : instructors){
            names.add(i.getFirstName()+" "+ i.getLastName());
            usernames.add(i.getUsername());
            isApproved.add(i.isApproved());
        }
        RVAdminInstructorAdapter adapter = new RVAdminInstructorAdapter(this,names,usernames,isApproved);
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    void loadCourses(){
        DBHandler dbHandler = new DBHandler(this);
        ArrayList<Course> courses = dbHandler.getCourses();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        for(Course c : courses){
            names.add(c.getName());
            codes.add(c.getCourseCode());
        }
        RVAdminCourseAdapter adapter = new RVAdminCourseAdapter(this,names,codes);
        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void makeCourse(){
        DBHandler dbHandler = new DBHandler(this);
        Course course = new Course(codeEditText.getText().toString(),nameEditText.getText().toString());
        dbHandler.addCourse(course);
        codeEditText.setText("");
        nameEditText.setText("");
    }

}