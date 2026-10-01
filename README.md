# Animal Shelter

**Author:** Finn Carmichael ([finnc0](https://github.com/finnc0))

**Course:** CSCI 221 – Individual Animal Shelter Project (custom scope)

**Standards:** I do my best to follow this ([commit standard] (https://www.conventionalcommits.org/en/v1.0.0))

A console-based animal shelter management system built in Java. It supports managing animals and owners, handling adoptions, importing animals from CSV files, and exporting animals as vCard contacts.

The goal of this project was to apply core object-oriented principles to a production standard, to the best of my abilities, including encapsulation, abstraction, inheritance, polymorphism, and composition, along with others.

## Dependencies

- **OpenCSV** – Parsing CSV files for data import
- **ez-vcard** – Generating vCard (.vcf) contact files

## Project Structure

- `data/` – CSV files available for import
    - `animals.csv` – Dummy animal data (dogs, cats, birds, horses, with optional owners)
    - `test.csv` – Test file for the import feature
    - `contacts/` – Output folder for generated vCard (.vcf) files
- `src/main/java/sh/finnean/AnimalShelter/` – All source code
    - `AnimalShelter.java` – Entry point; wires up managers and menus and starts the main menu
- `pom.xml` – Maven build config (Java 23) and dependencies
- `.gitignore` – Files excluded from Git

## Source Packages

**contracts/**
- `Adoptable.java` – Adopt/unadopt behavior for animals that can be adopted
- `Behavior.java` – Whether an animal is suitable for a family
- `Displayable.java` – Common `displayInfo()` method for printing details

**factory/**
- `VcfFactory.java` – Generates vCard (.vcf) contacts for animals, including owner email and animal-specific details

**instance/**
- `Animal.java` – Abstract base class for all animals (ID, name, vaccination date, owner)
- `AnimalType.java` – Enum of supported animal types (dog, cat, bird, horse)
- `Owner.java` – An adopter, with name, email, and phone number
- `Menu.java` – Abstract base menu that handles the display/input loop
- `animal/` – Concrete animal subclasses: `Bird`, `Cat`, `Dog`, `Horse`, each with their own specific fields
- `animalmodmenu/` – `EditableField` and `FieldType`, used to describe each animal's editable fields for the modification menu

**manager/**
- `AnimalManager.java` – Stores animals and handles lookup, adding, and removal
- `OwnerManager.java` – Stores owners, indexed by email
- `CsvManager.java` – Finds CSV files in `data/` and imports them into the managers

**menus/**
- `MainMenu.java` – Top-level menu linking to all other menus
- `OwnerManagementMenu.java` – Add, remove, and view owners
- `AdoptionMenu.java` – Adopt and unadopt animals
- `DataIngestionMenu.java` – Search for and import CSV files
- `animal/AnimalManagementMenu.java` – Add, remove, view, and filter animals, and export contacts
- `animal/AnimalModificationMenu.java` – Edit an animal's fields, built dynamically from its editable fields

**utils/**
- `ShelterUtil.java` – Input prompt helpers (validated strings, booleans, numbers, dates) and data directory lookup

## Running

Build and run with Maven, or open the project in IntelliJ and run `AnimalShelter.java`. CSV files placed in `data/` will show up in the Data Ingestion menu.

`animals.csv` can be imported as dummy data to try out the app. To add more or real data, create a new CSV in `data/` that follows the same column structure as `animals.csv`.

Contacts generated from the Animal Management menu are saved to `data/contacts/`.
