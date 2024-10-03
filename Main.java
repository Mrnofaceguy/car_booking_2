import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FleetManager manager = new FleetManager();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Save fleet data on exit
            System.out.println("Saving fleet to file...");
            manager.saveFleetToFile();
            System.out.println("Fleet saved successfully.");
        }));

        while (true) {
            System.out.println("1. Admin Actions");
            System.out.println("2. User Actions");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (option == 1) {
                // Admin actions with password protection
                System.out.print("Enter password for admin access: ");
                String password = scanner.nextLine();

                if (password.equals("1234")) {
                    System.out.println("Admin access granted.");

                    while (true) {
                        System.out.println("\nAdmin Menu:");
                        System.out.println("1. Add Car");
                        System.out.println("2. Remove Car");
                        System.out.println("3. Update Car Details");
                        System.out.println("4. View Fleet");
                        System.out.println("5. Exit Admin Menu");
                        System.out.print("Select an option: ");
                        int adminOption = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        if (adminOption == 1) {
                            // Add car
                            System.out.print("Enter Model: ");
                            String model = scanner.nextLine();
                            System.out.print("Enter Manufacturer: ");
                            String manufacturer = scanner.nextLine();
                            System.out.print("Enter Color: ");
                            String color = scanner.nextLine();
                            System.out.print("Enter Year: ");
                            int year = scanner.nextInt();
                            System.out.print("Enter Seats: ");
                            int seats = scanner.nextInt();
                            scanner.nextLine(); // consume newline
                            System.out.print("Enter License Plate: ");
                            String licensePlate = scanner.nextLine();
                            System.out.print("Enter Engine Type: ");
                            String engineType = scanner.nextLine();
                            System.out.print("Enter Autonomy (in km): ");
                            double autonomy = scanner.nextDouble();
                            scanner.nextLine(); // consume newline

                            manager.addCar(model, manufacturer, color, year, seats, licensePlate, engineType, autonomy);

                        } else if (adminOption == 2) {
                            // Remove car
                            System.out.print("Enter Car ID to Remove: ");
                            String removeCarID = scanner.nextLine();
                            manager.removeCar(removeCarID);
                        }

                            else if (adminOption == 3) {
                                // Update car details
                                System.out.print("Enter Car ID to Update: ");
                                String carID = scanner.nextLine();
                                
                                System.out.print("Enter New Model: ");
                                String newModel = scanner.nextLine();
                                System.out.print("Enter New Manufacturer: ");
                                String newManufacturer = scanner.nextLine();
                                System.out.print("Enter New Color: ");
                                String newColor = scanner.nextLine();
                                System.out.print("Enter New Year: ");
                                int newYear = scanner.nextInt();
                                System.out.print("Enter New Seats: ");
                                int newSeats = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                                System.out.print("Enter New License Plate: ");
                                String newLicensePlate = scanner.nextLine();
                                System.out.print("Enter New Engine Type: ");
                                String newEngineType = scanner.nextLine();
                                System.out.print("Enter New Autonomy (in km): ");
                                double newAutonomy = scanner.nextDouble();
                                scanner.nextLine(); // consume newline
                            
                                manager.updateCarDetails(carID, newModel, newManufacturer, newColor, newYear, newSeats, newLicensePlate, newEngineType, newAutonomy);
                            }
                            
                        else if (adminOption == 4) {
                            // Display Fleet
                            manager.displayFleet();

                        } else if (adminOption == 5) {
                            // Exit Admin Menu
                            break;

                        } else {
                            System.out.println("Invalid option, try again.");
                        }
                    }

                } else {
                    System.out.println("Incorrect password. Access denied.");
                }

            } else if (option == 2) {
                // User actions
                System.out.println("\nUser Menu:");
                System.out.println("1. View Available Cars");
                System.out.println("2. Reserve a Car");
                System.out.println("3. Cancel a Reservation");
                System.out.println("4. Exit");
                System.out.print("Select an option: ");
                int userOption = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (userOption == 1) {
                    // Display available cars to user
                    manager.displayAvailableCars();

                } else if (userOption == 2) {
                    // Reserve a car
                    System.out.print("Enter Car ID to Reserve: ");
                    String carID = scanner.nextLine();
                    System.out.print("Enter Reservation Start Date (yyyy-mm-dd): ");
                    String startDateStr = scanner.nextLine();
                    System.out.print("Enter Reservation End Date (yyyy-mm-dd): ");
                    String endDateStr = scanner.nextLine();
                    System.out.print("Enter your Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter your License Number: ");
                    String licenseNumber = scanner.nextLine();
                    System.out.print("Enter your Contact Details: ");
                    String contactDetails = scanner.nextLine();
                    Car car = manager.findCarByID(carID);

                    try {
                        Date startDate = java.sql.Date.valueOf(startDateStr);
                        Date endDate = java.sql.Date.valueOf(endDateStr);
                        if (car.reserve(startDate, endDate, name, licenseNumber, contactDetails)
                        ) {
                            System.out.println("Car reserved successfully.");
                        } else {
                            System.out.println("Failed to reserve the car.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
                    }

                } else if (userOption == 3) {
                    // Cancel a reservation
                    System.out.print("Enter Car ID to Cancel Reservation: ");
                    String carID = scanner.nextLine();
                    System.out.print("Enter Reservation Start Date (yyyy-mm-dd): ");
                    String startDateStr = scanner.nextLine();
                    System.out.print("Enter Reservation End Date (yyyy-mm-dd): ");
                    String endDateStr = scanner.nextLine();

                    try {
                        Date startDate = java.sql.Date.valueOf(startDateStr);
                        Date endDate = java.sql.Date.valueOf(endDateStr);
                        if (manager.cancelReservation(carID, startDate, endDate)) {
                            System.out.println("Reservation canceled successfully.");
                        } else {
                            System.out.println("Failed to cancel the reservation.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
                    }

                } else if (userOption == 4) {
                    // Exit
                    break;

                } else {
                    System.out.println("Invalid option, try again.");
                }

            } else if (option == 3) {
                // Exit the program
                break;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }

        scanner.close();
    }
}
