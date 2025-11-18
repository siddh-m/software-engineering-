package uk.ac.ucl.comp0010.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ucl.comp0010.model.Registration;

/**
 * Repository interface for Registration entity.
 * Provides CRUD operations and is automatically exposed as a REST endpoint.
 */
@RepositoryRestResource
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {
}
