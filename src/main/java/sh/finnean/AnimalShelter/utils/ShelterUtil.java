package sh.finnean.AnimalShelter.utils;

public class ShelterUtil {

    // can be modified in the future instead of hardcoding magic numbers or pulled from a yaml config or similar.
    private static final double dogAdoptionBaseFee = 120.99;
    private static final double catAdoptionBaseFee = 59.66;
    private static final double birdAdoptionBaseFee = 25.00;
    private static final double horseAdoptionBaseFee = 439.99;

    private static final String dataDirectoryURI = "data";


    public static double dogAdoptionFee() { return dogAdoptionBaseFee; }
    public static double catAdoptionFee() { return catAdoptionBaseFee; }
    public static double birdAdoptionFee() { return birdAdoptionBaseFee; }
    public static double horseAdoptionFee() { return horseAdoptionBaseFee; }

    public static String getDataDirURI() { return dataDirectoryURI; }
}
