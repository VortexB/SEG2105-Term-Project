package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText email;
    private EditText password1;
    private EditText password2;
    private TextView logger;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_Register);
        firstname = (EditText) findViewById(R.id.editTextFirstname_Register);
        lastname = (EditText) findViewById(R.id.editTextLastname_Register);
        username = (EditText) findViewById(R.id.editTextUsername_Register);
        email = (EditText) findViewById(R.id.editTextEmail_Register);
        password1 = (EditText) findViewById(R.id.editTextPassword1_Register);
        password2 = (EditText) findViewById(R.id.editTextPassword2_Register);
        logger = (TextView) findViewById(R.id.textViewLogger_Register);
        registerButton = (Button) findViewById(R.id.buttonRegister_Register);
    }

    public void createUser(View view){
        DBHandler dbHandler = new DBHandler(this);
        String fn = firstname.getText().toString();
        String ln = lastname.getText().toString();
        String un = username.getText().toString();
        String eml = email.getText().toString();
        String pw = password1.getText().toString();
        if(fn.chars().allMatch(Character::isAlphabetic)){
            logger.setText("First Name must only contain letters");
            return;
        }
        if(ln.chars().allMatch(Character::isAlphabetic)){
            logger.setText("Last Name must only contain letters");
            return;
        }
        if(un.length()<3){
            logger.setText("Username must be over 2 characters");
        }
        if(!eml.contains("@")){
            logger.setText("Invalid Email");
            return;
        }
        if(!pw.equals(password2.getText().toString())){
            logger.setText("Passwords do not match");
            return;
        }
        User user = new User(-1,fn,ln,un,eml,pw);
        boolean result=false;
        switch (radioGroup.getCheckedRadioButtonId()){
            case(R.id.radioButtonStudent_Register):
                result=dbHandler.addUser(user,dbHandler.TABLE_STUDENT);
                break;
            case(R.id.radioButtonInstructor_Register):
                result=dbHandler.addUser(user,dbHandler.TABLE_INSTRUCTOR);
                break;
            case(R.id.radioButtonAdmin_Register):
                result=dbHandler.addUser(user,dbHandler.TABLE_ADMIN);
                break;
        }
        if(!result){
            logger.setText("Error Username or Email in use");
            return;
        }
        openLoginActivity();

    }

    public void openLoginActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}