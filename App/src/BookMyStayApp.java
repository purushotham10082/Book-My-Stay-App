/**
 * Hotel Booking Application - Reservation Confirmation & Room Allocation
 * Author: Student
 * Version: 6.0
 */

import java.util.*;

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
}

class RoomInventory {
    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        availability.put(type, count);
    }

    public boolean isAvailable(String roomType) {
        return availability.getOrDefault(roomType, 0) > 0;
    }

    public void allocateRoom(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
        }
    }

    public int getAvailableRooms(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

class RoomAllocationService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public String allocateRoom(Reservation res) {
        String type = res.getRoomType();
        if (!inventory.isAvailable(type)) {
            return null;
        }

        String roomID = type.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 5);

        allocatedRooms.putIfAbsent(type, new HashSet<>());
        allocatedRooms.get(type).add(roomID);

        inventory.allocateRoom(type);

        return roomID;
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation res) {
        queue.offer(res);
    }

    public Reservation pollRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

public class UseCase6RoomAllocationService {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 2);
        inventory.addRoomType("Suite", 1);

        BookingRequestQueue requestQueue = new BookingRequestQueue();
        requestQueue.addRequest(new Reservation("Alice", "Single"));
        requestQueue.addRequest(new Reservation("Bob", "Double"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite"));
        requestQueue.addRequest(new Reservation("David", "Single"));

        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.pollRequest();
            String allocatedRoomID = allocationService.allocateRoom(res);
            if (allocatedRoomID != null) {
                System.out.println("Reservation confirmed for " + res.getGuestName() + " | Room ID: " + allocatedRoomID);
            } else {
                System.out.println("Reservation failed for " + res.getGuestName() + " | Room type not available");
            }
        }

        allocationService.displayAllocations();
    }
}