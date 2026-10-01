package sh.finnean.AnimalShelter.factory;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.Note;
import ezvcard.property.StructuredName;
import ezvcard.property.Uid;
import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.animal.Bird;
import sh.finnean.AnimalShelter.instance.animal.Cat;
import sh.finnean.AnimalShelter.instance.animal.Dog;
import sh.finnean.AnimalShelter.instance.animal.Horse;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VcfFactory {

    private final AnimalManager animalManager;

    public VcfFactory(AnimalManager animalManager) {
        this.animalManager = animalManager;
    }

    private VCard createVCard(Animal a) {
        VCard vCard = new VCard();
        StructuredName name = new StructuredName();
        name.setGiven(a.getName());
        vCard.setStructuredName(name);

        // if owner, add email
        if (a.getOwner() != null) {
            vCard.addEmail(a.getOwner().getEmail());
        }

        String noteRaw = "";
        // applies to all
        Uid uid = new Uid(String.valueOf(a.id()));
        vCard.setUid(uid);

        noteRaw += "ID: " + a.id() + "\n";
        noteRaw += "Last Vaccination: " + a.getVaccDate() + "\n";


        switch(a) {
            case Dog d: {
                vCard.addExtendedProperty("X-ANIMAL-TYPE","DOG");
                noteRaw += "Likes Walks: " + d.likesWalks() + "\n";
                noteRaw += "Crate Trained: " + d.isCrateTrained() + "\n";
                break;
            }
            case Cat c: {
                vCard.addExtendedProperty("X-ANIMAL-TYPE","CAT");
                noteRaw += "Likes catnip: " + c.likesCatNip() + "\n";
                noteRaw += "Litterbox Trained: " + c.litterBoxTrained() + "\n";
                break;
            }
            case Horse h: {
                vCard.addExtendedProperty("X-ANIMAL-TYPE","HORSE");
                noteRaw += "Rideable: " + h.getIsRideable() + "\n";
                break;
            }
            case Bird b: {
                vCard.addExtendedProperty("X-ANIMAL-TYPE","BIRD");
                noteRaw += "Can Fly: " + b.getCanFly() + "\n";
                noteRaw += "Can Talk: " + b.getCanTalk() + "\n";
                break;
            }
            default:
                System.out.println("VCard could not be created due to invalid child class.");
        }

        Note note = new Note(noteRaw);
        vCard.addNote(note);

        return vCard;
    }


    // deletes all contact cards currently in the data/contacts folder.
    private void prepareDir(Path storeDir ) {
        try {
            Files.createDirectories(storeDir);

            // Remove old .vcf files so only the current animals remain
            try (DirectoryStream<Path> oldFiles = Files.newDirectoryStream(storeDir, "*.vcf")) {
                for (Path file : oldFiles) {
                    Files.delete(file);
                }
            }
        } catch (IOException e) {
            System.out.println("VCF LOG ERROR: Could not prepare directory " + storeDir + " | " + e);
            return;
        }
    }

    public void createContacts() {

        String storeDirUri = ShelterUtil.getVcfDataDirectoryURI();

        List<? extends Animal> animals = animalManager.getAllAnimals();
        System.out.println("Animals found: " + animals.size());

        List<VCard> vCards = new ArrayList<>();

        // create vcard for each animal and add to arr
        for (Animal a : animals) {
            vCards.add(createVCard(a));
        }

        prepareDir(Path.of(storeDirUri));


        for (VCard card : vCards) {
            String fileURI = storeDirUri + "/" + card.getUid().getValue() + ".vcf";

            String str = Ezvcard.write(card).version(VCardVersion.V4_0).go();

            Path filePath = Path.of(fileURI).toAbsolutePath();
            System.out.println("Writing VCFs to: " + filePath);

            try {
                Files.writeString(filePath, str, StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.out.println("VCF LOG ERROR: Failed to write VCF. Animal ID: " + card.getUid());
            }

            System.out.println("Wrote " + filePath);

        }
    }
}
