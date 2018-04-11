package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class RESTController {

    private int count = 1;

    @Autowired
    private GreetingDao greetingDao;
    private VehicleDao vehicleDao;

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody Greeting g) throws IOException {
        greetingDao.create(g);
        return g;
    }

    @RequestMapping(value = "/getGreeting/{id}", method = RequestMethod.GET)
    public Greeting getGreeting(@PathVariable("id") int id) throws IOException {
        return greetingDao.getById(id);
    }

    @RequestMapping(value = "/deleteGreeting/{id}", method = RequestMethod.DELETE)
    public String deleteGreeting(@PathVariable int id) throws IOException {
        return greetingDao.delete(id);
    }

    @RequestMapping(value = "/updateGreeting/{id}", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String message, @PathVariable int id) throws IOException {
        return greetingDao.update(id, message);
    }

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        return vehicleDao.create(newVehicle);
    }

    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable ("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }

    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeVehicle(@PathVariable ("id") int id) throws IOException {
        Vehicle vehicle = vehicleDao.getById(id);
        if(vehicle != null) {
            vehicleDao.delete(vehicle);
            return new ResponseEntity<>("Deleted Vehicle", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Invalid Vehicle Id", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/updateVehicle/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateVehicle(@PathVariable ("id") int id, @RequestBody Vehicle vehicle) throws IOException {
        Vehicle findVehicle = vehicleDao.getById(id);
        if(findVehicle != null) {
            findVehicle.setMakeModel(vehicle.getMakeModel());
            findVehicle.setRetailPrice(vehicle.getRetailPrice());
            findVehicle.setYear(vehicle.getYear());

            vehicleDao.update(findVehicle);
            return new ResponseEntity<>("Updated Vehicle", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Invalid vehicle id", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException {
        return vehicleDao.getLatest();
    }

}
