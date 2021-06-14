package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler extends SQLiteOpenHelper{

    //General database
    static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME="database.db";
    //Users
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_INSTRUCTOR = "instructor";
    public static final String TABLE_STUDENT = "student";

    public static final String USER_TABLES_COLUMN_ID = "id";
    public static final String USER_TABLES_COLUMN_EMAIL = "emailAddress";
    public static final String USER_TABLES_COLUMN_FNAME = "firstName";
    public static final String USER_TABLES_COLUMN_LNAME = "lastName";
    public static final String USER_TABLES_COLUMN_USERNAME = "username";
    public static final String USER_TABLES_COLUMN_PASSWORD = "password";

    public static final String INSTRUCTOR_TABLE_COLUMN_APPROVED = "approved";

//    //Pending Instructors
//    public static final String TABLE_PENDING_INSTRUCTORS = "pendingInstructors";
//    public static final String TABLE_PENDING_INSTRUCTORS_COLUMN_ID = "id";




    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADMIN_TABLE=  "create table "+TABLE_ADMIN+"("+
                USER_TABLES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_TABLES_COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT PRIMARY KEY,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT"+
                ")";
        db.execSQL(CREATE_ADMIN_TABLE);
        String CREATE_INSTRUCTOR_TABLE=  "create table "+TABLE_INSTRUCTOR+"("+
                USER_TABLES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_TABLES_COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT PRIMARY KEY,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT,"+
                INSTRUCTOR_TABLE_COLUMN_APPROVED + " BOOL"+
                ")";
        db.execSQL(CREATE_INSTRUCTOR_TABLE);
        String CREATE_STUDENT_TABLE=  "create table "+TABLE_STUDENT+"("+
                USER_TABLES_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_TABLES_COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT PRIMARY KEY,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT"+
                ")";
        db.execSQL(CREATE_STUDENT_TABLE);

//        String CREATE_PENDING_INSTRUCTORS_TABLE="create table "+TABLE_PENDING_INSTRUCTORS+"("+
//                TABLE_PENDING_INSTRUCTORS_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT" +
//                ")";
//        db.execSQL(CREATE_PENDING_INSTRUCTORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ADMIN);
        db.execSQL("drop table if exists " + TABLE_INSTRUCTOR);
        db.execSQL("drop table if exists " + TABLE_STUDENT);

        onCreate(db);
    }

    public boolean addUser(User user, String userType){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_TABLES_COLUMN_EMAIL,user.getEmailAddress());
        values.put(USER_TABLES_COLUMN_FNAME,user.getFirstName());
        values.put(USER_TABLES_COLUMN_LNAME,user.getLastName());
        values.put(USER_TABLES_COLUMN_USERNAME,user.getUsername());
        values.put(USER_TABLES_COLUMN_PASSWORD,user.getPassword());//TODO: add hashing to password

        switch (userType){
            case(TABLE_ADMIN):
                break;
            case(TABLE_INSTRUCTOR):
                values.put(INSTRUCTOR_TABLE_COLUMN_APPROVED,false);
                break;
            case(TABLE_STUDENT):
                break;
            default:
                return false;
        }
        try {
            db.insert(TABLE_ADMIN, null, values);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


        db.close();
        return true;

    }

    public User findUser(String username,String userType){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="select * from " + userType + " where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'";
        Cursor cursor;
        try {
            cursor= db.rawQuery(query,null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(!cursor.moveToFirst())return null;
        User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        cursor.close();
        db.close();
        return user;
    }

    public boolean deleteUser(String username,String userType){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="select * from " + userType + " where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'";
        Cursor cursor;
        try {
            cursor= db.rawQuery(query,null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        if(!cursor.moveToFirst())return false;
        String idStr = cursor.getString(0);
        db.delete(userType,USER_TABLES_COLUMN_ID+"="+idStr,null);
        cursor.close();
        db.close();
        return true;
    }

    public void approveInstructor(String username){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="update " + TABLE_INSTRUCTOR + " set "+INSTRUCTOR_TABLE_COLUMN_APPROVED+" = "+true+" where "+ USER_TABLES_COLUMN_EMAIL + " = '" + username +"'";
        db.execSQL(query);
        db.close();
    }

}
