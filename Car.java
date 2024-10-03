import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter = 1000;
    private String carID;
    private String model;
    private String manufacturer;
    private String color;
    private int year;
    private int seats;
    private String licensePlate;
    private String engineType;
    private double autonomy;
    private ArrayList<Reservation> reservations;
    private boolean available; // To track availability


    public Car(String model, String manufacturer, String color, int year, int seats, String licensePlate, String engineType, double autonomy) {
        this.carID = "CAR" + idCounter++;
        this.model = model;
        this.manufacturer = manufacturer;
        this.color = color;
        this.year = year;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.engineType = engineType;
        this.autonomy = autonomy;
        this.reservations = new ArrayList<>();
        this.available = true;  // Initially available when created

    }

 

    public boolean isAvailable() {
        Date currentDate = new Date(); // Get the current date
        for (Reservation r : reservations) {
            if (!currentDate.before(r.getStartDate()) && !currentDate.after(r.getEndDate())) {
                return false; // Car is not available if there's an active reservation
            }
        }
        return true; // Car is available if no overlapping reservations
    }
    // Method to get the car's ID
    public String getCarID() {
        return carID;
    }
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setSeats(int seats) {
        this.seats = seats;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    
    public void setAutonomy(double autonomy) {
        this.autonomy = autonomy;
    }
    
    public boolean reserve(Date startDate, Date endDate, String name, String licenseNumber, String contactDetails) {
        // Check if the car is available during the requested time frame
        for (Reservation r : reservations) {
            if (!(endDate.before(addDays(r.getStartDate(), -1)) || startDate.after(addDays(r.getEndDate(), 1)))) {
                System.out.println("Car is already reserved during that time.");
                return false;
            }
        }
        // Create a new reservation and add it to the list of reservations
        reservations.add(new Reservation(startDate, endDate, name, licenseNumber, contactDetails));
        System.out.println("Car reserved successfully by " + name + " from " + startDate + " to " + endDate);
        return true;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean cancelReservation(Date startDate, Date endDate) {
        Reservation reservationToCancel = null;
        
        for (Reservation r : reservations) {
            if (r.getStartDate().equals(startDate) && r.getEndDate().equals(endDate)) {
                reservationToCancel = r;
                break;
            }
        }
        
        if (reservationToCancel != null) {
            reservations.remove(reservationToCancel);
            System.out.println("Reservation canceled for " + startDate + " to " + endDate);
            // If there are no future reservations left, mark the car as available
            if (reservations.isEmpty()) {
                setAvailable(true);
            }
            return true;
        } else {
            System.out.println("No reservation found for the provided dates.");
            return false;
        }
    }

    public void displayCarDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Car ID: " + carID);
        System.out.println("Model: " + model);
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Color: " + color);
        System.out.println("Year: " + year);
        System.out.println("Seats: " + seats);
        System.out.println("License Plate: " + licensePlate);
        System.out.println("Engine Type: " + engineType);
        System.out.println("Autonomy: " + autonomy + " km");
        if (reservations.isEmpty()) {
            System.out.println("No reservations.");
        } else {
            System.out.println("Reservations:");
            for (Reservation r : reservations) {
                System.out.println("Reserved by: " + r.getName() + " | License: " + r.getLicenseNumber() + " | Contact: " + r.getContactDetails());
                System.out.println("  From: " + sdf.format(r.getStartDate()) + " To: " + sdf.format(r.getEndDate()));
            }
        }
    }

    private Date addDays(Date date, int days) {
        long millis = date.getTime() + (days * 24L * 60 * 60 * 1000);
        return new Date(millis);
    }
}
