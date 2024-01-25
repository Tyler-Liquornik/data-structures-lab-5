package LAQ5;

import java.util.LinkedList;

public class Airplane
{
    // Airplane fields
    private int numRows; // the number of rows (displayed as `columns` in side view) (min 2, max 99)
    private int rowWidth; // the number of seats on each side of the aisle (max 5)
    private Seat[][] seats; // Matrix tracks seats in the plane, regular arrays since this is fixed size once set
    private int capacity; // Maximum capacity of the plane
    private int seatsFull; // Number of seats full
    private float percentFull; // Percent of seats full

    private String drawing; // Side view drawing of the plane (for UX purposes), only present for >= 16 rows

    private final String DEFAULT_DRAWING = """
                                
                             ___________
                                _\\\\ _~-\\\\___
                        =  = ==(__________D
                                    \\\\_____\\\\____________________┈--~~~~~~~`-.._
                                    /     o o o o o o o o o o o,o o o o o  |\\\\_
                                    `~-.__        ___..----..  '               )
                                          `---~~\\\\___________/--╶---------`````
                                             =  ===(_________D""\";                       
                        """;

    // Seats within rows use letters
    public final String ALPHABET = "ABCDEFGHIJ";

    // Default Constructor
    public Airplane()
    {
        // Number of plane seats in the base sized airplane
        this.numRows = 16;
        this.rowWidth = 2;
        this.capacity = 64;
        this.percentFull = 0.0f;
        this.seatsFull = 0;
        this.drawing = DEFAULT_DRAWING;

        // Build the empty seat matrix
        this.seats = new Seat[numRows][rowWidth * 2];
        buildSeatMatrix();
    }

    // Custom Constructor
    public Airplane(int numRows, int rowWidth)
    {
        this.numRows = numRows;
        this.rowWidth = rowWidth;
        this.capacity = numRows * rowWidth * 2;
        this.seatsFull = 0;
        this.percentFull = 0.0f;

        // Build the empty seat matrix
        this.seats = new Seat[numRows][rowWidth * 2];
        buildSeatMatrix();

        // Build the drawing
        this.drawing = DEFAULT_DRAWING;;
        buildDrawing();
    }

    // Build the empty seat matrix for the given specified plane size
    public void buildSeatMatrix()
    {
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < rowWidth * 2; j++)
            {
                seats[i][j] = new Seat(null, i + 1, ALPHABET.charAt(j));
            }
        }
    }

    // Build the custom sized drawing from the base drawing (16 rows / windows)
    public void buildDrawing()
    {
        // StringBuilder used to insert characters to extend the plane
        StringBuilder newDrawing = new StringBuilder(drawing);

        // Extensions add one new window, loop for the amount of times needed to grow past default numRows = 16;
        for (int i = 0; i < numRows - 16; i++)
        {
            // Detect special characters to add length
            newDrawing.insert(newDrawing.indexOf("┈") + 3, "-~");
            newDrawing.insert(newDrawing.indexOf(",") + 5, "o ");
            newDrawing.insert(newDrawing.indexOf("'") + 5, "  ");
            newDrawing.insert(newDrawing.indexOf("╶") + 9, "~-");
        }

        // Update the drawing
        this.drawing = String.valueOf(newDrawing);
    }

    // Board the plane from a passenger list. Return the list of passengers who couldn't board (empty if below capacity)
    // Rationale for choosing a linked list in main
    public LinkedList<Passenger> board(LinkedList<Passenger> l)
    {
        // Who has boarded already determines how many people we can board
        int numBoarded = seatsFull;
        int numBoarding = Math.min(l.size(), capacity - numBoarded);

        // Loop through the people we need to board
        for (int i = 0; i < numBoarding; i++)
        {
            // If There are seats open
            if (seatsFull < capacity)
            {
                // Find their seat
                int row = seatsFull / (rowWidth * 2);
                int seatIndex = seatsFull % (rowWidth * 2);

                // Assign them to their seat
                seats[row][seatIndex].setPassenger(l.get(i));
                seatsFull++;
            }
        }

        // Update how full the list is
        percentFull = (float) seatsFull / capacity;

        // Remove successfully boarded passengers from the original list
        l.subList(0, numBoarding).clear();

        // Return the new waiting list
        return l;
    }

    // Deboard the plane, returning deboarded passengers into a linked list for optional use
    public LinkedList<Passenger> deboard()
    {
        // List to add deboarded passengers
        LinkedList<Passenger> deboardedPassengers = new LinkedList<>();

        // Loop through the seat matrix
        for (int i = 0; i < numRows; i++)
        {
            for (int j = 0; j < rowWidth * 2; j++)
            {
                Seat seat = seats[i][j];
                if (seat.getPassenger() != null)
                {
                    // Add the passenger to the deboarded list and clear the seat
                    deboardedPassengers.add(seat.getPassenger());
                    seat.setPassenger(null);
                    seatsFull--;
                }
            }
        }

        // Update the percentFull to reflect deboarding
        percentFull = (float) seatsFull / capacity;

        return deboardedPassengers;
    }

    // toString method will print the current state of the plane
    public String toString()
    {
        // StringBuilder allows easy concatenation through .append()
        StringBuilder str = new StringBuilder();

        // Loop through to append seats into matrix, ensuring appropriate spacing
        // Left of the aisle (displayed as above)
        for (int i = 0; i < rowWidth; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                str.append(seats[j][i]).append(" ");
            }

            str.append("\n");
        }

        str.append("\n"); // Aisle Gap

        // Right of the aisle (displayed as below)
        for (int i = rowWidth; i < rowWidth * 2; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                str.append(seats[j][i]).append(" ");
            }

            str.append("\n");
        }

        return str.toString();
    }

    // Accessors
    public float getPercentFull() {return percentFull;}
    public int getCapacity() {return capacity;}
    public int getSeatsFull() {return seatsFull;}
    public int getNumRows() {return numRows;}
    public String getDrawing() {return drawing;}

    /*
       No mutators as changing the size of the plane one initializing causes obvious problems
       in the case where we've already boarded some passengers
    */
}
