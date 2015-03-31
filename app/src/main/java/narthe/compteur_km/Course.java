package narthe.compteur_km;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Locale;

/**
 * Created by narthe on 11/01/2015.
 */
public class Course {
    private int id;
    private int start;
    private int end;
    private int distance;
    private DateTime date;

    public Course() {}

    public Course(int id, int start, int end, DateTime date) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.distance = end-start;
        this.date = date;
    }

    public Course(int start, int end, DateTime date) {
        this.start = start;
        this.end = end;
        this.distance = end-start;
        this.date = date;
    }

    public int getId() { return id; }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDistance() {
        return distance;
    }

    public DateTime getDate() {
        return date;
    }

    public String getDateToString(){
        String dayOfMonth = Integer.toString(this.date.getDayOfMonth());
        String dayOfWeek = this.date.dayOfWeek().getAsText(Locale.FRANCE);
        String month = this.date.monthOfYear().getAsText(Locale.FRANCE);

        return dayOfWeek + " " + dayOfMonth + " " + month;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Run{" +
                "start=" + start +
                ", end=" + end +
                ", distance=" + distance +
                ", date=" + date +
                '}';
    }
}
