package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
    public static final String STUDENT_TABLE_COLUMN_COURSES = "courses";

    //Courses

    public static final String TABLE_COURSE = "course";
    public static final String COURSE_TABLE_COLUMN_CODE = "code";
    public static final String COURSE_TABLE_COLUMN_NAME = "name";
    public static final String COURSE_TABLE_COLUMN_CAPACITY = "capacity";
    public static final String COURSE_TABLE_COLUMN_DESCRIPTION = "description";
    public static final String COURSE_TABLE_COLUMN_DATES = "dates";
    public static final String COURSE_TABLE_COLUMN_INSTRUCTOR = "instructor";







    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADMIN_TABLE=  "create table "+TABLE_ADMIN+"("+
                USER_TABLES_COLUMN_ID +" INTEGER primary key autoincrement," +
                USER_TABLES_COLUMN_EMAIL + " TEXT not null unique," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT not null unique,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT"+
                ")";
        db.execSQL(CREATE_ADMIN_TABLE);
        String CREATE_INSTRUCTOR_TABLE=  "create table "+TABLE_INSTRUCTOR+"("+
                USER_TABLES_COLUMN_ID +" INTEGER primary key autoincrement," +
                USER_TABLES_COLUMN_EMAIL + " TEXT not null unique," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT not null unique,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT,"+
                INSTRUCTOR_TABLE_COLUMN_APPROVED + " BOOL"+
                ")";
        db.execSQL(CREATE_INSTRUCTOR_TABLE);
        String CREATE_STUDENT_TABLE=  "create table "+TABLE_STUDENT+"("+
                USER_TABLES_COLUMN_ID +" INTEGER primary key autoincrement," +
                USER_TABLES_COLUMN_EMAIL + " TEXT not null unique," +
                USER_TABLES_COLUMN_FNAME + " TEXT,"+
                USER_TABLES_COLUMN_LNAME + " TEXT,"+
                USER_TABLES_COLUMN_USERNAME + " TEXT not null unique,"+
                USER_TABLES_COLUMN_PASSWORD + " TEXT,"+
                STUDENT_TABLE_COLUMN_COURSES + " TEXT not null unique"+
                ")";
        db.execSQL(CREATE_STUDENT_TABLE);
        String CREATE_COURSE_TABLE=  "create table "+TABLE_COURSE+"("+
                COURSE_TABLE_COLUMN_CODE +" TEXT primary key ," +
                COURSE_TABLE_COLUMN_NAME + " TEXT," +
                COURSE_TABLE_COLUMN_CAPACITY + " INT,"+
                COURSE_TABLE_COLUMN_DESCRIPTION + " TEXT,"+
                COURSE_TABLE_COLUMN_DATES + " TEXT,"+
                COURSE_TABLE_COLUMN_INSTRUCTOR + " TEXT,"+
                "foreign key ("+ COURSE_TABLE_COLUMN_INSTRUCTOR + ") references " + TABLE_INSTRUCTOR + " on delete cascade on update cascade" +
                ")";
        db.execSQL(CREATE_COURSE_TABLE);

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
    public boolean addCourse(Course course){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_TABLE_COLUMN_CODE,course.getCourseCode());
        values.put(COURSE_TABLE_COLUMN_NAME,course.getName());
        values.put(COURSE_TABLE_COLUMN_CAPACITY,course.getStudentCapacity());
        values.put(COURSE_TABLE_COLUMN_DESCRIPTION,course.getCourseDescription());
        values.put(COURSE_TABLE_COLUMN_DATES,course.getDates());
        if(course.getInstructor()!=null)
            values.put(COURSE_TABLE_COLUMN_INSTRUCTOR,course.getInstructor().getUsername());
        try {
            db.insert(TABLE_COURSE, null, values);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        db.close();
        return true;
    }
    public Course findCourse(String code){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="select * from " + TABLE_COURSE + " where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        Cursor cursor;
        try {
            cursor= db.rawQuery(query,null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(!cursor.moveToFirst())return null;
        Course course = new Course(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),(Instructor) findUser(cursor.getString(5),TABLE_INSTRUCTOR));
        cursor.close();
        db.close();
        return course;
    }

    public Course findCourseByName(String name){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="select * from " + TABLE_COURSE + " where "+ COURSE_TABLE_COLUMN_NAME + " = '" + name +"'";
        Cursor cursor;
        try {
            cursor= db.rawQuery(query,null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        if(!cursor.moveToFirst())return null;
        Course course = new Course(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),Instructor.fromUser(findUser(cursor.getString(5),TABLE_INSTRUCTOR)));
        cursor.close();
        db.close();
        return course;
    }

    public boolean deleteCourse(String code){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="select * from " + TABLE_COURSE + " where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        Cursor cursor;
        try {
            cursor= db.rawQuery(query,null);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        if(!cursor.moveToFirst())return false;
        String codeStr = cursor.getString(0);
        db.delete(TABLE_COURSE,COURSE_TABLE_COLUMN_CODE+"= '"+codeStr+"'",null);
        cursor.close();
        db.close();
        return true;
    }

    public void changeCourse(String oldCode,String newCode,String newName){
        SQLiteDatabase db =this.getWritableDatabase();
        if(newCode!=null) {
            if(newCode!=""){
            String query1 = "update " + TABLE_COURSE + " set " + COURSE_TABLE_COLUMN_CODE + " = '" + newCode + "' where " + COURSE_TABLE_COLUMN_CODE + " = '" + oldCode + "'";
            db.execSQL(query1);
            }
        }
        if(newName!=null) {
            if(newName!="") {
                String query2 = "update " + TABLE_COURSE + " set " + COURSE_TABLE_COLUMN_NAME + " = '" + newName + "' where " + COURSE_TABLE_COLUMN_CODE + " = '" + oldCode + "'";
                db.execSQL(query2);
            }
        }
        db.close();
    }

    public ArrayList<Course> getCoursesInstructor(){
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_COURSE,null);
        if(!c.moveToFirst())return courses;
        do{
            courses.add(new Course(c.getString(0),c.getString(1),c.getInt(2),c.getString(3),c.getString(4),Instructor.fromUser(findUser(c.getString(5),TABLE_INSTRUCTOR))));
        }while(c.moveToNext());
        c.close();
        db.close();
        return courses;

    }
    public ArrayList<Course> getCoursesInstructor(String instructor){
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_COURSE+" where "+COURSE_TABLE_COLUMN_INSTRUCTOR+" = '"+instructor+"'",null);
        if(!c.moveToFirst())return courses;
        do{
            courses.add(new Course(c.getString(0),c.getString(1),c.getInt(2),c.getString(3),c.getString(4),Instructor.fromUser(findUser(c.getString(5),TABLE_INSTRUCTOR))));
        }while(c.moveToNext());
        c.close();
        db.close();
        return courses;
    }
    public ArrayList<Course> getCoursesStudent(String student){
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_STUDENT+" where "+USER_TABLES_COLUMN_USERNAME+" = '"+student+"'",null);
        if(!c.moveToFirst())return courses;
        String courseNames =c.getString(6);
        for(String courseN: courseNames.split("/")){
            courses.add(findCourseByName(courseN));
        }
        c.close();
        db.close();
        return courses;
    }
    public ArrayList<Course> searchCourses(String search){
        if(search==null)
            return getCoursesInstructor();
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_COURSE+" where "+COURSE_TABLE_COLUMN_CODE+" like '%"+search+"%' or "+COURSE_TABLE_COLUMN_NAME+" like '%"+search+"%' or " +COURSE_TABLE_COLUMN_DATES+" like '%"+search+"%'",null);
        if(!c.moveToFirst())return courses;
        do{
            courses.add(new Course(c.getString(0),c.getString(1),c.getInt(2),c.getString(3),c.getString(4),Instructor.fromUser(findUser(c.getString(5),TABLE_INSTRUCTOR))));
        }while(c.moveToNext());
        c.close();
        db.close();
        return courses;

    }

    public void addInstructorToCourse(String code,String instructorName){
        SQLiteDatabase db =this.getWritableDatabase();
        String query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_INSTRUCTOR+" = '"+instructorName+"' where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        db.close();
    }

    public void addDetailsToCourse(String code,int cap,String desc,String dates){
        SQLiteDatabase db =this.getWritableDatabase();
        String query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_CAPACITY+" = "+cap+" where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_DESCRIPTION+" = '"+desc+"' where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_DATES+" = '"+dates+"' where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        db.close();
    }

    public void removeInstructorFromCourse(String code){
        SQLiteDatabase db =this.getWritableDatabase();
        String query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_INSTRUCTOR+" = null where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_DESCRIPTION+" = null where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_DATES+" = null where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        query="update " + TABLE_COURSE + " set "+COURSE_TABLE_COLUMN_CAPACITY+" = 0 where "+ COURSE_TABLE_COLUMN_CODE + " = '" + code +"'";
        db.execSQL(query);
        db.close();
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
                values.put(STUDENT_TABLE_COLUMN_COURSES,"");
                break;
            default:
                return false;
        }
        try {
            db.insert(userType, null, values);
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

    public ArrayList<Instructor> getInstructors(){
        ArrayList<Instructor> instructors = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_INSTRUCTOR,null);
        if(!c.moveToFirst())return instructors;
        do{
            instructors.add(new Instructor(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getInt(6)==1));
        }while(c.moveToNext());
        c.close();
        db.close();
        return instructors;

    }
    public ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_STUDENT,null);
        if(!c.moveToFirst())return students;
        do{
            students.add(new Student(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5)));
        }while(c.moveToNext());
        c.close();
        db.close();
        return students;

    }

    public void changeApproveInstructor(String username,boolean value){
        SQLiteDatabase db =this.getWritableDatabase();

        String query="update " + TABLE_INSTRUCTOR + " set "+INSTRUCTOR_TABLE_COLUMN_APPROVED+" = "+value+" where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'";
        db.execSQL(query);
        db.close();
    }

    public boolean isApproved(String username){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_INSTRUCTOR + " where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'",null);
        if(!c.moveToFirst()) return false;
        return c.getInt(6)==1;
    }
    public void removeCourseFromStudent(String username,Course course){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_STUDENT + " where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'",null);
        if(!c.moveToFirst()) return;
        String removed =c.getString(6);
        removed=removed.replace("/"+course.getName(),"");
        String query="update " + TABLE_STUDENT + " set "+STUDENT_TABLE_COLUMN_COURSES+" = '"+removed+"' where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'";
        db.execSQL(query);
        db.close();
    }

    public boolean addClassToStudent(String username,Course course){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor c= db.rawQuery("select * from " + TABLE_STUDENT + " where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'",null);
        if(!c.moveToFirst()) return false;
        String prev =c.getString(6);
        db.close();

        //System.out.println(prev);
            for(String courseN: prev.split("/")) {
                //System.out.println(courseN);
                Course tmp =  findCourseByName(courseN);
                if(tmp==null)continue;
                System.out.println(course.getDates());
                System.out.println(tmp.getDates());
                if (!timeConflict(course.getDates(), tmp.getDates())||!timeConflict(tmp.getDates(),course.getDates())) {
                    return false;
                }
            }
            db =this.getWritableDatabase();
        String query="update " + TABLE_STUDENT + " set "+STUDENT_TABLE_COLUMN_COURSES+" = '"+prev+"/"+course.getName()+"' where "+ USER_TABLES_COLUMN_USERNAME + " = '" + username +"'";
        db.execSQL(query);
        db.close();
        return true;
    }
    //dates should be formatted Day1,Day2/Time1Start:Time1End,Time2Start:Time2End
    public static boolean timeConflict(String date1,String date2){ //absolutely disgusting code pls i want to go to bed
        for(String day1:date1.split("/")[0].split(",")){
            for(String day2:date2.split("/")[0].split(",")){
                if(day1.equals(day2)){
                    for(String time1:date1.split("/")[1].split(",")){
                        for(String time2:date2.split("/")[1].split(",")) {
                            if((Integer.parseInt(time1.split("-")[0].replace(":",""))>=Integer.parseInt(time2.split("-")[0].replace(":",""))&&
                                Integer.parseInt(time1.split("-")[0].replace(":",""))<=Integer.parseInt(time2.split("-")[1].replace(":","")))||
                                    (Integer.parseInt(time1.split("-")[1].replace(":",""))>=Integer.parseInt(time2.split("-")[0].replace(":",""))&&
                                            Integer.parseInt(time1.split("-")[1].replace(":",""))<=Integer.parseInt(time2.split("-")[1].replace(":",""))))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public static boolean validDate(String date){
        try{
            if(date.split("/").length!=2)return false;
            if(!date.split("/")[1].split(",")[0].contains("-")) return false;
            if(!date.split("/")[1].split(",")[0].contains(":")) return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
