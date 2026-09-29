package sh.finnean.AnimalShelter.manager;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;

public class CsvManager {

    private final AnimalManager animalManager;

    public CsvManager(AnimalManager animalManager) {
        this.animalManager = animalManager;
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

    public void ingestFile(String fileNameToIngest) {
        List<Dog> dogs = new ArrayList<>();
        List<Cat> cats = new ArrayList<>();
        List<Horse> horses = new ArrayList<>();
        List<Bird> birds = new ArrayList<>();

        try {
            List<String[]> rows = readCsv(fileNameToIngest);


        } catch (CsvValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}