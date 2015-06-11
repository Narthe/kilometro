package narthe.compteur_km;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import pdf_export.Main;


public class ExportActivity extends FragmentActivity {

    private Button exportButton;
    static DatabaseHandler db;
    int fromYear, fromMonth, fromDay,toYear, toMonth, toDay;
    DatePickerDialog.OnDateSetListener fromDateListener,toDateListener;
    static final int DATE_PICKER_TO = 0;
    static final int DATE_PICKER_FROM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        db = new DatabaseHandler(this);
        /* get the current date */
        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH);
        fromDay = c.get(Calendar.DAY_OF_MONTH);
        toYear = c.get(Calendar.YEAR);
        toMonth = c.get(Calendar.MONTH);
        toDay = c.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_export, menu);
        return true;
    }

    public void initWidgets(){
        this.exportButton = (Button)findViewById(R.id.exportButton);
        //this.fromDatePicker = (DatePicker) findViewById(R.id.fromDatePicker);
        //this.toDatePicker = (DatePicker) findViewById(R.id.toDatePicker);
    }

    public void initEvents() {
        fromDateListener = new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            }
        };

        toDateListener = new DatePickerDialog.OnDateSetListener(){
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

            }
        };

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

    public void showFromDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showToDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(int id) {
            // Use the current date as the default date in the picker
            switch(id){
                case ExportActivity.DATE_PICKER_FROM:
                    return new DatePickerDialog(this, from_dateListener, from_year, from_month, from_day);
                case DATE_PICKER_TO:
                    return new DatePickerDialog(this, to_dateListener, to_year, to_month, to_day);
            }
            return null;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

        }
    }
}
