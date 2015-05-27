package narthe.compteur_km;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class ChartsActivity extends ActionBarActivity {

    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        db = new DatabaseHandler(this);
        this.initWidgets();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_charts, menu);
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
        BarChart mChart = (BarChart) findViewById(R.id.chart);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

        mChart.getAxisLeft().setDrawGridLines(false);

        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 20; i++) {
            float val1 = (float) (Math.random() * i) + i / 3;
            yVals1.add(new BarEntry((int) val1, i));
        }
        /** adapted for Courses :
         * Integer i = 0;
         * foreach course in Courses{
         *  yVals.add(new BarEntry((int) distance, i));
         *  xVals.add(month)
         *  i++;
         * }
         */

/*        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            xVals.add((int) yVals1.get(i).getVal() + "");
        }*/

        BarDataSet set1 = new BarDataSet(yVals1, "Data set");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("janvier"); xVals.add("fevrier"); xVals.add("mars"); xVals.add("avril");

        BarData data = new BarData(xVals, dataSets);
        mChart.setData(data);
        // add a nice and smooth animation
        mChart.animateY(2500);
        mChart.invalidate(); // refresh
    }
}
