import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

class BookingValidator {
    private static final Set<String> validRoomTypes = new HashSet<>(
            Arrays.asList("Single", "Double", "Suite")
    );

    public static void validate(String roomType, int availableRooms) throws InvalidBookingException {
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected");
        }
        if (availableRooms <= 0) {
            throw new InvalidBookingException("No rooms available for selected type");
        }
    }
}

class BookingSystem {
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public Reservation bookRoom(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        int available = inventory.getOrDefault(roomType, 0);
        BookingValidator.validate(roomType, available);

        inventory.put(roomType, available - 1);

        return new Reservation(reservationId, guestName, roomType);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        try {
            System.out.print("Enter Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String roomType = sc.nextLine();

            Reservation reservation = system.bookRoom(id, name, roomType);

            System.out.println("Booking Successful:");
            System.out.println(
                    reservation.getReservationId() + " | " +
                            reservation.getGuestName() + " | " +
                            reservation.getRoomType()
            );

        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}