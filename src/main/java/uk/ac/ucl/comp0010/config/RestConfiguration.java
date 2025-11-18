package uk.ac.ucl.comp0010.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Registration;
import uk.ac.ucl.comp0010.model.Student;

/**
 * Configuration for Spring Data REST.
 * Exposes entity IDs in REST responses for frontend consumption.
 */
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

  /**
   * Configures repository REST settings to expose entity IDs.
   * By default, Spring Data REST does not include entity IDs in responses.
   *
   * @param config the repository REST configuration
   * @param cors the CORS registry
   */
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config,
      CorsRegistry cors) {
    config.exposeIdsFor(Student.class);
    config.exposeIdsFor(Module.class);
    config.exposeIdsFor(Grade.class);
    config.exposeIdsFor(Registration.class);
  }
}
