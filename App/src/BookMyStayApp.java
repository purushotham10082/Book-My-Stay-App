import java.util.*;

class Reservation {
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

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Reservation> bookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String id, String name, String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("Booking Failed: No rooms available");
            return;
        }

        Reservation reservation = new Reservation(id, name, roomType);
        bookings.put(id, reservation);
        inventory.put(roomType, available - 1);
        rollbackStack.push(roomType);

        System.out.println("Booking Successful");
    }

    public void cancelBooking(String id) {
        if (!bookings.containsKey(id)) {
            System.out.println("Cancellation Failed: Invalid reservation ID");
            return;
        }

        Reservation reservation = bookings.remove(id);
        String roomType = reservation.getRoomType();

        inventory.put(roomType, inventory.get(roomType) + 1);

        if (!rollbackStack.isEmpty()) {
            rollbackStack.pop();
        }

        System.out.println("Cancellation Successful for ID: " + id);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Guest Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Room Type (Single/Double/Suite): ");
        String roomType = sc.nextLine();

        system.bookRoom(id, name, roomType);

        system.displayInventory();

        System.out.print("Enter Reservation ID to cancel: ");
        String cancelId = sc.nextLine();

        system.cancelBooking(cancelId);

        system.displayInventory();
    }
}