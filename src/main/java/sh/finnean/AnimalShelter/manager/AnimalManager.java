package sh.finnean.AnimalShelter.manager;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.Owner;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalManager {

    private List<Animal> animals;

    public AnimalManager() {
        this.animals = new ArrayList<>();
    }

    // allows input of List<Dog>, List<Cat> etc. Then we can use instanceof later to check for specific subclass
    public void addAnimals(List<? extends Animal> newAnimals) {
        animals.addAll(newAnimals);
    }
    // we dont technically need the wildcard for this method sig as animals list only contains instances of the super class
    public List<? extends Animal> getAllAnimals() { return this.animals; }

    public Animal getAnimal(long id) throws InstanceNotFoundException {
        for (Animal a : this.animals) {
            if (a.id() == id) {
                return a;
            }
        }
        throw new InstanceNotFoundException("Could not find an animal with the provided id.");
    }

    public void addAnimal(Animal newAnimal) throws IllegalArgumentException {
        if (newAnimal == null) throw new IllegalArgumentException("You must provide an animal to add.");
        animals.add(newAnimal);
    }

    public Optional<List<Animal>> getOwnersAdoptions(Owner owner) {

        List<Animal> result = new ArrayList<>();
        for (Animal a : this.animals) {
            // we need to check if animal has a non-null owner, if an animal obj has a null owner and we dont check
            // we will get a null pointer exception, we can just skip the animals here that have a null owner
            if (a.getOwner() == null) continue;
            if (a.getOwner().id() == owner.id()) {
                result.add(a);
            }
        }

        if (result.isEmpty()) return Optional.empty();
        return Optional.of(result);
    }


}
