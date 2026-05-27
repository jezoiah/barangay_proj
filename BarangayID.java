public class BarangayID extends Document {
    BarangayID() {
        super();
        setDocType("Barangay ID");
        getRequirements().add("Proof of Residency");
        getRequirements().add("Two (2) Recent 1x1 or 2x2 Photos w/ White Background");
    }
}
