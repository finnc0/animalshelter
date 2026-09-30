# Animal Shelter Management System

## Overview

Animal Shelter is a Java 23 console application that models the core operations of an animal shelter. The application is organized around object oriented programming principles and separates responsibilities into domain objects, contracts, managers, factories, menus, and utility classes.

The system currently supports:

* Managing animal records
* Managing shelter owners
* Associating animals with owners
* Determining adoption eligibility
* Calculating animal specific adoption fees
* Determining whether an animal is considered suitable for a family
* Importing animal and owner records from CSV files
* Displaying animal and owner information through console menus
* Searching the local data directory for CSV files
* Preparing an extension point for generating VCF contact records

The application is intentionally structured so that the main program is responsible for assembling the application while individual classes handle their own areas of responsibility.

## Technology Stack

| Technology | Purpose |
|─-|─-|
| Java 23 | Application language and runtime target |
| Maven | Project and dependency management |
| OpenCSV 5.12.0 | CSV file parsing |
| Java Collections Framework | In memory storage of animals and owners |
| `java.time.LocalDate` | Vaccination date representation |
| `UUID` | Owner identifiers |
| `SecureRandom` | Animal identifiers |

## Project Structure

```text
animalshelter/
|
+─ data/
|   +─ animals.csv
|   +─ test.csv
|
+─ src/
|   +─ main/
|       +─ java/
|           +─ sh/
|               +─ finnean/
|                   +─ AnimalShelter/
|                       +─ AnimalShelter.java
|                       |
|                       +─ contracts/
|                       |   +─ Adoptable.java
|                       |   +─ Behavior.java
|                       |   +─ Displayable.java
|                       |
|                       +─ factory/
|                       |   +─ VcfFactory.java
|                       |
|                       +─ instance/
|                       |   +─ Animal.java
|                       |   +─ Menu.java
|                       |   +─ Owner.java
|                       |   |
|                       |   +─ animal/
|                       |       +─ Bird.java
|                       |       +─ Cat.java
|                       |       +─ Dog.java
|                       |       +─ Horse.java
|                       |
|                       +─ manager/
|                       |   +─ AnimalManager.java
|                       |   +─ CsvManager.java
|                       |   +─ OwnerManager.java
|                       |
|                       +─ menus/
|                       |   +─ AnimalManagementMenu.java
|                       |   +─ DataIngestionMenu.java
|                       |   +─ MainMenu.java
|                       |   +─ OwnerManagementMenu.java
|                       |
|                       +─ utils/
|                           +─ ShelterUtil.java
|
+─ pom.xml
+─ .gitignore
```

## Architectural Hierarchy

The application can be understood as six major layers.

```text
AnimalShelter
|
+─ Application Composition
|   |
|   +─ Scanner
|   +─ AnimalManager
|   +─ OwnerManager
|   +─ CsvManager
|   +─ VcfFactory
|   +─ MainMenu
|
+─ User Interface
|   |
|   +─ Menu
|       |
|       +─ MainMenu
|       +─ AnimalManagementMenu
|       +─ OwnerManagementMenu
|       +─ DataIngestionMenu
|
+─ Management
|   |
|   +─ AnimalManager
|   +─ OwnerManager
|   +─ CsvManager
|
+─ Domain Objects
|   |
|   +─ Animal
|   |   |
|   |   +─ Dog
|   |   +─ Cat
|   |   +─ Bird
|   |   +─ Horse
|   |
|   +─ Owner
|
+─ Contracts
|   |
|   +─ Adoptable
|   +─ Behavior
|   +─ Displayable
|
+─ Supporting Components
    |
    +─ VcfFactory
    +─ ShelterUtil
```

## Object Oriented Class Hierarchy

The central inheritance relationship is based around the abstract `Animal` class.

```text
                    Animal
                      |
          +─────-+─────-+─────-+
          |           |           |           |
         Dog         Cat         Bird       Horse
```

`Animal` defines the properties common to every animal in the shelter:

* ID
* Name
* Vaccination date
* Owner
* Adoption fee contract

