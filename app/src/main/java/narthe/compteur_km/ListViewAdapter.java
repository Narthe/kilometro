package narthe.compteur_km;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by narthe on 31/03/2015.
 */

public class ListViewAdapter extends BaseAdapter{

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Course tempCourse = null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public ListViewAdapter(Activity a, ArrayList d, Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView date;
        public TextView distance;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.date = (TextView) vi.findViewById(R.id.course_date);
            holder.distance =(TextView)vi.findViewById(R.id.distance);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.date.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempCourse =null;
            tempCourse = ( Course ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.date.setText(tempCourse.getDateToString());
            holder.distance.setText(Integer.toString(tempCourse.getDistance()) + " km");

            /******** Set Item Click Listener for LayoutInflater for each row *******/

            //vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

}