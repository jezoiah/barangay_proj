public class BarangayCOI extends Document {
    BarangayCOI() {
        super();
        setDocType("Certificate of Indigency");
        getRequirements().add("Proof of Residency");
        getRequirements().add("Proof of Purpose");
    }
}
