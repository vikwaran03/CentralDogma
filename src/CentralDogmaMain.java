import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class CentralDogmaMain {

    private static final int CODON_LEN = 3;
    private static final String START_CODON = "aug";
    private static final String INVALID_SEQ = "Invalid DNA Sequence";

    public static HashMap readingData(String thisPathname) {
        File codonFile = new File(thisPathname);
        HashMap<String, String> aminoAcidHashMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(codonFile);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] aminoAcids = scanner.nextLine().split(",");
                aminoAcidHashMap.put(aminoAcids[0].toLowerCase(), aminoAcids[1].toLowerCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return aminoAcidHashMap;
    }

    public static String readingSeq(String sequence) throws IllegalArgumentException {
        if (sequence.length() % CODON_LEN != 0) {
            throw new IllegalArgumentException();
        }
        int start = 0;
        int end = CODON_LEN;
        while (end <= sequence.length()) {
            if (!sequence.substring(start, end).equals(START_CODON)) {
                start += CODON_LEN;
                end += CODON_LEN;
            } else {
                return sequence.substring(start).toLowerCase();
            }
        }
        return INVALID_SEQ;
    }

    public static String transcription(String sequence, String pathname) throws IllegalArgumentException {
        String checkString = readingSeq(sequence);
        if (checkString.equals(INVALID_SEQ)) {
            throw new IllegalArgumentException();
        }
        String returnSeq = "";
        int start = 0;
        int end = CODON_LEN;
        while (end <= checkString.length()) {
            String thisCodon = checkString.substring(start, end);
            if (thisCodon.equals("uaa") || thisCodon.equals("uag") || thisCodon.equals("uga")) {
                return returnSeq;
            }
            returnSeq += readingData(pathname).get(thisCodon) + "-";
            start += CODON_LEN;
            end += CODON_LEN;
        }
        System.out.println(returnSeq);
        return returnSeq;
    }

}
