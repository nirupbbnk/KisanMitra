package pathrer.com.kisanmitra;

/**
 * Created by Pathrer on 25-01-2017.
 */
public class Crop {
   private String cropname,price,place,imageurl,phno,cuid;

    public Crop() {
    }

    public Crop(String cropname, String price, String place, String imageurl, String phno) {
        this.cropname = cropname;
        this.price = price;
        this.place = place;
        this.imageurl = imageurl;
        this.phno = phno;
    }

    public String getCropname() {
        return cropname;
    }

    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }
}
