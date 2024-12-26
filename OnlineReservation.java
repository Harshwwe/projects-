import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

//class for registered user
class User{
    private String username;
    private String password;
    public User(String username, String password ){
        this.username=username;
        this.password=password;
    }
    //getter for username
    public String getUsername(){
        return username;
    }
    //check password
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}
//class for train reservation
class Reservation{

    private String pnr;
    private String passengerName;
    private int trainNumber;
    private String Class;
    private String journeyDate;
    private String StartStation;
    private String EndStation;

    
    public Reservation(String passengerName,int trainNumber,String Class,String journeyDate,String StartStation, String EndStation){
        //generate pnr
        this.pnr = UUID.randomUUID().toString().substring(0, 8);
        this.passengerName=passengerName;
        this.trainNumber=trainNumber;
        this.Class=Class;
        this.journeyDate=journeyDate;
        this.StartStation=StartStation;
        this.EndStation= EndStation;
    }
    public String getPnr(){
        return pnr;
    }
    
    @Override
    public String toString(){
        return "PNR: "+ pnr + "\nPassenger Name: "+ passengerName + "\nTrain Number: "+ trainNumber + "\nClass: " + Class + "\nJourney Date (DD/MM/YYYY): "+ journeyDate +"\nStarting Station: "+StartStation+"\nEnding Station: "+EndStation;
    }
}
//Main Class for Reservation
public class OnlineReservation {
    private static ArrayList<User> userList= new ArrayList<>();
    private static ArrayList<Reservation> reservationList= new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        initializeUsers();
        System.out.println("====Welcome to Online Reservation System====");
        if (login()){
            boolean exit = false;
            while(!exit){
                System.out.println("\n Main Menu:");
                System.out.println("1.Make a reservation");
                System.out.println("2.Cancel a reservation");
                System.out.println("3.View reservations");
                System.out.println("4.Exit");
                System.err.println("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:makeReservation();
                    break;
                    case 2:cancleReservation();
                    break;
                    case 3:viewReservation();
                    break;
                    case 4:
                        System.out.println("Thank you for coming.");
                        exit =true;
                    break;
                    
                    default:
                       System.out.println("Invalid Choise. Please try again");
                }
            }
        }
        else{
            System.out.println("Login failed. Exiting the system.");
        }
    }
    //initialize predefined users
    private static void initializeUsers() {
        userList.add(new User("admin", "1234"));
        userList.add(new User("user1", "5678"));
    }
    //login method
    private static boolean login(){
        System.out.print("Enter Username: ");
        String username= sc.nextLine();
        System.out.print("Enter Password: ");
        String password= sc.nextLine();
        for (User user: userList){
            if(user.getUsername().equals(username) && user.checkPassword(password)){
                System.out.println("Login Successful.");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    
    }
    //method for making reserevation
    private static void makeReservation(){
        System.out.print("Enter passenger name : ");
        String name= sc.nextLine();
        System.out.print("Enter train number : ");
        int trainNumber= sc.nextInt();
        sc.nextLine();
        switch (trainNumber) {
            case 11272:
                System.out.println("Vindhyachal Express");
                break;
            case 11465: 
                System.out.println("Jabalpur Express");
                break;
            case 22170:
                System.out.println("Humsafar Express");  
                break;      
        
            default:
                System.out.println("Generic Train");
        }
        System.out.print("Enter the Class (AC/Sleeper) : ");
        String Class= sc.nextLine();
        System.out.print("Enter Journey Date (DD/MM/YYYY) : ");
        String journeyDate = sc.nextLine();
        System.out.print("Enter Starting Station : ");
        String StartStation=sc.nextLine();
        System.out.print("Enter End Station : ");
        String EndStation=sc.nextLine();
    
        //create and store reservation
        Reservation reservation = new Reservation(name,trainNumber,Class,journeyDate,StartStation,EndStation);
        reservationList.add(reservation);
        System.out.println("Reservation Successful.");
        System.out.println("Your PNR : "+reservation.getPnr());
    }
    
    //cancel a reservation
    private static void cancleReservation(){
        System.out.print("Enter PNR to cancel: ");
        String pnr=sc.nextLine();
        Reservation reservationToCancel = null;
        for (Reservation reservation : reservationList ){
            if (reservation.getPnr().equalsIgnoreCase(pnr)) {
                reservationToCancel=reservation;
                break;
            }
        }
        if(reservationToCancel!=null){
            System.out.println("Reservation Details: ");
            System.out.println(reservationToCancel);
            System.out.println("Are you sure you want to cancel this reservation? (yes/no): ");
            String confirmation =sc.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                reservationList.remove(reservationToCancel);
                System.out.println("Reservation cancel Successfully.");
            }
            else{
                System.out.println("Cancelation Aborted.");
            }
        }
        else{
            System.out.println("PNR not found. Please try again.");
        }
    }
    //view all reservation
    private static void viewReservation(){
        if(reservationList.isEmpty()){
            System.out.println("No reservation is found.");
        }
        else{
            System.out.println("All reservations: ");
            for (Reservation reservation: reservationList){
                System.out.println("-------------");
                System.out.println(reservation);
            }
        }
    }

}
