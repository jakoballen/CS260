/**
 * Class MedicalCenter - Object made to represent a row in the MedicalCenter table
 *
 * Created by: Jakob Allen (allenjd4061), 20 November 2019
 */

public class MedicalCenter extends HumResource {
    //variables for all unique columns in table
    private int numBeds; //Number of beds available in the medical center
    private int emergencyRoomCap; //Emergency room capacity of the medical center
    private int numDoctors; //Number of doctors in the medical center
    private int numNurses; //Number of nurses in the medical center

    //returns numBeds value
    public int getNumBeds() {
        return numBeds;
    }

    //sets numBeds to parameter value
    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    //returns emergencyRoomCap value
    public int getEmergencyRoomCap() {
        return emergencyRoomCap;
    }

    //sets emergencyRoomCap to parameter value
    public void setEmergencyRoomCap(int emergencyRoomCap) {
        this.emergencyRoomCap = emergencyRoomCap;
    }

    //returns numDoctors value
    public int getNumDoctors() {
        return numDoctors;
    }

    //sets numDoctors to parameter value
    public void setNumDoctors(int numDoctors) {
        this.numDoctors = numDoctors;
    }

    //returns numNurses value
    public int getNumNurses() {
        return numNurses;
    }

    //sets numNurses to parameter value
    public void setNumNurses(int numNurses) {
        this.numNurses = numNurses;
    }
}
