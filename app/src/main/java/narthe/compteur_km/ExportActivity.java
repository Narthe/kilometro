package narthe.compteur_km;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.itextpdf.text.DocumentException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import pdf_export.Main;


public class ExportActivity extends ActionBarActivity {

    private Button exportButton;
    static DatabaseHandler db;
    private DatePicker fromDatePicker;
    private DatePicker toDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        db = new DatabaseHandler(this);
        this.initWidgets();
        this.initEvents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_export, menu);
        return true;
    }

    public void initWidgets(){
        this.exportButton = (Button)findViewById(R.id.exportButton);
        this.fromDatePicker = (DatePicker) findViewById(R.id.fromDatePicker);
        this.toDatePicker = (DatePicker) findViewById(R.id.toDatePicker);
    }

    public void initEvents() {
        this.exportButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer fromDay = fromDatePicker.getDayOfMonth();
                        Integer fromMonth = fromDatePicker.getMonth();
                        Integer fromYear = fromDatePicker.getYear();
                        Integer toDay = toDatePicker.getDayOfMonth();
                        Integer toMonth = toDatePicker.getMonth();
                        Integer toYear = toDatePicker.getYear();
                        ArrayList <Course> courses = db.getCoursesByPeriod(fromDay, fromMonth, fromYear, toDay, toMonth, toYear);
                        Integer distance = db.getDistanceOnPeriod(fromDay, fromMonth, fromYear, toDay, toMonth, toYear);
                        FileInputStream inputXSL = (FileInputStream) getResources().openRawResource(R.raw.template);
                        FileInputStream inputCSS = (FileInputStream) getResources().openRawResource(R.raw.style);
                        File pdf = null;
                        try {
                            pdf = Main.getPDF(courses,
                                    String.format("%02d-%02d-%d", fromDay, fromMonth, fromYear),
                                    String.format("%02d-%02d-%d", toDay, toMonth, toYear),
                                    distance,
                                    inputXSL,
                                    inputCSS);
                        } catch (SAXException | TransformerException | IOException | ParserConfigurationException | DocumentException e) {
                            e.printStackTrace();
                        }
                        startActivity(Intent.createChooser(new Intent(createShareIntent(pdf)), getString(R.string.sendvia)));
                    }
                }
        );
    }

    private Intent createShareIntent(File f) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, f);
        return shareIntent;
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
}
