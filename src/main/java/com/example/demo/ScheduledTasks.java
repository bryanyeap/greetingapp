package com.example.demo;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    RestTemplate restTemplate = new RestTemplate();

    //@Scheduled(cron = "*/10 * * * * *")
    public Greeting createGreeting() {
        Greeting g = new Greeting(0, RandomStringUtils.randomAlphabetic(10));
        String postUrl = "http://localhost:8080/createGreeting";
        restTemplate.postForObject(postUrl, g, Greeting.class);
        return g;
    }

    //@Scheduled(cron = "*/30 * * * * *")
    public Greeting getGreeting() {
        int id = RandomUtils.nextInt(0, 100);
        String getUrl = "http://localhost:8080/getGreeting/" + id;
        Greeting g = restTemplate.getForObject(getUrl, Greeting.class);
        System.out.println(g.getContent());
        return g;
    }


    @Scheduled (fixedRate = 5000)
    public void periodicTask1() {
        System.out.println("The time now is " + new Date());
    }



    //@Scheduled(cron = "*/45 * * * * *")
    public void updateGreeting() {
        String putURL = "http://localhost:8080/updateGreeting";
        if (getGreeting().getContent().equals("bye")) {
            restTemplate.put(putURL, "hello");
        } else {
            restTemplate.put(putURL, "bye");
        }
        Greeting g = restTemplate.getForObject("http://localhost:8080/greeting", Greeting.class);
        System.out.println(g.getContent());
    }

    public int id = 1;
    public int row = 1;

    //@Scheduled(fixedRate = 1500)
    public void automatedPost() {
        String[] makeModels = {"Toyota Camry", "Honda Civic", "Nissan Altima", "Nissan Sentra", "Ford Mustang", "Honda Accord"};
        Integer[] years = {1990, 2001, 2018, 2077, 1964, 2000};
        Double[] retail = {20161.20, 10930.00, 8399.99, 25000.00, 3600.99, 63212.00};
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/addVehicle";
        Random rand = new Random();
        Vehicle vehicle = new Vehicle(
                makeModels[rand.nextInt(6)], years[rand.nextInt(6)], retail[rand.nextInt(6)]);
        restTemplate.postForObject(url, vehicle, Vehicle.class);
    }

    //@Scheduled(fixedRate = 2500)
    public void automatedPut() {
        String[] makeModels = {"Toyota Camry", "Honda Civic", "Nissan Altima", "Nissan Sentra", "Ford Mustang", "Honda Accord"};
        Integer[] years = {1990, 2001, 2018, 2077, 1964, 2000};
        Double[] retail = {20161.20, 10930.00, 8399.99, 25000.00, 3600.99, 63212.00};

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/updateVehicle/" + id;
        Random rand = new Random();
        Vehicle vehicle = new Vehicle(
                makeModels[rand.nextInt(6)], years[rand.nextInt(6)], retail[rand.nextInt(6)]);
        restTemplate.put(url, vehicle, Vehicle.class);
        id++;
    }

    //@Scheduled(fixedRate = 5000)
    public void automatedDelete() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/deleteVehicle/" + row;
        restTemplate.delete(url, Vehicle.class);
        row++;
    }

    //@Scheduled(fixedRate = 5000)
    public void automatedLatest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/getLatestVehicles";
        List<Vehicle> vehicles = restTemplate.getForObject(url, List.class);
        System.out.println(vehicles.get(0));
    }


}
