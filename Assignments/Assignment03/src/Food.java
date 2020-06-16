/**
 * Class Food - Object made to represent a row in the Food table
 *
 * Created by: Jakob Allen (allenjd4061), 20 November 2019
 */

public class Food extends HumResource {
    //variables for all unique columns in table
    private String fType; //Type of food available
    private int fMealsAvail; //Number of meals available
    private String fSpecificDesc; //Specific description of the food

    //returns fType string
    public String getfType() {
        return fType;
    }

    //sets fType to parameter string
    public void setfType(String fType) {
        this.fType = fType;
    }

    //returns fMealsAvail value
    public int getfMealsAvail() {
        return fMealsAvail;
    }

    //sets fMealsAvail to parameter value
    public void setfMealsAvail(int fMealsAvail) {
        this.fMealsAvail = fMealsAvail;
    }

    //returns fSpecificDesc string
    public String getfSpecificDesc() {
        return fSpecificDesc;
    }

    //sets fSpecificDesc to parameter string
    public void setfSpecificDesc(String fSpecificDesc) {
        this.fSpecificDesc = fSpecificDesc;
    }
}
