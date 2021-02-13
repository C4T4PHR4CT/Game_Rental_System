import DAO.PostgreManager;
import mediator.RequestInterpreter;
import java.io.File;

/**entry point of tier 3*/
public class SEP3_tier3
{
    /**sets the SSL keystore, then instantiates a PostgreManager (input credentials here) and initializes the RequestInterpreter*/
    public static void main(String[] args)
    {
        String basePath = new File("").getAbsolutePath();
        System.setProperty("javax.net.ssl.keyStore", basePath + "\\src\\keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "kolbinyo");
        new RequestInterpreter(new PostgreManager("jdbc:postgresql://localhost:5432/postgres?currentSchema=game_rental_system", "postgres", "postgres"));
    }
}