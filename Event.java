
/**
 * Makes an event object containing the type of event and the number of tickets.
 * 
 * @author Peter Triggs
 * @version 1.0
 */
public class Event implements Comparable<Event>
{
    // instance variables - replace the example below with your own
    private String eventName;
    private int numTickets;

    /**
     * Constructor for objects of class Event
     */
    public Event(String eventName, int numTickets)
    {
        // initialise instance variables
        this.eventName = eventName;
        this.numTickets = numTickets;
    }

    /**
     * Getter for event name
     * 
     * @return     eventName
     */
    public String getEventName()
    {
        return eventName;
    }
    
    /**
     * Getter for number of tickets
     * @return      numTickets
     */
    public int getNumTickets()
    {
        return numTickets;
    }
    
    /**
     * Setter for number of tickets
     * 
     * @param       new number of tickets
     */
    public void setNumTickets(int newNumTickets)
    {
        numTickets = newNumTickets;
    }
    
    /**
     * toString method
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Event: " + eventName+ "\n");
        sb.append("No.of tickets: " + numTickets+ "\n");
        sb.append("---------------------------------------------------------------------\n");
        String text = sb.toString();
        return text;
    }
    
     public int compareTo(Event e)
    {
        return eventName.compareTo(e.eventName);
    }

}
