package LAQ5;

public class Passenger
{
    // Passenger Fields represent their person info and seat info
    private String fName; // first name
    private String lName; // last name
    private float priority; // float from 0.0f to 100.0f, low to high priority for boarding

    // Default Passenger Constructor (me)
    public Passenger()
    {
        this.fName = "Tyler";
        this.lName = "Liquornik";
        this.priority = 0.0f;
    }

    // Custom Passenger constructor
    public Passenger(String fName, String lName, float priority)
    {
        this.fName = fName;
        this.lName = lName;
        this.priority = priority;
    }

    // Accessors
    public String getFName() {return fName;}
    public String getLName() {return lName;}
    public float getPriority() {return priority;}

    // Mutators
    public void setFName(String fName) {this.fName = fName;}
    public void setLName(String lName) {this.lName = lName;}
    public void setPriority(float priority) {this.priority = priority;}

    // Passenger String is just their name
    @Override
    public String toString() {return fName + " " + lName;}
}
