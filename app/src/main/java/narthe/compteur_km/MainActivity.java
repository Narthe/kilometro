package narthe.compteur_km;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "narthe.compteur_km.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);
        //db.dropTable();

        // Setting font for App Logo
        TextView tv=(TextView)findViewById(R.id.app_name_txt_view);
        //Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Mission-Script.otf");
        Typeface logoFace = Typeface.createFromAsset(getAssets(),"fonts/Nautilus.otf");
        tv.setTypeface(logoFace);

        //Setting font-awesome for buttons
        Typeface fontAwesomeFace = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        Button add_button = (Button)findViewById(R.id.add_course_button);
        add_button.setTypeface(fontAwesomeFace);
        Button list_button = (Button)findViewById(R.id.list_courses_button);
        list_button.setTypeface(fontAwesomeFace);
        Button export_button = (Button)findViewById(R.id.export_button);
        export_button.setTypeface(fontAwesomeFace);
        Button stat_button = (Button)findViewById(R.id.stat_button);
        stat_button.setTypeface(fontAwesomeFace);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                System.out.println("Search");
                return true;
            case R.id.action_settings:
                System.out.println("Settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addCourse(View view){
        Intent intent = new Intent(this, AddCourseActivity.class);
        startActivity(intent);
    }

    public void listCourses(View view){
        Intent intent = new Intent(this, CourseListViewActivity.class);
        startActivity(intent);
    }

    public void export(View view){
        Intent intent = new Intent(this, ExportActivity.class);
        startActivity(intent);
    }

    public void showCharts(View view){
        Intent intent = new Intent(this, ChartsActivity.class);
        startActivity(intent);
    }
}
