package sh.finnean.AnimalShelter;

import sh.finnean.AnimalShelter.factory.VcfFactory;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.manager.CsvManager;
import sh.finnean.AnimalShelter.manager.OwnerManager;
import sh.finnean.AnimalShelter.menus.AnimalManagementMenu;
import sh.finnean.AnimalShelter.menus.DataIngestionMenu;
import sh.finnean.AnimalShelter.menus.MainMenu;
import sh.finnean.AnimalShelter.menus.OwnerManagementMenu;

import java.util.Scanner;

public class AnimalShelter {

    // first thing to do when program starts is to load the csv into the system. Csv manager should handle the parsing and
    // loading of data into lists based on animal type. After that, it will call animal manager to import those lists into the main
    // program broad animal container. Vcf factory gets access to animal manager and is called when the option to create the contact
    // cards is selected within the menus.
    private static final Scanner scanner = new Scanner(System.in);
    private static final AnimalManager animalManager = new AnimalManager();
    private static final VcfFactory vcfFactory = new VcfFactory(animalManager);
    private static final CsvManager csvManager = new CsvManager(animalManager);
    private static final OwnerManager ownerManager = new OwnerManager();

    public static void main(String[] args) {

        final OwnerManagementMenu ownerManagementMenu = new OwnerManagementMenu("Owner MGMT", scanner, ownerManager, animalManager);
        final DataIngestionMenu dataIngestionMenu = new DataIngestionMenu("Data Ingestion", scanner,csvManager);
        final AnimalManagementMenu animalManagementMenu = new AnimalManagementMenu("Animal MGMT", scanner,animalManager);

        // start main console menu
        MainMenu mainMenu = new MainMenu("Main Menu", scanner, ownerManagementMenu, dataIngestionMenu, animalManagementMenu);
        mainMenu.run();
    }

}
