package org.EvaAndTheSovietHouseholdAppliances;

import org.EvaAndTheSovietHouseholdAppliances.mediator.PersistenceFormatter;
import org.EvaAndTheSovietHouseholdAppliances.mediator.PersistenceInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import java.io.File;

/**entry point of tier 2*/
@SpringBootApplication
public class SEP3_tier2
{

  /**PersistenceInterface instance*/
  public static PersistenceInterface Persistence;

  /**sets the SSL keystore, then instantiates a PersistenceFormatter (input tier 1 address here) and starts the SpringBootApplication*/
  public static void main(String[] args)
  {
    String basePath = new File("").getAbsolutePath();
    System.setProperty("javax.net.ssl.trustStore", basePath + "\\src\\keystore.jks");
    System.setProperty("javax.net.ssl.trustStorePassword", "kolbinyo");
    Persistence = new PersistenceFormatter("localhost");
    String[] properties = new String[]{
            //"--server.port=42069"
            "--debug=true"
    };
    SpringApplication.run(SEP3_tier2.class, properties);
  }

  /**returns the corresponding org.springframework.http.HttpStatus object of the http status code convention*/
  public static HttpStatus getHttpStatus(int code)
  {
    switch (code)
    {
      case 200:
        return HttpStatus.OK;
      case 201:
        return HttpStatus.CREATED;
      case 400:
        return HttpStatus.BAD_REQUEST;
      case 403:
        return HttpStatus.FORBIDDEN;
      case 404:
        return HttpStatus.NOT_FOUND;
      case 409:
        return HttpStatus.CONFLICT;
      case 500:
        return HttpStatus.INTERNAL_SERVER_ERROR;
      default:
        return HttpStatus.OK;
    }
  }
}