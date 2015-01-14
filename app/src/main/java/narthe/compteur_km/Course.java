package narthe.compteur_km;

import java.util.Date;

/**
 * Created by narthe on 11/01/2015.
 */
public class Course {
    private int id;
    private int start;
    private int end;
    private int distance;
    private Date date;

    public Course() {}

    public Course(int id, int start, int end, Date date) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.distance = end-start;
        this.date = date;
    }

    public Course(int start, int end, Date date) {
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

    public Date getDate() {
        return date;
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

    public void setDate(Date date) {
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
