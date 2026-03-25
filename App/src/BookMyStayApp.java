import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingSystem implements Serializable {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Reservation> bookings = new HashMap<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean bookRoom(String id, String name, String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("Booking Failed for " + name + ": No rooms available");
            return false;
        }
        Reservation reservation = new Reservation(id, name, roomType);
        bookings.put(id, reservation);
        inventory.put(roomType, available - 1);
        System.out.println("Booking Successful for " + name + " (" + roomType + ")");
        return true;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public Map<String, Reservation> getBookings() {
        return bookings;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "booking_system.ser";

    public static void save(BookingSystem system) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(system);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public static BookingSystem load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return new BookingSystem();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            BookingSystem system = (BookingSystem) in.readObject();
            System.out.println("System state loaded successfully.");
            return system;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading system state: " + e.getMessage());
            System.out.println("Starting with fresh system state.");
            return new BookingSystem();
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = PersistenceService.load();

        System.out.print("Enter number of bookings to add: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Reservation ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            system.bookRoom(id, name, roomType);
        }

        system.displayInventory();

        PersistenceService.save(system);
    }
}