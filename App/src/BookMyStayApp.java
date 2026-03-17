/**
 * Hotel Booking Application - Centralized Room Inventory Management
 * Author: Student
 * Version: 3.1
 */

import java.util.HashMap;
import java.util.Map;

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

    public String getType() {
        return type;
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

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(Room room, int count) {
        availability.put(room.getType(), count);
    }

    public int getAvailableRooms(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        availability.put(roomType, newCount);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom, 10);
        inventory.addRoomType(doubleRoom, 5);
        inventory.addRoomType(suiteRoom, 2);

        System.out.println("Welcome to Book My Stay - Hotel Booking System v3.1\n");

        singleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailableRooms(singleRoom.getType()) + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailableRooms(doubleRoom.getType()) + "\n");

        suiteRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailableRooms(suiteRoom.getType()) + "\n");

        inventory.displayInventory();
    }
}