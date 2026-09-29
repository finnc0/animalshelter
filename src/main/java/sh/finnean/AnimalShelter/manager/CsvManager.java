package sh.finnean.AnimalShelter.manager;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CsvManager {

    private final AnimalManager animalManager;
    private final OwnerManager ownerManager;

    public CsvManager(AnimalManager animalManager, OwnerManager ownerManager) {
        this.animalManager = animalManager;
        this.ownerManager = ownerManager;
    }

    public List<String> getIngestionFileNames() throws IOException {
        // get data dir where csv files must be stored from shelter util and create path obj from uri
        // must be absolute otherwise it will go to root of filesystem
        Path dataPath = Path.of(ShelterUtil.getDataDirURI()).toAbsolutePath();

        // create predicates for filtering the csv files we want
        Predicate<Path> IS_FILE = Files::isRegularFile;
        Predicate<Path> IS_CSV_FILE = path -> path.getFileName().toString().toLowerCase().endsWith(".csv");
        Predicate<Path> IS_VALID_FILE = IS_FILE.and(IS_CSV_FILE);

        // try and access dir and list files, if this fails, IOExcep. gets thrown.
        try {
            Stream<Path> files = Files.list(dataPath);
            List<Path> filteredFiles = files.filter(IS_VALID_FILE).toList();

            // return the file names not the stream of paths;
            // below maps the path's file name to the path
            return filteredFiles.stream().map(path -> path.getFileName().toString()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> readCsv(String fileNameToIngest) throws CsvValidationException {
        // path looks like $proj_root/data/file.txt
        Path filePath = Path.of(ShelterUtil.getDataDirURI() + "/" + fileNameToIngest).toAbsolutePath();

        List<String[]> rows = new ArrayList<>();

        try {
            CSVReader reader = new CSVReaderBuilder(Files.newBufferedReader(filePath)).withSkipLines(1).build();

            String[] fields;
            // check if the next field the cursor is on is avail to read
            while ((fields = reader.readNext()) != null) {
                rows.add(fields);
            }

        } catch(IOException | CsvValidationException e) {
            throw new CsvValidationException("An error occurred parsing the file. Error: " + e.getMessage());
        };

        return rows;
    }

    private String requireField(String field, String fieldName) throws IllegalArgumentException {
        String result = blankToNull(field);
        if (result == null) throw new IllegalArgumentException("PARSE LOG: Missing field: " + fieldName);
        return result;
    }

    // helper to convert blank csv field to a obj equal to null if it is empty, otherwise it keeps the value
    private String blankToNull(String input) {
        return input.isBlank() ? null : input.trim();
    }

    // helper to parse owner fields as there is some complexity here that can be separated
    private Owner parseOwner(String name, String email, String phone) throws IllegalArgumentException {
        String n = blankToNull(name), e = blankToNull(email), p = blankToNull(phone);

        if (n == null && e == null && p == null) return null; // no owner thats fine
        if (n == null || e == null || p == null) {
            throw new IllegalArgumentException("PARSE LOG: Owner info is incomplete.");
        }
        return new Owner(n, e, p);
    }

    public void ingestFile(String fileNameToIngest) {
        List<Dog> dogs = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        List<Horse> horses = new ArrayList<>();
        List<Bird> birds = new ArrayList<>();

        try {
            List<String[]> rows = readCsv(fileNameToIngest);


            // loop through all rows in csv and create obj for each row
            for (String[] row : rows) {
                try {
                    String type = requireField(row[0], "animal_type");
                    String name = requireField(row[1], "animal_name");
                    String vaccRaw = blankToNull(row[2]);
                    LocalDate vaccDate = vaccRaw == null ? null : LocalDate.parse(vaccRaw);


                    String ownerName = row[3];
                    String ownerEmail = row[4];
                    String ownerPhone = row[5];

                    // parse owner from data
                    Owner owner = parseOwner(ownerName, ownerEmail, ownerPhone);
                    // absolutely need to make sure we add these owners into the owner manager as well
                    if (owner != null) {
                        this.ownerManager.addOwner(owner);
                    }

                    switch (type.toLowerCase()) {
                        case "dog":
                            boolean likesWalks = Boolean.parseBoolean(blankToNull(row[6]));
                            boolean isCrateTrained = Boolean.parseBoolean(blankToNull(row[7]));

                            Dog dog = new Dog(name, vaccDate, owner, likesWalks, isCrateTrained);
                            dogs.add(dog);

                            System.out.println("PARSE LOG: Added dog successfully to local container.");
                            break;
                        case "cat":
                            boolean likesCatNip = Boolean.parseBoolean(blankToNull(row[8]));
                            boolean litterBoxTrained = Boolean.parseBoolean(blankToNull(row[9]));

                            Cat cat = new Cat(name, vaccDate, owner, likesCatNip, litterBoxTrained);
                            cats.add(cat);

                            System.out.println("PARSE LOG: Added cat successfully to local container.");
                            break;
                        case "bird":
                            boolean canTalk = Boolean.parseBoolean(blankToNull(row[10]));
                            boolean canFly = Boolean.parseBoolean(blankToNull(row[11]));

                            Bird bird = new Bird(name, vaccDate, owner, canTalk, canFly);
                            birds.add(bird);

                            System.out.println("PARSE LOG: Added bird successfully to local container.");
                            break;
                        case "horse":
                            boolean isRideable = Boolean.parseBoolean(blankToNull(row[12]));

                            Horse horse = new Horse(name, vaccDate, owner, isRideable);
                            horses.add(horse);

                            System.out.println("PARSE LOG: Added horse successfully to local container.");
                            break;
                        default:
                            System.out.println("PARSE LOG: animal_type not valid.");
                    }
                } catch (IllegalStateException | DateTimeParseException e) {
                    System.out.println("PARSE LOG ERROR: An error occurred parsing row: " + (rows.indexOf(row) + 1) + " SKIPPING! - Error: " + e.getMessage());
                }
            }
            this.animalManager.addAnimals(dogs);
            this.animalManager.addAnimals(cats);
            this.animalManager.addAnimals(birds);
            this.animalManager.addAnimals(horses);

            System.out.println("PARSE LOG: Added all animals to system manager successfully.");
        } catch (CsvValidationException e) {
            System.out.println("An error occurred parsing the CSV. Error: " + e.getMessage());
        }
    }
}