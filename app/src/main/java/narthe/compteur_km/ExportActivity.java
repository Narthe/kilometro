package narthe.compteur_km;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import narthe.compteur_km.R;
import pdf_export.Main;

public class ExportActivity extends Activity {

    static DatabaseHandler db;

    private TextView tvDisplayDate, tvDisplayDate2;
    private DatePicker dpResult;

    private Button btnChangeDate, btnChangeDate2, exportButton;

    private int fromYear, fromMonth, fromDay, toYear, toMonth, toDay;
    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int cur = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        this.initWidgets();
        this.initEvents();
        addListenerOnButton();

    }

    public void setFromDate(){

    }

    public void addListenerOnButton() {

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }

        });
        btnChangeDate2 = (Button) findViewById(R.id.btnChangeDate2);

        btnChangeDate2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID2);
            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, fromYear, fromMonth, fromDay);
            case DATE_DIALOG_ID2:
                cur = DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, toYear, toMonth, toDay);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            Integer year = selectedYear;
            Integer month = selectedMonth;
            Integer day = selectedDay;

            if(cur == DATE_DIALOG_ID){
                // set selected date into textview
                tvDisplayDate.setText("From : " + new StringBuilder().append(day).append("/").append(String.format("%02d", month+1)).append("/").append(year).append(" "));
                fromDay = day;
                fromMonth = month + 1;
                fromYear = year;
            } else {
                tvDisplayDate2.setText("To : " + new StringBuilder().append(day).append("/").append(String.format("%02d", month+1)).append("/").append(year).append(" "));
                toDay = day;
                toMonth = month + 1;
                toYear = year;
            }
            removeDialog(cur);
        }
    };
    public void initWidgets() {
        //Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Mission-Script.otf");
        Typeface logoFace = Typeface.createFromAsset(getAssets(),"fonts/Nautilus.otf");

        exportButton = (Button) findViewById(R.id.exportButton);
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate2 = (Button) findViewById(R.id.btnChangeDate2);
        exportButton.setTypeface(logoFace);
        btnChangeDate.setTypeface(logoFace);
        btnChangeDate2.setTypeface(logoFace);

        tvDisplayDate = (TextView) findViewById(R.id.tvDate);
        tvDisplayDate2 = (TextView) findViewById(R.id.tvDate2);

        tvDisplayDate.setTypeface(logoFace);
        tvDisplayDate2.setTypeface(logoFace);

        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH);
        fromDay = c.get(Calendar.DAY_OF_MONTH);

        toYear = fromYear;
        toMonth = fromMonth;
        toDay = fromDay;

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(fromDay).append("/").append(String.format("%02d", fromMonth+1)).append("/")
                .append(fromYear).append(" "));

        tvDisplayDate2.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(toDay).append("/").append(String.format("%02d", toMonth+1)).append("/")
                .append(toYear).append(" "));
    }
    public void initEvents() {
        this.exportButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Course> courses = db.getCoursesByPeriod(fromDay, fromMonth, fromYear, toDay, toMonth, toYear);
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
}