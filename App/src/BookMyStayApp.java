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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}

class BookingReportService {
    public void generateReport(List<Reservation> reservations) {
        System.out.println("Booking Report:");
        for (Reservation r : reservations) {
            System.out.println(
                    r.getReservationId() + " | " +
                            r.getGuestName() + " | " +
                            r.getRoomType()
            );
        }
        System.out.println("Total Bookings: " + reservations.size());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of bookings: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Reservation ID: ");
            String id = sc.nextLine();
            System.out.print("Enter Guest Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Room Type: ");
            String room = sc.nextLine();

            Reservation reservation = new Reservation(id, name, room);
            history.addReservation(reservation);
        }

        List<Reservation> reservations = history.getAllReservations();
        reportService.generateReport(reservations);
    }
}