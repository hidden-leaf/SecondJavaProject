
/**
 * Makes a client object containing first name, last name and a list of events.
 * 
 * @author Peter Triggs 
 * @version 1.0
 */

public class Client implements Comparable<Client> 
{
    // instance variables 
    private String firstName;
    private String lastName;
    private SortedArrayList<Event> clientEvents;

    /**
     * Constructor for objects of class Client
     */
    public Client(String firstName, String lastName)
    {
        // initialise instance variables
        this.firstName = firstName;
        this.lastName = lastName;
        clientEvents = new SortedArrayList<Event>();
    }
    
    /**
     * Getter for client events sortedarraylist
     */
    public SortedArrayList<Event> getClientEvents()
    {
        return clientEvents;
    }
    
    /**
     * Add to the clients list of events
     */
    public void addClientEvent(Event newEvent)
    {
        clientEvents.add(newEvent);
    }
    
    /**
     * Getter for first name
     * 
     * @return     first name 
     */
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * Getter for last name
     * 
     * @return      last name
     */
    public String getLastName()
    {
        return lastName;
    }
    
    /**
     * toString method
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Client: " + firstName + " " + lastName + "\n");
        for(Event e : clientEvents){
            sb.append(e.toString());
        }
        String text = sb.toString();
        return text;      
    }
    
    public int compareTo(Client c)
    {
        int lcomp = lastName.compareTo(c.lastName);
        if (lcomp != 0) return lcomp;
        int fcomp = firstName.compareTo(c.firstName);
        return fcomp;
    }
}
