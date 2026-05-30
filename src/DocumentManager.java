package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class DocumentManager {

    private static final String TEMPLATES_DIR = "templates" + File.separator;
    private static final String OUTPUT_DIR =
            System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    private static final String TEMPLATE_FILENAME = "template.pdf";

    private DocumentManager() {
    }

    public static String download(String docType) {
        try {
            String folder = folderForDocType(docType);
            if (folder == null) {
                throw new IOException();
            }

            File template = new File(TEMPLATES_DIR + folder, TEMPLATE_FILENAME);
            File outDir = new File(OUTPUT_DIR);
            outDir.mkdirs();

            File destination = new File(outDir, folder + ".pdf");
            Files.copy(template.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destination.getPath();

        } catch (IOException e) {
            System.out.println("Error: Unable to download PDF.");
            return null;
        }
    }

    private static String folderForDocType(String docType) {
        if (docType == null) {
            return null;
        }
        if (docType.equalsIgnoreCase("Barangay Clearance")) {
            return "barangay_clearance";
        }
        if (docType.equalsIgnoreCase("Barangay ID")) {
            return "barangay_id";
        }
        if (docType.equalsIgnoreCase("Certificate of Residency")) {
            return "certificate_of_residency";
        }
        if (docType.equalsIgnoreCase("Certificate of Indigency")) {
            return "certificate_of_indigency";
        }
        if (docType.equalsIgnoreCase("Barangay Business Permit")) {
            return "barangay_business_permit";
        }
        if (docType.equalsIgnoreCase("Barangay Building Clearance")) {
            return "barangay_building_clearance";
        }
        return null;
    }
}
