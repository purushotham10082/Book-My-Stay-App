/**
 * Hotel Booking Application - Room Initialization
 * Author: Student
 * Version: 2.1
 */

abstract class Room {
    protected String type;
    protected int beds;
    protected double pricePerNight;

    public Room(String type, int beds, double pricePerNight) {
        this.type = type;
        this.beds = beds;
        this.pricePerNight = pricePerNight;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Number of Beds: " + beds);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 50.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 90.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 150.0);
    }
}

public class  BookMyStayApp {
    public static void main(String[] args) {

        int singleRoomAvailability = 10;
        int doubleRoomAvailability = 5;
        int suiteRoomAvailability = 2;

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("Welcome to Book My Stay - Hotel Booking System v2.1\n");

        singleRoom.displayDetails();
        System.out.println("Available: " + singleRoomAvailability + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleRoomAvailability + "\n");

        suiteRoom.displayDetails();
        System.out.println("available: " + suiteRoomAvailability + "\n");
    }
}