Each concrete animal class adds its own properties and behavior.

### Animal

`Animal` is an abstract class and therefore cannot be instantiated directly.

It contains:

```java
private final long id;
private String name;
private LocalDate vaccDate;
private Owner owner;
```

The constructor generates an identifier and initializes the common animal state.

Every subclass must implement:

```java
public abstract double getAdoptionFee();
```

This is an important example of abstraction. The base class establishes that every animal must have an adoption fee, while each animal type determines how that fee is calculated.

### Dog

`Dog` extends `Animal` and implements:

* `Adoptable`
* `Behavior`
* `Displayable`

Dog specific properties include:

```text
likesWalks
crateTrained
isAdopted
```

The adoption fee starts with the dog base fee and can be increased when:

* The dog has no vaccination date
* The dog likes walks

A dog is considered suitable for a family when:

```text
likes walks
AND
crate trained
AND
vaccinated
```

### Cat

`Cat` extends `Animal` and implements:

* `Adoptable`
* `Behavior`
* `Displayable`

Cat specific properties include:

```text
likesCatNip
litterBoxTrained
isAdopted
```

The adoption fee starts with the cat base fee and can be increased when:

* The cat has no vaccination date
* The cat likes catnip

A cat is considered suitable for a family when:

```text
likes catnip
AND
vaccinated
```

### Bird

`Bird` extends `Animal` and implements:

* `Adoptable`
* `Behavior`
* `Displayable`

Bird specific properties include:

```text
canTalk
canFly
isAdopted
```

The adoption fee starts with the bird base fee and can be increased when:

* The bird can both talk and fly
* The bird has no vaccination date

A bird is considered suitable for a family when:

```text
can talk
AND
cannot fly
```

### Horse

`Horse` extends `Animal` and implements:

* `Adoptable`
* `Behavior`
* `Displayable`

Horse specific properties include:

```text
isRideable
isAdopted
```

The current horse adoption fee returns the horse base fee without additional modifiers.

A horse is considered suitable for a family when:

```text
rideable
```

## Interface Hierarchy

The project uses interfaces to define capabilities that can be shared by multiple classes.

```text
Adoptable
|
+─ Dog
+─ Cat
+─ Bird
+─ Horse

Behavior
|
+─ Dog
+─ Cat
+─ Bird
+─ Horse

Displayable
|
+─ Owner
+─ Dog
+─ Cat
+─ Bird
+─ Horse
```

### Adoptable

The `Adoptable` interface defines the operations required for an animal to participate in the adoption system.

```java
void adopt(Owner owner);
void setAdoptable(boolean adoptionValue);
boolean isAdoptable();
```

This allows the concrete animal classes to implement adoption behavior independently while exposing the same public contract.

The current implementation uses an internal `isAdopted` field in each adoptable animal. The public `isAdoptable()` method returns the inverse of that state.

### Behavior

The `Behavior` interface defines:

```java
boolean isSuitableForFamily();
```

Each animal determines suitability using its own characteristics.

This is an example of polymorphism. Code can work with the common `Behavior` contract without needing to know the specific animal implementation.

### Displayable

The `Displayable` interface defines:

```java
void displayInfo();
```

Both owners and animals can therefore expose a standard method for displaying their information.

## Manager Layer

Managers are responsible for maintaining collections and providing operations against those collections.

```text
                 Management Layer
                       |
          +──────+──────+
          |                         |
    AnimalManager              OwnerManager
          |
     Animal records

    CsvManager
          |
          +─ AnimalManager
          +─ OwnerManager
```

### AnimalManager

`AnimalManager` owns the application's primary animal collection.

```java
private List<Animal> animals;
```

The manager provides operations for:

* Adding one animal
* Adding multiple animals
* Retrieving all animals
* Finding an animal by ID
* Finding all animals belonging to an owner

The generic method:

```java
public void addAnimals(List<? extends Animal> newAnimals)
```

allows lists containing concrete subclasses such as:

```text
List<Dog>
List<Cat>
List<Bird>
List<Horse>
```

