package narthe.compteur_km;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;


public class AddCourseActivity extends Activity {

    Button addButton;
    EditText lastRecordEdit;
    EditText newRecordEdit;
    TextView addCourseTextView;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        db = new DatabaseHandler(this);
        this.initWidgets();
        this.initEvents();
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
        Typeface logoFace = Typeface.createFromAsset(getAssets(),"fonts/Nautilus.otf");
        lastRecordEdit = (EditText)findViewById(R.id.lastRecordEdit);
        newRecordEdit = (EditText)findViewById(R.id.newRecordEdit);
        addCourseTextView = (TextView)findViewById(R.id.addRunLabel);
        addButton = (Button)findViewById(R.id.addRunButton);

        addButton.setTypeface(logoFace);
        lastRecordEdit.setTypeface(logoFace);
        newRecordEdit.setTypeface(logoFace);
        addCourseTextView.setTypeface(logoFace);
        Course course = db.getLastCourse();
        Log.d("last course end value", Integer.toString(course.getEnd()));
        this.lastRecordEdit.setText(Integer.toString(course.getEnd()));
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

                            try {
                                Course course = new Course(start, end, new DateTime());
                                System.out.println(course.toString());
                                AddCourseActivity.db.addCourse(course);
                                SuccessDialogFragment dialog = new SuccessDialogFragment();
                                dialog.show();
                            } catch(Exception e) {
                                System.out.println(e)
                            }

                        }
                        else
                        {
                            BadValuesDialogFragment dialog = new BadValuesDialogFragment();
                            dialog.show();
                            System.out.println("Compteur d'arrivée inférieur au compteur de départ");
                        }
                    }
                }
        );
    }
    
    public class BadValuesDialogFragment extends DialogFragment {
    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.bad_values_alert_dialog_message)
                   .setTitle(R.string.bad_values_alert_dialog_title)
                   .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // Do nothing
                       }
           })
       });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    public class SuccessDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.add_success_alert_dialog_message)
                   .setTitle(R.string.add_success_alert_dialog_title)
                   .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Start CourseListViewActivity
                            Intent intent = new Intent(this, CourseListViewActivity.class);
                            startActivity(intent);
                        }
            })
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
}
