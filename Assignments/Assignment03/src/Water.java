/**
 * Class Water - Object made to represent a row in the Water table
 *
 * Created by: Jakob Allen (allenjd4061), 20 November 2019
 */

public class Water extends HumResource{
    //variables for all unique columns in table
    private int num10ozBottle;
    private int numHalfLiterBottle;
    private int num5GalJug;

    //returns num10OzBottle value
    public int getNum10ozBottle() {
        return num10ozBottle;
    }

    //sets num10OzBottle to parameter value
    public void setNum10ozBottle(int num10ozBottle) {
        this.num10ozBottle = num10ozBottle;
    }

    //returns numHalfLiterBottle value
    public int getNumHalfLiterBottle() {
        return numHalfLiterBottle;
    }

    //sets numHalfLiterBottle to parameter value
    public void setNumHalfLiterBottle(int numHalfLiterBottle) {
        this.numHalfLiterBottle = numHalfLiterBottle;
    }

    //returns num5GalJug value
    public int getNum5GalJug() {
        return num5GalJug;
    }

    //sets num5GalJug to parameter value
    public void setNum5GalJug(int num5GalJug) {
        this.num5GalJug = num5GalJug;
    }
}