to be added to the common `List<Animal>` collection.

This is a useful example of bounded wildcards in Java generics.

### OwnerManager

`OwnerManager` maintains owners using a `HashMap`.

```java
private Map<String, Owner> animalOwners;
```

The owner's email address is used as the map key.

Conceptually:

```text
email
  |
  v
Owner object
```

This allows the application to locate an owner by email without iterating through the entire owner collection.

`OwnerManager` provides:

* Add owner
* Find owner by email
* Remove owner
* Retrieve all owners

It also prevents duplicate email addresses from being added.

### CsvManager

`CsvManager` is responsible for converting external CSV data into application objects.

It communicates with both managers:

```text
CsvManager
   |
   +───> OwnerManager
   |
   +───> AnimalManager
```

Its responsibilities include:

1. Finding CSV files in the data directory
2. Reading CSV rows
3. Validating required fields
4. Converting blank values to `null`
5. Parsing vaccination dates
6. Creating `Owner` objects
7. Creating concrete animal objects
8. Adding owners to `OwnerManager`
9. Adding animals to `AnimalManager`

The CSV parser uses OpenCSV.

## Factory Layer

### VcfFactory

`VcfFactory` is intended to provide functionality for generating VCF contact records for shelter animals.

Its constructor receives an `AnimalManager`:

```java
public VcfFactory(AnimalManager animalManager)
```

This establishes the relationship:

```text
VcfFactory
    |
    v
AnimalManager
    |
    v
List<Animal>
```

The current `createContacts()` method retrieves the animal collection but does not yet generate or write VCF files.

This makes the factory an extension point rather than a completed subsystem.

## Menu Layer

The console user interface is built around the abstract `Menu` class.

```text
                     Menu
                       |
          +──────+──────-+────────+
          |            |             |                |
      MainMenu    OwnerManagement  AnimalManagement  DataIngestion
```

### Menu

`Menu` provides the common execution loop for all menus.

The basic flow is:

```text
run()
 |
 +─ Display menu title
 |
 +─ Display options
 |
 +─ Read user input
 |
 +─ Parse integer choice
 |
 +─ Call handleChoice()
 |
 +─ Continue or exit
```

The abstract methods are:

```java
protected abstract void printOptions();
protected abstract boolean handleChoice(int choice);
```

This means each menu controls its own choices while sharing the same execution framework.

### MainMenu

`MainMenu` is the top level navigation menu.

Current options include:

```text
1. Owner Management Menu
2. Initial Data Import Menu
3. Animal Management Menu
4. Adoption Menu
5. Quit
```

The first three options are currently connected to their corresponding menus.

The adoption menu option is displayed but does not currently have an implemented handler.

### OwnerManagementMenu

This menu handles owner operations:

```text
1. Add new owner
2. Remove owner
3. View owner
4. View all owners
5. Back
```

It communicates with:

```text
OwnerManagementMenu
       |
       +───> OwnerManager
       |
       +───> AnimalManager
```

The `AnimalManager` dependency is important when removing an owner because the application checks whether that owner currently has adopted animals.

An owner with existing adoptions cannot currently be removed.

### AnimalManagementMenu

This menu is intended to manage animal records.

The displayed options include:

```text
1. Add an animal
2. Remove an animal
3. Get a specific animal
4. Get all animals
5. Get all adopted animals
6. Get all unadopted animals
7. Modify an animal
8. Create contacts for all animals
8. Back
```

The current implementation has active functionality for:

* Adding an animal placeholder
* Displaying all animals
* Returning to the previous menu

Several other menu options are currently placeholders.

There is also a duplicate option number in the displayed menu where both contact creation and Back are labeled `8`. This should be corrected as the menu is expanded.

### DataIngestionMenu

This menu controls CSV import.

```text
1. Search for CSV files to import
2. Back
```

The menu delegates file discovery and ingestion to `CsvManager`.

The interaction is:

```text
DataIngestionMenu
       |
       v
CsvManager
       |
       +─ Search data directory
       |
       +─ Find CSV files
       |
       +─ Read selected file
       |
       +─ Create owners
       |
       +─ Create animals
       |
       +─ Store objects in managers
```

