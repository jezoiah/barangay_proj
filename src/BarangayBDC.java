package src;
public class BarangayBDC extends Document {
    BarangayBDC() {
        super();
        setDocType("Barangay Building Clearance");
        getRequirements().add("Proof of Ownership or Tenancy");
        getRequirements().add("Project Documents");
    }
}
