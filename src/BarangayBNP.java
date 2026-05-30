package src;
public class BarangayBNP extends Document {
    BarangayBNP() {
        super();
        setDocType("Barangay Business Permit");
        getRequirements().add("Proof of Business Identity");
        getRequirements().add("Proof of Location");
    }
}
