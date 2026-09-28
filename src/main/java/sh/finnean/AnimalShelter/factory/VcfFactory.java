package sh.finnean.AnimalShelter.factory;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.manager.AnimalManager;

import java.util.List;

public class VcfFactory {

    private final AnimalManager animalManager;

    public VcfFactory(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    public void createContacts() {
        List<? extends Animal> animals = animalManager.getAllAnimals();
    }
}
