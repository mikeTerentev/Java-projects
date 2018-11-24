
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Sum {
    public static void main(String[] args) {
        if (args.length == 0) {
            InputStream in = System.in;
            byte[] mem = new byte[100000];
            try {
                MessageDigest sa;
                try {
                    sa = MessageDigest.getInstance("SHA-256");
                    int amount = in.read(mem);
                    while (amount > -1) {
                        sa.update(mem, 0, amount);
                        amount = in.read(mem);
                    }
                    System.out.println(DatatypeConverter.printHexBinary(sa.digest()).toUpperCase() + " *-");
                } catch (IOException e) {
                    e.getMessage();
                }
            } catch (NoSuchAlgorithmException e) {
                System.err.println("sha256sum algorithm crash");
            }
        } else {
            for (String file : args) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    System.err.println("sha256sum algorithm crash");
                }
                try {
                    md.update(Files.readAllBytes(Paths.get(file)));
                } catch (IOException e) {
                    System.err.println("Read file " + file + " error");
                }
                if (md != null) {
                    byte[] code = md.digest();
                    System.out.println(DatatypeConverter.printHexBinary(code).toUpperCase() + " *" + file);
                } else {
                    System.err.println("Null pointer");
                }
            }
        }
    }
}
