package LAQ5;

public class Seat
{
    // Seat Fields
    private Passenger passenger; // the person sitting in this seat
    private int rowNum; // must be within the capacity of the current plane (implement in main)
    private char rowLetter; // each row has multiple seats

    // Custom Seat Constructor
    public Seat(Passenger passenger, int rowNum, char rowLetter)
    {
        this.passenger = passenger;
        this.rowNum = rowNum;
        this.rowLetter = rowLetter;
    }

    // Accessors
    public Passenger getPassenger() {return passenger;}

    // Mutators
    public void setPassenger(Passenger passenger) {this.passenger = passenger;}

    // Print the seat in brackets in the format [##X: XX], (ie. seat: initials)
    // Initials ** represent an empty seat
    @Override
    public String toString()
    {
        // Getting the passengers initials while checking if the seat is empty
        char fInitial = passenger == null ? '*' : passenger.getFName().toUpperCase().charAt(0);
        char lInitial = passenger == null ? '*' : passenger.getLName().toUpperCase().charAt(0);

        // Formatted Seat
        return String.format("[%02d%s: %s%s]", rowNum, rowLetter, fInitial, lInitial);
    }
}
