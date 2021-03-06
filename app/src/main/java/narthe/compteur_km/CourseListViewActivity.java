package narthe.compteur_km;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import net.danlew.android.joda.JodaTimeAndroid;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Date;


public class CourseListViewActivity extends Activity {

    ListView listview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_view);
        JodaTimeAndroid.init(this);

        java.util.Date juDate = new Date();
        DateTime dt = new DateTime(juDate);
        Integer month = dt.getMonthOfYear();  // where January is 1 and December is 12
        Integer year = dt.getYear();

        DatabaseHandler db = new DatabaseHandler(this);
        //final ArrayList<Course> list = db.getAllCourses();
        final ArrayList<Course> courseList = db.getCoursesByMonth(month, year);
        if (!courseList.isEmpty())
        {
            this.listview = (ListView) findViewById(R.id.listview);
            Resources res = getResources();
            final ListViewAdapter adapter = new ListViewAdapter(this, courseList, res);
            listview.setAdapter(adapter);
            registerForContextMenu(this.listview);
        }
        else{
            Log.d("Courses list", "courses list is empty");
        }
    }

    Override
    public boolean onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_context_menu, menu);
    }
    
    Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.delete_id:
                this.listview.remove(info.position);
                adapter.notifyDataSetChanged();
                
                // Delete course from databse here
                // db.deleteCourse(course);
                
                return true;
            case R.id.modify_id:
                // Start edit activity
                Intent i = new Intent(getApplicationContext(), EditCourseActivity.class);
                i.putExtra("course_to_edit", course);
                startActivity(i);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    //     // Inflate the menu; this adds items to the action bar if it is present.
    //     getMenuInflater().inflate(R.menu.menu_course_list_view, menu);
    //     return true;
    // }

    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
    //     // Handle action bar item clicks here. The action bar will
    //     // automatically handle clicks on the Home/Up button, so long
    //     // as you specify a parent activity in AndroidManifest.xml.
    //     int id = item.getItemId();

    //     //noinspection SimplifiableIfStatement
    //     if (id == R.id.action_settings) {
    //         return true;
    //     }

    //     return super.onOptionsItemSelected(item);
    // }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
