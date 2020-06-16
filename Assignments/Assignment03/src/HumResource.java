/**
 * Class HumResource - Object made to represent a row in the HumResource table
 *
 * Created by: Jakob Allen (allenjd4061), 20 November 2019
 */

public class HumResource {
    //variables for all unique columns in table
    private int HRID; //ID of the humanitarian resource
    private String HRName; //Name of the humanitarian resource
    private String HRAddress; //Address of the humanitarian resource
    private String HRPhoneNumber; //Phone number of the humanitarian resource
    private double latitude; //Latitude of the humanitarian resource
    private double longitude; //Longitude of the humanitarian resource
    private String HRType; // Type of humanitarian resource
    private String HRDesc; //Description of the humanitarian resource
    private String HROpenHours; //Operational Hours of the humanitarian resource

    //returns HRID value
    public int getHRID() {
        return HRID;
    }

    //set HRID to parameter value
    public void setHRID(int HRID) {
        this.HRID = HRID;
    }

    //returns HRName string
    public String getHRName() {
        return HRName;
    }

    //set HRName to parameter string
    public void setHRName(String HRName) {
        this.HRName = HRName;
    }

    //returns HRAddress string
    public String getHRAddress() {
        return HRAddress;
    }

    //set HRAddress to parameter string
    public void setHRAddress(String HRAddress) {
        this.HRAddress = HRAddress;
    }

    //returns HRPhoneNumber string
    public String getHRPhoneNumber() {
        return HRPhoneNumber;
    }

    //set HRPhoneNumber to parameter string
    public void setHRPhoneNumber(String HRPhoneNumber) {
        this.HRPhoneNumber = HRPhoneNumber;
    }

    //returns latitude value
    public double getLatitude() {
        return latitude;
    }

    //set latitude to parameter value
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //returns longitude value
    public double getLongitude() {
        return longitude;
    }

    //set longitude to parameter value
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //returns HRType string
    public String getHRType() {
        return HRType;
    }

    //sets HRType to parameter string
    public void setHRType(String HRType) {
        this.HRType = HRType.replace(" ",""); //get rid of space in "Medical Center" value
    }

    //returns HRDesc string
    public String getHRDesc() {
        return HRDesc;
    }

    //set HRDesc to parameter string
    public void setHRDesc(String HRDesc) {
        this.HRDesc = HRDesc;
    }

    //returns HROpenHours string
    public String getHROpenHours() {
        return HROpenHours;
    }

    //set HROpenHours to parameter string
    public void setHROpenHours(String HROpenHours) {
        this.HROpenHours = HROpenHours;
    }
}
