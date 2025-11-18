package uk.ac.ucl.comp0010;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Student Grade Management System.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
public class Application {

  /**
   * Main method to run the Spring Boot application.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
