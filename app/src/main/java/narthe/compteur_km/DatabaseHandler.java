package narthe.compteur_km;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by narthe on 11/01/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cptkm";

    // Courses table name
    private static final String COURSES_TABLE = "courses";

    // Courses Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + COURSES_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_START + " INTEGER,"
                + KEY_END + " INTEGER,"
                + KEY_DISTANCE + " INTEGER,"
                + KEY_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);

        // Create tables again
        onCreate(db);
    }

    // Adding new course
    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateToString = df.format(course.getDate());
        
        values.put(KEY_START, course.getStart());
        values.put(KEY_END, course.getEnd());
        values.put(KEY_DISTANCE, course.getDistance());
        values.put(KEY_DATE, dateToString);

        // Inserting Row
        db.insert(COURSES_TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single course
    public Course getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(COURSES_TABLE, new String[] { KEY_ID,
                        KEY_START, KEY_END, KEY_DISTANCE, KEY_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String date_str = cursor.getString(4);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        try
        {
            date = sdf.parse(date_str);
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }

        Course course = new Course(Integer.parseInt(cursor.getString(0)), //id
                                   Integer.parseInt(cursor.getString(1)), //start
                                   Integer.parseInt(cursor.getString(2)), //end
                                   Integer.parseInt(cursor.getString(3)), //distance
                                   date);                                 //date
        // return contact
        return course;
    }

    // Getting All courses
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<Course>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + COURSES_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String date_str = cursor.getString(3);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                try
                {
                    date = sdf.parse(date_str);
                }
                catch (ParseException ex){
                    ex.printStackTrace();
                }
                Course course = new Course(Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), date);
                // Adding contact to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        // return contact list
        return courseList;
    }

    // Getting courses Count
    public int getCoursesCount() {
        String countQuery = "SELECT  * FROM " + COURSES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    // Updating single course
    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateToString = df.format(course.getDate());

        ContentValues values = new ContentValues();
        values.put(KEY_START, course.getStart());
        values.put(KEY_END, course.getEnd());
        values.put(KEY_DISTANCE, course.getDistance());
        values.put(KEY_DATE, dateToString);

        // updating row
        return db.update(COURSES_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(course.getId()) });
    }

    // Deleting single course
    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSES_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(course.getId()) });
        db.close();
    }
}
