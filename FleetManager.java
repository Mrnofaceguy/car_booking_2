import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class FleetManager {
    private ArrayList<Car> fleet;
    private static final String FILE_NAME = "car_fleet.dat";

    public FleetManager() {
        this.fleet = new ArrayList<>();
        loadFleetFromFile();  // Load the fleet from file when the program starts
    }

    // Add a new car to the fleet
    public void addCar(String model, String manufacturer, String color, int year, int seats, String licensePlate, String engineType, double autonomy) {
        Car car = new Car(model, manufacturer, color, year, seats, licensePlate, engineType, autonomy);
        fleet.add(car);
        System.out.println("Car added: " + car.getCarID());
    }


    

    // Cancel a car reservation
    public boolean cancelReservation(String carID, Date startDate, Date endDate) {
        for (Car car : fleet) {
            if (car.getCarID().equals(carID)) {
                return car.cancelReservation(startDate, endDate);
            }
        }
        System.out.println("Car with ID " + carID + " not found.");
        return false;
    }

    // Load and save methods for persistence
    public void saveFleetToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(fleet);  // Write the fleet to the file
            System.out.println("Fleet saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving fleet: " + e.getMessage());
        }
    }

    public void loadFleetFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            fleet = (ArrayList<Car>) ois.readObject();  // Read the fleet from the file
            System.out.println("Fleet loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved fleet found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading fleet: " + e.getMessage());
        }
    }



    Car findCarByID(String carID) {
        for (Car car : fleet) {
            if (car.getCarID().equals(carID)) {
                return car;
            }
        }
        return null; // Car not found
    }
    // Update car details (accepts all parameters including new attributes)
    public void updateCarDetails(String carID, String model, String manufacturer, String color, int year, int seats, String licensePlate, String engineType, double autonomy) {
        Car carToUpdate = findCarByID(carID);
        if (carToUpdate != null) {
            // Update the car's attributes
            carToUpdate.setModel(model);
            carToUpdate.setManufacturer(manufacturer);
            carToUpdate.setColor(color);
            carToUpdate.setYear(year);
            carToUpdate.setSeats(seats);
            carToUpdate.setLicensePlate(licensePlate);
            carToUpdate.setEngineType(engineType);
            carToUpdate.setAutonomy(autonomy);
            System.out.println("Car details updated successfully.");
        } else {
            System.out.println("Car not found.");
        }
    }
    

    // Remove a car based on its ID
    public void removeCar(String carID) {
        // Loop through the fleet and find the car by ID
        for (Car car : fleet) {
            if (car.getCarID().equals(carID)) {
                fleet.remove(car);
                System.out.println("Car removed successfully.");
                return;
            }
        }
        System.out.println("Car with the given ID not found.");
    }

    // Display all cars in the fleet
    public void displayFleet() {
        for (Car car : fleet) {
            car.displayCarDetails();
            System.out.println();
        }
    }

    // Reserve a car based on its ID
    public void reserveCar(String carID, Date startDate, Date endDate, String name, String licenseNumber, String contactDetails) {
        Car car = findCarByID(carID);
        if (car != null) {
            // Pass the name, license number, and contact details as arguments
            car.reserve(startDate, endDate, name, licenseNumber, contactDetails);
        } else {
            System.out.println("Car not found.");
        }
    }


    // Cancel a reservation for a car
    public void cancelCarReservation(String carID, Date startDate, Date endDate) {
        Car car = findCarByID(carID);
        if (car != null) {
            if (car.cancelReservation(startDate, endDate)) {
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("Failed to cancel the reservation. No matching reservation found.");
            }
        } else {
            System.out.println("Car not found.");
        }
    }

    // Display available cars
    public void displayAvailableCars() {
        System.out.println("\nAvailable Cars:");
        boolean anyAvailable = false;
        for (Car car : fleet) {
            if (car.isAvailable()) {
                car.displayCarDetails();
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No cars are currently available.");
        }
    }
}
