/**
 * Hotel Booking Application - Room Search & Availability Check
 * Author: Student
 * Version: 4.0
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

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

class RoomSearchService {
    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("Available Rooms for Booking:\n");
        for (Room room : rooms) {
            int available = inventory.getAvailableRooms(room.getType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
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
        inventory.addRoomType(suiteRoom, 0); // Suite currently unavailable

        Room[] rooms = { singleRoom, doubleRoom, suiteRoom };

        System.out.println("Welcome to Book My Stay - Hotel Booking System v4.0\n");

        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.searchAvailableRooms(rooms);
    }
}