## Program Startup Flow

The application begins in:

```text
AnimalShelter.main()
```

The main class creates the shared application components.

```text
AnimalShelter
 |
 +─ Scanner
 |
 +─ AnimalManager
 |
 +─ OwnerManager
 |
 +─ CsvManager
 |      |
 |      +─ AnimalManager
 |      +─ OwnerManager
 |
 +─ VcfFactory
 |      |
 |      +─ AnimalManager
 |
 +─ OwnerManagementMenu
 |
 +─ DataIngestionMenu
 |
 +─ AnimalManagementMenu
 |
 +─ MainMenu
```

After construction, the application calls:

```java
mainMenu.run();
```

The main menu then controls the rest of the console interaction.

## CSV Data Flow

The supplied `animals.csv` file uses the following structure:

```text
type
name
vaccDate
ownerName
ownerEmail
ownerPhone
likesWalks
crateTrained
likesCatNip
litterBoxTrained
canTalk
canFly
isRideable
```

The parser interprets different columns depending on the animal type.

### Dog

```text
type
name
vaccDate
owner information
likesWalks
crateTrained
```

### Cat

```text
type
name
vaccDate
owner information
likesCatNip
litterBoxTrained
```

### Bird

```text
type
name
vaccDate
owner information
canTalk
canFly
```

### Horse

```text
type
name
vaccDate
owner information
isRideable
```

The import process is:

```text
CSV file
   |
   v
CSVReader
   |
   v
String[] row
   |
   +─ Validate common fields
   |
   +─ Parse owner
   |
   +─ Determine animal type
   |
   +─ Construct concrete animal
   |
   +─ Store temporary typed list
   |
   v
AnimalManager
```

## Adoption Model

Adoption behavior is implemented separately by each concrete animal class through the `Adoptable` interface.

The conceptual process is:

```text
Owner selected
     |
     v
Animal selected
     |
     v
Check owner exists
     |
     v
Check animal is available
     |
     v
Set animal owner
     |
     v
Mark animal as adopted
```

An animal is considered initially adopted when its constructor receives a nonnull owner.

For example:

```java
this.isAdopted = owner != null;
```

The owner is stored directly on the animal:

```java
private Owner owner;
```

This means the current domain model treats the animal as the object that maintains its owner relationship.

## Adoption Fee Calculation

Adoption fees are calculated polymorphically.

The abstract `Animal` class requires every subclass to implement:

```java
getAdoptionFee()
```

The base fees are centralized in `ShelterUtil`.

```text
Dog      120.99
Cat       59.66
Bird      25.00
Horse    439.99
```

The concrete classes can then apply additional conditions.

### Dog Fee

```text
Base dog fee
+
100 if vaccination date is missing
+
25 if the dog likes walks
```

### Cat Fee

```text
Base cat fee
+
89 if vaccination date is missing
+
55 if the cat likes catnip
```

### Bird Fee

```text
Base bird fee
+
220 if the bird can talk and fly
+
80 if vaccination date is missing
```

### Horse Fee

```text
Base horse fee
```

This structure demonstrates polymorphism because the caller can treat all animals as `Animal` objects while the actual `getAdoptionFee()` implementation is selected according to the concrete runtime type.

## Owner Relationship

Owners are represented by the `Owner` class.

Each owner receives a generated UUID:

```java
private final UUID id;
```

Owners also contain:

```text
name
email
phoneNumber
```

`OwnerManager` indexes owners by email:

```text
HashMap<String, Owner>
```

The email therefore acts as the lookup key within the current application.

When viewing an owner, the application can also query `AnimalManager` for animals whose owner ID matches the selected owner's ID.

```text
OwnerManager
     |
     +─ Owner
           ^
           |
Animal
     |
     +─ Owner reference
```

## Use of Generics

The project makes use of Java generic collections throughout the management layer.

The animal manager stores:

```java
List<Animal>
```

while the CSV manager can temporarily store:

