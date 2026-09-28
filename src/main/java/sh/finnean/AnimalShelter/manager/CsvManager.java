package sh.finnean.AnimalShelter.manager;

public class CsvManager {

    private final AnimalManager animalManager;

    public CsvManager(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    public void loadInitialData() throws Error {
        System.out.println("Loaded initial data.");
    }
}
