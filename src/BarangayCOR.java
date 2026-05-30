package src;
public class BarangayCOR extends Document {
    BarangayCOR() {
        super();
        setDocType("Certificate of Residency");
        getRequirements().add("Proof of Residency");
    }

    @Override
    public void displayRequirements(Resident resident) {
        super.displayRequirements();
        System.out.println("\nYour Residency Duration: " + resident.calculateResidencyDuration() + " Months");
    }
}