```java
List<Dog>
List<Cat>
List<Bird>
List<Horse>
```

The method:

```java
addAnimals(List<? extends Animal> newAnimals)
```

accepts any list whose element type is `Animal` or a subclass of `Animal`.

This allows the CSV manager to construct strongly typed lists before combining them into the central animal collection.

## Polymorphism

Polymorphism appears in several places.

### Adoption Fees

```java
Animal animal = new Dog(...);
animal.getAdoptionFee();
```

The method implementation from `Dog` is used.

### Family Suitability

Every concrete animal implements:

```java
isSuitableForFamily()
```

but the rules differ by animal type.

### Displaying Information

Each concrete animal implements:

```java
displayInfo();
```

The menu layer can therefore work with an `Animal` reference and determine which concrete implementation should display the information.

The current menu also uses Java pattern matching for `switch`:

```text
Animal
 |
 +─ Dog
 +─ Cat
 +─ Horse
 +─ Bird
```

This allows the program to access subclass specific behavior when necessary.

## Encapsulation

The project uses private fields throughout its domain objects.

For example:

```java
private String name;
private LocalDate vaccDate;
private Owner owner;
```

Access is provided through methods such as:

```java
getName()
getVaccDate()
getOwner()
setName()
setVaccDate()
setOwner()
```

This prevents external classes from directly manipulating the object's internal state.

The same principle is used for managers:

```java
private List<Animal> animals;
private Map<String, Owner> animalOwners;
```

Consumers interact with the managers through public methods instead of directly accessing the underlying collections.

## Abstraction

The project uses both abstract classes and interfaces.

### Abstract Class

`Animal` defines shared state and behavior for every animal.

```text
Animal
 |
 +─ Shared fields
 +─ Shared getters and setters
 +─ Abstract adoption fee
```

### Interfaces

The interfaces define capabilities:

```text
Adoptable
Behavior
Displayable
```

This separates what an object can do from the specific implementation of how it does it.

## Exception Handling

The application uses exceptions for invalid operations and parsing problems.

Examples include:

```text
IllegalArgumentException
IllegalStateException
IllegalCallerException
InstanceNotFoundException
DateTimeParseException
CsvValidationException
IOException
NumberFormatException
IndexOutOfBoundsException
```

Examples of validation include:

* Preventing duplicate owner emails
* Rejecting missing required CSV fields
* Rejecting incomplete owner information
* Rejecting adoption when an animal is already adopted
* Rejecting adoption when an owner cannot be found
* Handling invalid menu input
* Handling malformed dates
* Handling file system errors

## Utility Layer

`ShelterUtil` contains values and utility accessors that are shared across the application.

Currently it provides:

```text
Dog adoption base fee
Cat adoption base fee
Bird adoption base fee
Horse adoption base fee
Data directory path
```

Centralizing these values avoids repeating the same constants throughout multiple animal classes.

The current data directory is:

```text
data
```

## Data Storage Model

The application currently uses in memory collections rather than a database.

```text
AnimalManager
    |
    +─ List<Animal>

OwnerManager
    |
    +─ Map<String, Owner>
```

CSV files provide persistent input data, but modifications made during program execution are not currently written back to the CSV files.

This means restarting the application clears the in memory state unless the data is imported again.

## Current Application Flow

A typical session can be represented as:

```text
Start Program
     |
     v
AnimalShelter.main()
     |
     v
Create managers and menus
     |
     v
MainMenu.run()
     |
     +──────────────-+
     |                             |
     v                             v
Owner Management             Data Ingestion
     |                             |
     v                             v
OwnerManager                 CsvManager
     |                             |
     |                       +──-+──-+
     |                       |           |
     |                       v           v
     |                  OwnerManager  AnimalManager
     |                                   |
     +─────────────────-+
                                         |
                                         v
                                Animal Management
                                         |
                                         v
                                  Display Animals
```

## Design Responsibilities

