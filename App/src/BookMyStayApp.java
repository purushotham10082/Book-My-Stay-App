/**
 * Hotel Booking Application - Booking Request Queue (First-Come-First-Served)
 * Author: Student
 * Version: 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + ", Requested Room: " + roomType);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Booking request added for guest: " + reservation.getGuestName());
    }

    public Reservation peekNextRequest() {
        return queue.peek();
    }

    public Reservation processNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayAllRequests() {
        System.out.println("\nCurrent Booking Queue:");
        for (Reservation res : queue) {
            res.displayReservation();
        }
    }
}

public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Alice", "Single");
        Reservation r2 = new Reservation("Bob", "Double");
        Reservation r3 = new Reservation("Charlie", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        bookingQueue.displayAllRequests();

        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) next.displayReservation();
    }
}