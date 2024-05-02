package com.example.taxi_backend.controllers;

import com.example.taxi_backend.dtos.*;
import com.example.taxi_backend.dtos.trip.TripForDriverProfileDto;
import com.example.taxi_backend.entities.*;
import com.example.taxi_backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/driver/")
public class DriverController {
    private DriverRepository driverRepository;
    private DriverRatingRepository driverRatingRepository;
    private ReviewsRepository reviewsRepository;
    private CarRepository carRepository;
    private GetUserInfoJsonRepository userInfoJsonRepository;
    private LogRepository logRepository;

    @Autowired
    public DriverController(DriverRepository driverRepository, DriverRatingRepository driverRatingRepository, ReviewsRepository reviewsRepository, CarRepository carRepository, GetUserInfoJsonRepository userInfoJsonRepository, LogRepository logRepository) {
        this.driverRepository = driverRepository;
        this.driverRatingRepository = driverRatingRepository;
        this.reviewsRepository = reviewsRepository;
        this.carRepository = carRepository;
        this.userInfoJsonRepository = userInfoJsonRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable Long id){
        Driver driver = driverRepository.findById(id).get();

        if(driver != null){
            DriverDto driverDto = new DriverDto(driver);
            return ResponseEntity.ok(driverDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}/trips")
    public ResponseEntity<Set<TripForDriverProfileDto>> getSetOfTrips(@PathVariable Long id){
        Set<Trip> trips = driverRepository.findById(id).get().getTrips();

        Set<TripForDriverProfileDto> tripResponseDtos = new HashSet<>();

        for(Trip trip : trips){
            tripResponseDtos.add(new TripForDriverProfileDto(trip));
        }

        return ResponseEntity.ok(tripResponseDtos);
    }
    @GetMapping("/{id}/rating")
    public ResponseEntity<DriverRatingResponseDto> getDriverRating(@PathVariable Long id){
        DriverRating driverRating = driverRatingRepository.findById(id).get();

        List<ReviewsDto> reviewsDtos = new ArrayList<>();

        for (Review review: driverRating.getReviews()){
            reviewsDtos.add(new ReviewsDto(review.getComment()));
        }

        return ResponseEntity.ok(new DriverRatingResponseDto(driverRating.getRating(), driverRating.getTripsCount(), reviewsDtos));
    }
    @GetMapping("")
    public List<DriverToAuthenticateDto> getAllNonAuthenticatedDrivers(){
        List<Driver> drivers = driverRepository.findAllByStatus("Non-authenticated");
        List<DriverToAuthenticateDto> authenticateDtos = new ArrayList<>();

        for (Driver driver : drivers){
            if (!driver.getLicense())
                continue;

            DriverToAuthenticateDto driverToAuthenticateDto = new DriverToAuthenticateDto();

            driverToAuthenticateDto.setId(driver.getId());
            driverToAuthenticateDto.setName(driver.getName());
            driverToAuthenticateDto.setSurname(driver.getSurname());
            driverToAuthenticateDto.setEmail(driver.getEmail());
            driverToAuthenticateDto.setAuthenticated(false);

            authenticateDtos.add(driverToAuthenticateDto);
        }

        return authenticateDtos;
    }
    @PutMapping("authenticate")
    public void authenticateDriver(@RequestBody DriverToAuthenticateDto driverToAuthenticateDto){
        Driver driver = driverRepository.findById(driverToAuthenticateDto.getId()).get();

        driver.setStatus("Authenticated");

        driverRepository.save(driver);

        Log log = new Log();
        log.setMessage("Driver #" + driverToAuthenticateDto.getId() + "was authenticated.");
        logRepository.save(log);
    }

    @PutMapping("")
    public void setLicenseTrue(@RequestBody Long id){
        Driver driver = driverRepository.findById(id).get();

        driver.setLicense(true);

        driverRepository.save(driver);

        Car car = carRepository.findById(id).get();

        car.setDocument(true);

        carRepository.save(car);
    }

    @GetMapping("{id}/car")
    public boolean checkIfDriverHasCar(@PathVariable long id){
        Optional<Car> car = carRepository.findById(id);

        if(car.isPresent())
            return true;
        else
            return false;
    }

    @PostMapping("{id}/car")
    public void registerDriverCar(@RequestBody CarDto carDto){
        Car car = new Car();

        car.setId(carDto.getId());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setDocument(false);

        carRepository.save(car);

        Log log = new Log();
        log.setMessage("Driver #" + carDto.getId() + "registered a car.");
        logRepository.save(log);
    }
    @GetMapping("/downloadUserData/{id}")
    public ResponseEntity<byte[]> downloadUserData(@PathVariable long id) {
        String jsonData = userInfoJsonRepository.getDriverData(id);

        byte[] jsonDataBytes = jsonData.getBytes();

        Log log = new Log();
        log.setMessage("Driver #" + id + "got his info in Json");
        logRepository.save(log);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Disposition", "attachment; filename=driverData.json")
                .body(jsonDataBytes);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Boolean> checkIfDriverRegisteredCar(@PathVariable long id){
        Optional<Car> car = carRepository.findById(id);

        if (car.isPresent())
            return ResponseEntity.ok(true);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/car")
    public void registerCar(@RequestBody CarDto carDto){
        Car car = new Car();

        car.setId(carDto.getId());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setDocument(false);

        carRepository.save(car);
    }
}