| Component | Primary Responsibility |
|─-|─-|
| `AnimalShelter` | Application startup and dependency construction |
| `Animal` | Shared animal state and abstract animal behavior |
| `Dog` | Dog specific state and behavior |
| `Cat` | Cat specific state and behavior |
| `Bird` | Bird specific state and behavior |
| `Horse` | Horse specific state and behavior |
| `Owner` | Owner identity and owner information |
| `Menu` | Shared console menu execution loop |
| `MainMenu` | Top level navigation |
| `AnimalManagementMenu` | Animal related user interaction |
| `OwnerManagementMenu` | Owner related user interaction |
| `DataIngestionMenu` | CSV import interaction |
| `AnimalManager` | Animal collection and animal queries |
| `OwnerManager` | Owner collection and owner lookup |
| `CsvManager` | CSV discovery, parsing, validation, and object creation |
| `VcfFactory` | Intended VCF contact generation |
| `ShelterUtil` | Shared configuration and adoption fee constants |
| `Adoptable` | Adoption capability contract |
| `Behavior` | Family suitability contract |
| `Displayable` | Console display contract |

## Dependency Relationships

The major dependencies can be summarized as follows:

```text
AnimalShelter
 |
 +─ MainMenu
 |     |
 |     +─ OwnerManagementMenu
 |     +─ DataIngestionMenu
 |     +─ AnimalManagementMenu
 |
 +─ AnimalManager
 |
 +─ OwnerManager
 |
 +─ CsvManager
 |     |
 |     +─ AnimalManager
 |     +─ OwnerManager
 |
 +─ VcfFactory
       |
       +─ AnimalManager
```

The menu layer depends on manager classes rather than directly manipulating the underlying collections.

The CSV layer depends on managers because its job is to convert external data into application state.

The animal subclasses depend on utility values for their base adoption fees.

## Why the Hierarchy Is Structured This Way

The architecture separates responsibilities so that different parts of the program can evolve independently.

For example:

* Changing the CSV format primarily affects `CsvManager`
* Changing owner lookup behavior primarily affects `OwnerManager`
* Changing animal storage primarily affects `AnimalManager`
* Adding a new animal type primarily requires a new `Animal` subclass and related CSV handling
* Changing console navigation primarily affects the menu classes
* Changing adoption fee constants primarily affects `ShelterUtil`
* Adding VCF generation primarily affects `VcfFactory`

This reduces the amount of application logic that needs to be changed when a single feature evolves.

## Adding a New Animal Type

A new animal type should generally follow the existing inheritance pattern.

For example, adding `Rabbit` would involve:

```text
Animal
   |
   +─ Rabbit
```

The new class would extend `Animal` and implement the appropriate interfaces.

For example:

```java
public class Rabbit extends Animal
        implements Adoptable, Behavior, Displayable {
}
```

The class would then implement:

```text
getAdoptionFee()
adopt()
setAdoptable()
isAdoptable()
isSuitableForFamily()
displayInfo()
```

The CSV ingestion logic would also need a new branch for the new animal type.

The manager layer would not need a new manager because `AnimalManager` already stores the abstract `Animal` type.

This is one of the primary benefits of the existing inheritance design.

## Running the Application

The project uses Maven and requires Java 23.

### Verify Java

```bash
java ─version
```

The project targets Java 23.

### Compile

```bash
mvn clean compile
```

### Package

```bash
mvn clean package
```

### Run From an IDE

Run:

```text
sh.finnean.AnimalShelter.AnimalShelter
```

as the application's main class.

The program expects the `data` directory to be available relative to the project working directory.

## Sample Data

The repository contains:

```text
data/animals.csv
```

The sample file contains records for:

```text
Dogs
Cats
Birds
Horses
```

Some records contain owners while others do not. This allows the application to represent both currently owned and unowned animals.

## Current Implementation Status

The project contains several implemented foundations as well as features that are still under development.

### Implemented

* Animal inheritance hierarchy
* Dog, Cat, Bird, and Horse implementations
* Adoption interfaces
* Family suitability behavior
* Animal specific adoption fee calculations
* Owner management
* Owner lookup by email
* Owner deletion protection when adoptions exist
* Animal collection management
* CSV discovery
* CSV parsing
* Owner creation from CSV data
* Animal creation from CSV data
* Console menu framework
* Animal display
* Owner display

