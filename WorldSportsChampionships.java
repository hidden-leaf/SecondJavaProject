
/**
 * Driver class for the program
 * 
 * @author Peter Triggs
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class WorldSportsChampionships 
{
    // instance variables 
    private SortedArrayList<Client> clients;
    private SortedArrayList<Event> events;
    private Scanner reader;
    
    /**
     * Constructor for objects of class WorldSportsChampionships
     */
    public WorldSportsChampionships()
    {
        // initialise instance variables
        reader = new Scanner(System.in);
        clients = new SortedArrayList<Client>();
        events = new SortedArrayList<Event>();
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        WorldSportsChampionships myWSC = new WorldSportsChampionships();
        myWSC.readData();
        myWSC.getUserInput();    
    }
    
    /**
     * Take info from a text file, creates corresponding objects and places them in the correct sortedarraylist 
     */
    public void readData() throws FileNotFoundException
    {
        String filePath = new File("").getAbsolutePath();
        Scanner file = new Scanner(new FileReader(filePath + "/ticketOfficeInput.txt")).useDelimiter("\n");        
        int eventNum = Integer.parseInt(file.next().trim());//reads how many events are in the text file
                
            for(int i = eventNum; i > 0; i--)
            {
                Event nextEvent = new Event (file.next().trim(), Integer.parseInt(file.next().trim()));//reads the next 2 lines from the file and sends them to the constructor for Event. 1st line is event name, 2nd is number of tickets converted to an int
                events.add(nextEvent);
            }
        
        int clientNum = Integer.parseInt(file.next().trim());//reads how many clients are in the text file
        
             for(int i = clientNum; i > 0; i--)
            {
                String[] name = file.next().split("\\s");//reads the next line and splits it in to an array based on white space
                Client nextClient = new Client (name[0].trim(), name[1].trim());//takes the 2 elements in the array and sends them to the constructor for Client. 1st element is first name, 2nd is last name.
                clients.add(nextClient);
            }
            
        file.close();
    }
    
    /**
     * Takes the users input and calls the associated methods
     */
    public void getUserInput() throws FileNotFoundException
    {
        boolean finish = false;
        
        while(!finish) {
            printMenu();
            String input = getStrInput();
            
            switch (input) {
                case "f":
                    finish = true;//finish option
                    System.out.println("Goodbye");
                    break;
                case "e"://event info option
                    System.out.println(eventInfo());
                    break;
                case "c"://client info option
                    System.out.println(clientInfo());
                    break;
                case "b"://buy tickets
                    buyTickets();
                    break;
                case "r"://return tickets
                    returnTickets();
                    break;
                default: System.out.println("Please enter a letter from the menu");
                        break;
                    
            }
        }
    }
    
    /**
     * Buy tickets for the client for an event 
     */
    public void buyTickets() throws FileNotFoundException
    {   
        try{
            Client clientBuy = clientValid();
            if(clientBuy.getClientEvents().size() == 3){//The client already has tickets for 3 events
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("The client has already bought tickets for 3 events, they can't purchase tickets for any others. ");
                System.out.println("-----------------------------------------------------------------------------------------------");
                return;
              }
              
            Event eventBuy = eventValid(); //checks if the event is in the companies list 
            System.out.println("Please input the number of tickets the client wishes to buy");
            int numTix = getNumInput();
            
            if(numTix <= eventBuy.getNumTickets()) 
            {
                Event addEvent = new Event(eventBuy.getEventName(), numTix);
                int eventPos = Collections.binarySearch(clientBuy.getClientEvents(), addEvent);//searches the clients list of events for the event
                    
                    if(eventPos > 0){
                        Event existEvent = clientBuy.getClientEvents().get(eventPos);// if the client already has ticket for this event new tickets
                        existEvent.setNumTickets(numTix + existEvent.getNumTickets());//are added
                    }
                    else{
                        clientBuy.addClientEvent(addEvent); //if the client doesn't have tickets for this event yet
                    }
                eventBuy.setNumTickets(eventBuy.getNumTickets() - numTix);//removes tickets from the event
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("Transaction complete ");
                System.out.println("-----------------------------------------------------------------------------------------------");
            }
            else
            {
                printLetter(eventBuy, clientBuy);//prints a letter informing the client that tickets aren't avaliable 
            }

        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return;
        }
    }
    
    /**
     * Return tickets for an event // need to finish read from start
     */
    public void returnTickets()
    {
        try{
            Client clientSell = clientValid();
            Event eventSell = eventValid();       
            System.out.println("Please input the number of tickets the client wishes to cancel");
            int numTix = getNumInput();        
            Event sellEvent = new Event(eventSell.getEventName(), numTix);
            int eventPos = Collections.binarySearch(clientSell.getClientEvents(), sellEvent);//searches the clients list of events for the event specified
            
            if(eventPos == -1) 
            {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("The client has not previously purchased tickets for that event");
                System.out.println("---------------------------------------------------------------------------------");
                return;//if the client doesn't have tickets for this event
            }
          
            Event existEvent = clientSell.getClientEvents().get(eventPos);//points to the event in the clients arraylist
                       
            if(numTix > existEvent.getNumTickets()){
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("The client doesn't have that many tickets to return");
                System.out.println("---------------------------------------------------------------------------------");
                return;//if the client has less tickets than the amount they wish to return
            }
            else
            {
                existEvent.setNumTickets(existEvent.getNumTickets() - numTix);//tickets removed form the client
                if(existEvent.getNumTickets() == 0) clientSell.getClientEvents().remove(eventPos);//if the client returns all their tickets the associated object is removed
                eventSell.setNumTickets(eventSell.getNumTickets() + numTix);//tickets added back to the event
                System.out.println("-----------------------------------------------------------------------------------------------");
                System.out.println("Transaction complete ");
                System.out.println("-----------------------------------------------------------------------------------------------");
            }   
                       
           }
           
        catch(ArrayIndexOutOfBoundsException e)
        {
            return;
        }        
         
    
    }
    
    /**
     * Check if a client is valid
     */
    public Client clientValid() throws ArrayIndexOutOfBoundsException  
    {

        System.out.println("Please input the first name of the client");
        String firstToCheck = getStrInput();
        System.out.println("Please input the last name of the client");
        String lastToCheck = getStrInput();
        Client checkClient = new Client(firstToCheck, lastToCheck);//creates object to check against client arraylist 
        int clientPos = Collections.binarySearch(clients, checkClient);//searches the client arraylist and returns position if client exists or negative if not
        
            try
            {
                Client clientBuy = clients.get(clientPos);
                return clientBuy;
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("That name doesn't correspond with a registered client");
                System.out.println("Returning you to the main menu");
                System.out.println("---------------------------------------------------------------------------------");
                throw e;
            }
        
    }
    
    /**
     * Check if the event is valid
     */
    public Event eventValid() throws ArrayIndexOutOfBoundsException 
    {

        System.out.println("Please input the name of the event");
        String eventToCheck = getStrInput();
        Event checkEvent = new Event(eventToCheck, 0);//creates object to check against event arraylist
        int eventPos = Collections.binarySearch(events, checkEvent);//searches the event arraylist and returns position if event exists or negative if not
        
            try
            {
                Event eventBuy = events.get(eventPos);//tries to retrieve event 
                return eventBuy;
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("That name doesn't correspond with an event.");
                System.out.println("Returning you to the main menu");
                System.out.println("---------------------------------------------------------------------------------");
                throw e;
            }

    }
    
    /**
     * Reads input from the user via the console. If the user enters nothing they are prompted to enter something.
     * 
     * @return String - user input from the console.
     */
    public String getStrInput()
    {
        
        System.out.print("> ");   //print prompt
        String inputLine = reader.nextLine().trim();
        
        if (inputLine.isEmpty()) {   //stop the user from entering nothing
            while (inputLine.isEmpty()){ // loops until something is entered
                System.out.println("Please don't leave this entry blank, try again");
                System.out.print("> ");   //print prompt
                inputLine = reader.nextLine().trim();
            }
        }
        
        return inputLine;
    }
    
    /**
     * Reads input from the user via the console. If the user enters a negative, decimal number or characters they are prompted to enter a 
     * whole number.
     * 
     * @return int - user input from the console.
     */
    public int getNumInput()
    {
        int inputLine = 0;
        
        while (inputLine <= 0) { //loops until a valid number has been entered
            try {
                System.out.print("> "); //print prompt
                inputLine = Integer.parseInt(reader.nextLine());
                if (inputLine <=0) {
                    System.out.println("Please enter a positive number");
                }
            }
            
            catch(InputMismatchException e)  {
               System.out.println("That wasn't a valid number, please enter a whole number");
               reader.nextInt();
            }
        
        }
        
        return inputLine;
    }
    
    /**
     * Print menu to the console method
     * 
     */
    public void printMenu()
    {
        System.out.println("-------------------------------------------");
        System.out.println("f - finish");
        System.out.println("e - information on events");
        System.out.println("c - information about clients");
        System.out.println("b - buy tickets");
        System.out.println("r - return tickets");
        System.out.println("-------------------------------------------");
        
    }
    
    /**
     * Client information method
     */
    public String clientInfo()
    {
        StringBuilder sb = new StringBuilder(); //prints client info to the console
        for(Client c : clients){
            sb.append(c.toString());
        }
        String text = sb.toString();
        return text;        
    }
    
    /**
     * Event information method
     */
    public String eventInfo()
    {
        StringBuilder sb = new StringBuilder(); //prints event info to the console
        for(Event e : events){
            sb.append(e.toString());
        }
        String text = sb.toString();
        return text;        
    }
    
    /**
     * Letter to client if tickets aren't avaliable 
     */
    public void printLetter(Event eventBuy, Client clientBuy) throws FileNotFoundException
    {
        String filePath = new File("").getAbsolutePath();
        PrintWriter outFile = new PrintWriter(new FileOutputStream(filePath + "/messageToClient.txt", true));
        outFile.printf("Dear " + clientBuy.getFirstName() + " " + clientBuy.getLastName() + "%n%n" + "There aren't enough tickets left for the "
        + eventBuy.getEventName().toString()+ " event to fulfill your order.%n%n" + "Apologies%nWorld Sports Championships.%n%n");
        outFile.close();
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("There aren't enough tickets left for that event to fulfill the order");
        System.out.println("---------------------------------------------------------------------------------");
    }
}
