package LAQ5;

import java.util.Comparator;

public class PassengerComparator implements Comparator<Passenger>
{
    // Passengers will be compared by priority, listed in descending order
    @Override
    public int compare(Passenger p1, Passenger p2)
    {
        return Float.compare(p2.getPriority(), p1.getPriority());
    }
}
