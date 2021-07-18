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
        DBHandler dbHandler = new DBHandler(this);//TODO: maybe add check that user isn't in a different table
        String fn = firstname.getText().toString();
        String ln = lastname.getText().toString();
        String un = username.getText().toString();
        String eml = email.getText().toString();
        String pw = password1.getText().toString();
        if(fn.length()==0||ln.length()==0||un.length()==0||eml.length()==0||pw.length()==0){
            logger.setText("No field may be empty");
            return;
        }
        if(!onlyAlpha(fn)){
            logger.setText("First Name must only contain letters");
            return;
        }
        if(!onlyAlpha(ln)){
            logger.setText("Last Name must only contain letters");
            return;
        }
        if(!validUsername(un)){
            logger.setText("Username must be over 2 characters");
            return;
        }
        if(!validEmail(eml)){
            logger.setText("Invalid Email");
            return;
        }
        if(!pw.equals(password2.getText().toString())){
            logger.setText("Passwords do not match");
            return;
        }
        User user = new User(-1,eml,fn,ln,un,pw);
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

    public static boolean onlyAlpha(String string){
        return string.chars().allMatch(Character::isAlphabetic);
    }

    public static boolean validEmail(String string){
        return string.contains("@");
    }
    public static boolean validUsername(String string){
        return string.length()>3;
    }

}