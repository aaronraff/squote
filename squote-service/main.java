import java.io.FileInputStream;
import java.util.Properties;

public class main {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream("app.config")) {
            props.load(in);
        } catch (Exception ex) {
            System.out.print(ex);
        }

    }
}
