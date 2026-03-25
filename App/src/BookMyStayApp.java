import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = reservationServices.get(reservationId);
        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("Enter number of services: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter service name: ");
            String name = sc.nextLine();
            System.out.print("Enter service cost: ");
            double cost = sc.nextDouble();
            sc.nextLine();
            manager.addService(reservationId, new Service(name, cost));
        }

        List<Service> services = manager.getServices(reservationId);
        System.out.println("Services for Reservation ID " + reservationId + ":");
        for (Service s : services) {
            System.out.println(s.getName() + " - " + s.getCost());
        }

        double totalCost = manager.calculateTotalCost(reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}