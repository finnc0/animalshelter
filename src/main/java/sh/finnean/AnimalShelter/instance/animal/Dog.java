package sh.finnean.AnimalShelter.instance.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.time.LocalDate;

public class Dog extends Animal {

    private boolean likesWalks;
    private boolean crateTrained;

    public Dog(String name,
               LocalDate vaccDate,
               String ownerName,
               String email,
               boolean likesWalks,
               boolean crateTrained
    ) {
        super(name,vaccDate,ownerName,email);

        this.likesWalks = likesWalks;
        this.crateTrained = crateTrained;
    }

    @Override
    public double getAdoptionFee() {
        // dog adoption fee is the base fee unless they are unvacc, then its base fee + 100$;
        // if dog likes walks, then add 25$ to base fee;
        double fee = ShelterUtil.dogAdoptionFee();
        // if vacc date is unavail it means its not set, therefore add an additional charge to the adoption fee
        if (this.getVaccDate() == null) {
            fee += 100;
        }

        if (likesWalks) { fee += 25;}

        return fee;
    }

    @Override
    public boolean suitableForFamily() {
        if (likesWalks && crateTrained) {
            // verify if vaccinated as well
            return getVaccDate() != null;
        }
        return false;
    }

    // getters
    public boolean isCrateTrained() { return this.crateTrained; }
    public boolean likesWalks() { return this.likesWalks; }

    // setters
    public void setCrateTrained(boolean crateTrained) { this.crateTrained = crateTrained; }
    public void setLikesWalks(boolean likesWalks) { this.likesWalks = likesWalks; }
}