### Partially Implemented

* Animal management menu
* Adoption workflow
* VCF contact generation
* Animal modification
* Animal removal
* Filtering adopted animals
* Filtering unadopted animals
* Specific animal lookup from the menu

### Known Implementation Details

The current `MainMenu` displays an adoption menu option, but that option does not currently have a corresponding implementation in `handleChoice()`.

`AnimalManagementMenu` displays several operations that are not yet implemented.

`VcfFactory.createContacts()` currently retrieves the animals from `AnimalManager` but does not yet create VCF contact files.

The animal management menu currently contains two entries numbered `8`. One is intended for contact generation and the other is intended to return to the previous menu.

The CSV import process currently creates a new `Owner` object for every owner record and attempts to add it to `OwnerManager`. The owner manager enforces unique email addresses.

The application currently keeps data in memory after ingestion. There is no database layer or write back operation for modified records.

## Design Patterns and Principles Present

The project contains several recognizable object oriented design patterns and principles.

### Factory Pattern

`VcfFactory` is structured as a factory component intended to create VCF contact artifacts from animal data.

### Template Style Menu Framework

`Menu` provides a reusable execution algorithm while subclasses implement:

```text
printOptions()
handleChoice()
```

This gives the menu system a template style structure.

### Single Responsibility

Several classes have focused responsibilities:

```text
AnimalManager
    Animal collection operations

OwnerManager
    Owner collection operations

CsvManager
    CSV parsing and ingestion

Menu classes
    User interaction

ShelterUtil
    Shared configuration values
```

### Open and Closed Design

The animal hierarchy allows new concrete animal types to be introduced without changing the core `AnimalManager` collection type.

The manager can continue storing:

```java
List<Animal>
```

even when additional subclasses are introduced.

## High Level Architecture Diagram

```text
                         +───────────+
                         |    AnimalShelter      |
                         |      main()           |
                         +─────+─────-+
                                    |
                     +───────+───────+
                     |              |              |
                     v              v              v
                MainMenu      AnimalManager   OwnerManager
                     |
          +─────+─────+
          |          |           |
          v          v           v
       Owner     Data Import   Animal
       Menu         Menu       Menu
                      |           |
                      v           v
                 CsvManager   AnimalManager
                      |
             +────+────+
             |                 |
             v                 v
        OwnerManager      Animal Objects
                               |
                 +──────-+──────-+
                 |             |             |
                 v             v             v
                Dog           Cat           Bird
                 |             |             |
                 +──────-+──────-+
                               |
                              Horse
```

## Class Relationship Diagram

```text
                         <<abstract>>
                           Animal
                             |
             +───────-+───────-+
             |               |               |
            Dog             Cat             Bird
             |               |               |
             +───────-+───────-+
                             |
                           Horse

Dog, Cat, Bird, Horse
        |
        +── implements Adoptable
        |
        +── implements Behavior
        |
        +── implements Displayable

Owner
 |
 +── implements Displayable

Menu
 |
 +── MainMenu
 +── OwnerManagementMenu
 +── AnimalManagementMenu
 +── DataIngestionMenu
```

## Summary

The Animal Shelter application is organized around a clear object oriented hierarchy.

At the center of the domain model is the abstract `Animal` class. `Dog`, `Cat`, `Bird`, and `Horse` inherit the shared animal state while providing their own adoption fee, suitability, adoption, and display behavior.

Interfaces provide common capabilities:

```text
Adoptable
Behavior
Displayable
```

Managers maintain application state:

```text
AnimalManager
OwnerManager
```

`CsvManager` acts as the bridge between external CSV data and those managers.

The menu hierarchy provides the console user interface, while `AnimalShelter` assembles the application's dependencies and starts execution.

Overall, the project demonstrates inheritance, abstraction, encapsulation, polymorphism, interfaces, generics, collections, exception handling, file processing, and separation of responsibilities in a Java application.
