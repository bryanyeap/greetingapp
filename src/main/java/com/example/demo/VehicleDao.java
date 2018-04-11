package com.example.restapiwithjpa;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VehicleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Vehicle create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return entityManager.find(Vehicle.class, vehicle.getId());
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public void delete(Vehicle vehicle) {
        entityManager.remove(vehicle);
    }

    public void update(Vehicle vehicle) {
        entityManager.merge(vehicle);
    }

    public List<Vehicle> getLatest() {
        Query query = entityManager.createNativeQuery("SELECT v.id, v.makeModel, v.year, v.retailPrice FROM vehicles v",
                Vehicle.class);

        List<Vehicle> vehicleList = query.getResultList();
        List<Vehicle> newList = new ArrayList<>();
        int size = 0;
        if(vehicleList.size() < 10) {
            size = 0;
        }
        else {
             size = vehicleList.size() - 10;
        }
        for(int i = vehicleList.size() - 1; i >= size; i--) {
            newList.add(vehicleList.get(i));
        }
        return newList;
    }
}
