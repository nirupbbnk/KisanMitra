package pathrer.com.kisanmitra;

/**
 * Created by Pathrer on 24-01-2017.
 */
public class Blog {

    private String title;
    private String desc;
    private String image;
    private String phno;
    private String place;
    private String usrid;

    public String getNpkval() {
        return npkval;
    }

    public void setNpkval(String npkval) {
        this.npkval = npkval;
    }

    private String npkval;

    public Blog(){

    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Blog(String title, String desc, String image, String phno, String place,String usrid ,String npkval) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.phno = phno;
        this.place = place;
        this.usrid = usrid;
        this.npkval = npkval;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
