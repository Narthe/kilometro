package narthe.compteur_km;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    }

    // Getting single course
    public Course getCourse(int id) {

    }

    // Getting All courses
    public List<Course> getAllCourses() {

    }

    // Getting courses Count
    public int getCoursesCount() {

    }
    // Updating single course
    public int updateCourse(Course course) {

    }

    // Deleting single course
    public void deleteCourse(Course course) {

    }
}
