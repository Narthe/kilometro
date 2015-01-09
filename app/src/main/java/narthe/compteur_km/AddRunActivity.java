package narthe.compteur_km;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddRunActivity extends ActionBarActivity {

    Button addButton;
    EditText lastRecordEdit;
    EditText newRecordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_run);

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
                        Log.v("last record : ", AddRunActivity.this.lastRecordEdit.getText().toString());
                        Log.v("new record : ", AddRunActivity.this.newRecordEdit.getText().toString());
                    }
                }
        );
    }
}
