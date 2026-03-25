import java.util.*;
import java.util.concurrent.*;

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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Reservation> bookings = new HashMap<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean bookRoom(String id, String name, String roomType) {
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

    public synchronized void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingTask implements Runnable {
    private BookingSystem system;
    private String id;
    private String name;
    private String roomType;

    public BookingTask(BookingSystem system, String id, String name, String roomType) {
        this.system = system;
        this.id = id;
        this.name = name;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        system.bookRoom(id, name, roomType);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        BookingSystem system = new BookingSystem();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<BookingTask> tasks = Arrays.asList(
                new BookingTask(system, "R1", "Alice", "Single"),
                new BookingTask(system, "R2", "Bob", "Single"),
                new BookingTask(system, "R3", "Charlie", "Double"),
                new BookingTask(system, "R4", "David", "Suite"),
                new BookingTask(system, "R5", "Eve", "Single")
        );

        for (BookingTask task : tasks) {
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        system.displayInventory();
    }
}