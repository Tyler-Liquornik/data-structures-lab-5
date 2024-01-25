package LAQ5;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Main
{
    // Scanner for user input
    public static Scanner input = new Scanner(System.in);

    // Header Method
    public static void myHeader(int labE_number, int q_number)
    {
        System.out.println("=======================================================");
        System.out.printf("Lab Exercise: %d-Q%d%n", labE_number, q_number);
        System.out.println("Prepared by: Tyler Liquornik");
        System.out.println("Student Number: 251271244");
        System.out.printf("Goal of this exercise: %s%n", "Boarding Plane Passengers");
        System.out.println("=======================================================");
    }

    // Footer Method
    public static void myFooter(int labE_number, int q_number)
    {
        System.out.println("=======================================================");
        System.out.printf("Completion of Lab Exercise %d-Q%d is successful!%n", labE_number, q_number);
        System.out.println("Signing off - Tyler");
        System.out.println("=======================================================");
    }

    public static void main(String[] args)
    {
        // Print the header
        myHeader(5, 1);

        // Control looping
        boolean continueMainLoop;

        // Main Loop
        do
        {
            System.out.println("\nAye aye captain! Tell me about your plane!");

            // Global variables initially start at default values for row and rowWidth
            // Done this way to avoid using mutators in the airplane class
            int r = 16;
            int rW = 2;

            // Control looping
            boolean continueInnerLoop = true;

            // Plane Creation Loop
            do
            {
                System.out.println("\nHow many rows does your plane have?");
                System.out.print("Rows: ");
                String numRows = input.nextLine();

                System.out.println("\nHow many seats are in each row on either side of the aisle?");
                System.out.print("Row Half-Width: ");
                String rowWidth = input.nextLine();

                // Try to create the plane with the given inputs, keep looping for invalid inputs
                try
                {
                    // Local variables to test
                    int rTest = Integer.parseInt(numRows);
                    int rWTest = Integer.parseInt(rowWidth);

                    if (rTest < 2 || rTest > 99 || rWTest < 1 || rWTest > 5) // Invalid number check
                    {
                        throw new RuntimeException();
                    }
                    else
                    {
                        // Assign local variables confirmed to be allowed inputs to global variables
                        r = rTest;
                        rW = rWTest;

                        // Valid inputs mean the loop can end
                        continueInnerLoop = false;

                        System.out.println("\nThe plane is ready for passengers!");
                    }
                }
                catch (RuntimeException e) // The user inputted an integer out of the valid range, or a non-integer
                {
                    System.out.println("\nEnsure you enter a valid number " +
                            "of rows (2-99) and row width (1-5), both ranges inclusive");
                }
            }
            while (continueInnerLoop);

            // Create the airplane object exit this section
            Airplane airplane = new Airplane(r, rW);

            // Print the planes drawing if it is sufficiently big to have a drawing (the min drawing is 16 rows / windows)
            if (airplane.getNumRows() >= 16)
            {
                System.out.println("That's a big plane you have there!");
                System.out.println(airplane.getDrawing());
            }

            // Reset this flag
            continueInnerLoop = true;

            /*
            The rationale behind choosing a linked list is for passengers waiting has two reasons.

            Firstly, we need a dynamic structure because we are constantly adding passengers to the list of passengers
            waiting to board. The true dynamic nature of a linked list makes it the most efficient for this.

            Second is we want to maintain a specific order, which is easiest to do with a linked list since the linked nature
            of the list means we can only access passengers in the order they are on the list, making boarding easier

             */
            LinkedList<Passenger> l = new LinkedList<>();

            // Boarding Loop
            do
            {
                // Give the menu of options
                System.out.println("""
                        \nBoarding Menu:
                        \t1: Someone New is Waiting to Board
                        \t2: Board Waiting Passengers
                        \t3: See Who's Waiting to Board
                        \t4: See Who's on the Plane
                        \t5: Deboard the Plane
                        \t6: Take Off!
                        """);
                System.out.print("Enter your choice: ");
                String option = input.nextLine();

                // Add a passenger to the list of those waiting
                if (Objects.equals(option, "1"))
                {
                    // Control Loop
                    boolean continueInnermostLoop = true;

                    do
                    {
                        Passenger psngr = new Passenger(); // Build passengers from default

                        System.out.print("\nEnter Their First Name: ");
                        String fName = input.nextLine();

                        System.out.print("Enter Their Last Name: ");
                        String lName = input.nextLine();

                        System.out.print("Enter Boarding Priority: ");
                        String priority = input.nextLine();

                        try
                        {
                            float f = Float.parseFloat(priority);

                            if (f < 0.0f || f > 100.0f) // Invalid priority check
                            {
                                throw new RuntimeException();
                            }
                            else
                            {
                                // Customize the passenger and add it to the list, auto-capitalizing names
                                psngr.setFName(fName.replace(fName.charAt(0), fName.toUpperCase().charAt(0)));
                                psngr.setLName(lName.replace(lName.charAt(0), lName.toUpperCase().charAt(0)));
                                psngr.setPriority(f);
                                l.add(psngr);
                                continueInnermostLoop = false;
                            }
                        }
                        catch (RuntimeException e)
                        {
                            System.out.println("\nEnsure that priority is a decimal number from 0 to 100 inclusive");
                            System.out.println("This person is invalid and is thus discarded");
                        }
                    }
                    while (continueInnermostLoop);
                }

                // Board the passengers waiting right now
                else if (Objects.equals(option, "2"))
                {
                    // Board only if there are people to board
                    if (!l.isEmpty())
                    {
                        // Sort the passenger list, so they can be boarded in the correct order
                        l.sort(new PassengerComparator());

                        // Board the plane, assigning the list of stuck people to the list still waiting
                        l = airplane.board(l);

                        // Display the boarded plane (seat matrix)
                        System.out.println("\nHere is the boarded plane, where highest priority " +
                                "passengers boarded first, closest to the front (left side)");
                        System.out.println("\n" + airplane);

                        // Extra information for user
                        System.out.printf("The airplane has %d of %d passengers boarded, " +
                                        "meaning the plane is %.2f%% full%n", airplane.getSeatsFull(),
                                airplane.getCapacity(), airplane.getPercentFull() * 100);

                        // Passengers who couldn't board (only print if there are any)
                        if (!l.isEmpty())
                        {
                            System.out.println("\nThe following passengers are stuck, " +
                                    "because the plane had no room left: " + Arrays.toString(l.toArray()));
                        }
                    }

                    else {System.out.println("There's nobody to board!");}
                }

                // Get the list of people waiting to board
                else if (Objects.equals(option, "3"))
                {
                    // Print the passengers current waiting if there are any
                    if (!l.isEmpty())
                    {
                        System.out.println("\nThe following passengers are waiting (in order) to board");
                        l.sort(new PassengerComparator()); // Sort the list to get the proper order in terms of priority
                        System.out.println(Arrays.toString(l.toArray()));
                    }

                    else {System.out.println("There are no waiting passengers!");}
                }

                // Print the current state of the plane
                else if (Objects.equals(option, "4"))
                {
                    // If the plane isn't empty
                    if (airplane.getSeatsFull() != 0)
                    {
                        System.out.println("\nHere is the current state of the plane:");
                        System.out.println(airplane); // Call the toString method of the Airplane class
                    }

                    else
                        System.out.println("The plane is empty!");
                }

                // Deboarding Option
                else if (Objects.equals(option, "5"))
                {
                    // Display the deboarded passengers (if there are any)
                    if (airplane.getSeatsFull() != 0)
                    {
                        LinkedList<Passenger> deboardedPassengers = airplane.deboard();
                        System.out.println("Passengers deboarded: " + Arrays.toString(deboardedPassengers.toArray()));
                    }

                    else
                        System.out.println("The plane is empty, nobody to deboard!");
                }

                // Take off (exit this menu)
                else if (Objects.equals(option, "6"))
                {
                    System.out.println("\n----------\nBlast off!\n----------");
                    continueInnerLoop = false; // stop the boarding loop
                }

                // User didn't enter a number from 1-6 representing one of the options
                else {System.out.println("Invalid Input, Try Again!");}
            }
            while (continueInnerLoop);

            // Ask the user if they want to start over, or exit the program
            System.out.println("\nDo you want to start over, or exit the program?");
            System.out.print("Enter \"Y\" to start over, or anything else to exit: ");
            continueMainLoop = input.next().equals("Y");
            input.nextLine(); // clear the buffer for if they decide to continue

        }
        while (continueMainLoop);

        // Print the footer
        myFooter(5, 1);
    }
}