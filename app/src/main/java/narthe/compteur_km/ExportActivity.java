package narthe.compteur_km;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

    Button exportButton;
    static DatabaseHandler db;

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
    }

    public void initEvents() {
        this.exportButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList <Course> courses = db.getCoursesByPeriod(1, 5, 2015, 31, 5, 2015);
                        Integer distance = db.getDistanceOnPeriod(1, 5, 2015, 31, 5, 2015);
                        FileInputStream inputXSL = (FileInputStream) getResources().openRawResource(R.raw.template);
                        FileInputStream inputCSS = (FileInputStream) getResources().openRawResource(R.raw.style);
                        File pdf = null;
                        try {
                            pdf = Main.getPDF(courses,
                                    "01/05/2015",
                                    "31/05/2015",
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
