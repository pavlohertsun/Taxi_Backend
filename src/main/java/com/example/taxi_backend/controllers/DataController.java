package com.example.taxi_backend.controllers;

import com.example.taxi_backend.entities.*;
import com.example.taxi_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@RestController
@RequestMapping("/api/populate")
public class DataController {
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private DriverRepository driverRepository;
    private TripRepository tripRepository;
    private CarRepository carRepository;
    private TripRatingRepository tripRatingRepository;
    private LogRepository logRepository;

    @Autowired
    public DataController(UserRepository userRepository, CustomerRepository customerRepository, DriverRepository driverRepository, TripRepository tripRepository, CarRepository carRepository, TripRatingRepository tripRatingRepository, LogRepository logRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.carRepository = carRepository;
        this.tripRatingRepository = tripRatingRepository;
        this.logRepository = logRepository;
    }

    @PostMapping("/customers")
    public void populateDCustomers(){
        try(Scanner scanner = new Scanner(new File("/Users/pavlo_hertsun/Downloads/customers.csv"))){
            scanner.nextLine();

            List<Customer> customers = new ArrayList<>();
            List<User> users = new ArrayList<>();

            while(scanner.hasNext()){
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                User user = new User();

                user.setUsername(parts[2]);
                user.setPassword(parts[6]);
                user.setRole(Role.USER);

                Customer customer = new Customer();

                customer.setName(parts[0]);
                customer.setSurname(parts[1]);
                customer.setEmail(parts[2]);
                customer.setPhoneNumber(parts[3]);
                customer.setRating(Integer.parseInt(parts[4]));
                customer.setBalance(Double.parseDouble(parts[5]));

                customers.add(customer);
                users.add(user);
            }
            userRepository.saveAll(users);
            customerRepository.saveAll(customers);
        } catch (FileNotFoundException ex){
            System.out.println("failed, reason:" + ex.getMessage());
        }
    }
    @PostMapping("/drivers")
    public void populateDrivers(){
        try(Scanner scanner = new Scanner(new File("/Users/pavlo_hertsun/Downloads/drivers.csv"))){
            scanner.nextLine();

            List<Driver> drivers = new ArrayList<>();
            List<User> users = new ArrayList<>();

            while(scanner.hasNext()){
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                User user = new User();

                user.setUsername(parts[2]);
                user.setPassword(parts[5]);
                user.setRole(Role.DRIVER);

                Driver driver = new Driver();

                driver.setName(parts[0]);
                driver.setSurname(parts[1]);
                driver.setEmail(parts[2]);
                driver.setPhoneNumber(parts[3]);
                driver.setLicense(false);
                driver.setStatus("Non-authenticated");
                driver.setBalance(Double.parseDouble(parts[4]));

                drivers.add(driver);
                users.add(user);
            }
            userRepository.saveAll(users);
            driverRepository.saveAll(drivers);
        } catch (FileNotFoundException ex){
            System.out.println("failed, reason:" + ex.getMessage());
        }
    }
    @PostMapping("/trips")
    public void populateTrips(){
        try(Scanner scanner = new Scanner(new File("/Users/pavlo_hertsun/Downloads/trips.csv"))){
            scanner.nextLine();

            List<Trip> trips = new ArrayList<>();

            while(scanner.hasNext()){
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                Trip trip = new Trip();

                trip.setStartPoint(parts[0]);
                trip.setEndPoint(parts[1]);
                trip.setPrice(Double.parseDouble(parts[2]));
                trip.setStatus("Created");
                trip.setRate("Low");
                trip.setDescription(" - ");
                trip.setUser(customerRepository.findById(Long.parseLong(parts[3])).get());

                trips.add(trip);
            }
            tripRepository.saveAll(trips);
        } catch (FileNotFoundException ex){
            System.out.println("failed, reason:" + ex.getMessage());
        }
    }
    @PostMapping("/cars")
    public void populateCars(){
        try(Scanner scanner = new Scanner(new File("/Users/pavlo_hertsun/Downloads/cars.csv"))){
            scanner.nextLine();

            List<Car> cars = new ArrayList<>();

            while(scanner.hasNext()){
                String line = scanner.nextLine();

                Car car = new Car();

                car.setLicensePlate(line);
                car.setDocument(false);

                cars.add(car);
            }
            carRepository.saveAll(cars);
        } catch (FileNotFoundException ex){
            System.out.println("failed, reason:" + ex.getMessage());
        }
    }
    @PostMapping("/tripsRating")
    public void populateTripsRating(){
        try(Scanner scanner = new Scanner(new File("/Users/pavlo_hertsun/Downloads/trips_rating.csv"))){
            scanner.nextLine();

            List<TripRating> tripRatings = new ArrayList<>();

            while(scanner.hasNext()){
                String line = scanner.nextLine();

                String[] parts = line.split(",");

                Trip trip = tripRepository.findById(Long.parseLong(parts[0])).get();

                if(trip.getStatus().equals("Completed")){
                    TripRating tripRating = new TripRating();

                    tripRating.setId(Long.parseLong(parts[0]));
                    tripRating.setcRate(Integer.parseInt(parts[1]));
                    tripRating.setdRate(Integer.parseInt(parts[2]));
                    tripRating.setComment(parts[3]);

                    tripRatings.add(tripRating);
                }
            }

            tripRatingRepository.saveAll(tripRatings);
        } catch (FileNotFoundException ex){
            System.out.println("failed, reason:" + ex.getMessage());
        }
    }
}
