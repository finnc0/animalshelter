package sh.finnean.AnimalShelter;

import sh.finnean.AnimalShelter.factory.VcfFactory;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.manager.CsvManager;
import sh.finnean.AnimalShelter.manager.OwnerManager;
import sh.finnean.AnimalShelter.menus.MainMenu;
import sh.finnean.AnimalShelter.menus.OwnerManagementMenu;

import java.util.Scanner;

public class AnimalShelter {

    // first thing to do when program starts is to load the csv into the system. Csv manager should handle the parsing and
    // loading of data into lists based on animal type. After that, it will call animal manager to import those lists into the main
    // program broad animal container. Vcf factory gets access to animal manager and is called when the option to create the contact
    // cards is selected within the menus.
    private static Scanner scanner = new Scanner(System.in);
    private static AnimalManager animalManager = new AnimalManager();
    private static VcfFactory vcfFactory = new VcfFactory(animalManager);
    private static CsvManager csvManager = new CsvManager(animalManager);
    private static OwnerManager ownerManager = new OwnerManager();

    public static void main(String[] args) {
        // load in data from csv.
        csvManager.loadInitialData();

        // start main console menu
        OwnerManagementMenu ownerManagementMenu = new OwnerManagementMenu("Owner MGMT", scanner, ownerManager, animalManager);

        MainMenu mainMenu = new MainMenu("Main Menu", scanner, ownerManagementMenu);
        mainMenu.run();
    }

}
