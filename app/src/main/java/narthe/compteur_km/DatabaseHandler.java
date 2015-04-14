package narthe.compteur_km;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        DateTime date = new DateTime();
        DateTimeFormatter dateDecoder = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dtStr = dateDecoder.print(date);
        
        values.put(KEY_START, course.getStart());
        values.put(KEY_END, course.getEnd());
        values.put(KEY_DISTANCE, course.getDistance());
        values.put(KEY_DATE, dtStr);

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

        DateTimeFormatter dateDecoder = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime courseDate = new DateTime();
        try {
            courseDate = dateDecoder.parseDateTime(cursor.getString(4));
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        cursor.close();
        return new Course(Integer.parseInt(cursor.getString(0)),          //id
                                   Integer.parseInt(cursor.getString(1)), //start
                                   Integer.parseInt(cursor.getString(2)), //end
                                   courseDate);                           //date
    }

    // Getting All courses
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courseList = new ArrayList<Course>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + COURSES_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DateTimeFormatter dateDecoder = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime courseDate = dateDecoder.parseDateTime(cursor.getString(4));
                Course course = new Course(Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), courseDate);
                // Adding contact to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        // return contact list
        return courseList;
    }

    public ArrayList<Course> getCoursesByMonth(Integer month) {
        ArrayList<Course> courseList = new ArrayList<Course>();
        Log.d("month", Integer.toString(month));
        Log.d("month padded", String.format("%02d", month));
        String coursesByMonthQuery = "SELECT  * FROM `" + COURSES_TABLE + "` WHERE strftime('%m', `date`) = '" + String.format("%02d", month) + "'";
        Log.d("query", coursesByMonthQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(coursesByMonthQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DateTimeFormatter dateDecoder = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                DateTime courseDate = dateDecoder.parseDateTime(cursor.getString(4));
                Course course = new Course(Integer.parseInt(cursor.getString(1)),
                                           Integer.parseInt(cursor.getString(2)),
                                           Integer.parseInt(cursor.getString(3)),
                                           courseDate);
                Log.d("Course after query : ", course.toString());
                // Adding contact to list
                courseList.add(course);
            } while (cursor.moveToNext());
        }
        else{
            Log.d("DatabaseHandler", "query returned nothing");
        }
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

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
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

    public void dropTable(){
        String dropQuery = "DROP IF EXISTS " + COURSES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(dropQuery, null);
        cursor.close();
    }
}
