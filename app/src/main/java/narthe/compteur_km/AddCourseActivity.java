package narthe.compteur_km;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;


public class AddCourseActivity extends ActionBarActivity {

    Button addButton;
    EditText lastRecordEdit;
    EditText newRecordEdit;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        this.initWidgets();
        this.initEvents();
        db = new DatabaseHandler(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_run, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initWidgets(){
        this.lastRecordEdit = (EditText)findViewById(R.id.lastRecordEdit);
        this.newRecordEdit = (EditText)findViewById(R.id.newRecordEdit);
        this.addButton = (Button)findViewById(R.id.addRunButton);
    }

    public void initEvents(){
        this.addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String start_str = AddCourseActivity.this.lastRecordEdit.getText().toString();
                        String end_str = AddCourseActivity.this.newRecordEdit.getText().toString();
                        Integer start = Integer.parseInt(start_str);
                        Integer end = Integer.parseInt(end_str);
                        if(end > start) {
                            Log.v("last record : ", start_str);
                            Log.v("new record : ", end_str);

                            Course course = new Course(start, end, new DateTime());
                            System.out.println(course.toString());
                            AddCourseActivity.db.addCourse(course);

                            //List<Course> courses = db.getAllCourses();

                            //for (Course c : courses) {
                                //System.out.println(cn.toString());
                            //}
                        }
                        else
                        {
                            System.out.println("Compteur d'arrivée inférieur au compteur de départ");
                        }
                    }
                }
        );
    }
}
