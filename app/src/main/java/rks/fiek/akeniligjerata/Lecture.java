package rks.fiek.akeniligjerata;

/**
 * Created by GentR on 05-Oct-16.
 */

public class Lecture {
    private int id;
    private String day;
    private String classnumber;
    private String classname;
    private String starttime;
    private String endtime;

    public Lecture() {
    }

    public Lecture(String day, String classnumber, String classname, String starttime, String endtime) {
        this.day = day;
        this.classnumber = classnumber;
        this.classname = classname;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Lecture(int id, String day, String classnumber, String classname, String starttime, String endtime) {
        this.id = id;
        this.day = day;
        this.classnumber = classnumber;
        this.classname = classname;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClassnumber() {
        return classnumber;
    }

    public void setClassnumber(String classnumber) {
        this.classnumber = classnumber;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }


}
