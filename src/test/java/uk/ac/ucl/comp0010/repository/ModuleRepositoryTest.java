package uk.ac.ucl.comp0010.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.ac.ucl.comp0010.model.Module;

/**
 * Integration tests for ModuleRepository.
 */
@DataJpaTest
public class ModuleRepositoryTest {

  @Autowired
  private ModuleRepository moduleRepository;

  /**
   * Test saving a module.
   */
  @Test
  void testSaveModule() {
    Module module = new Module("TEST001", "Test Module", true);
    Module saved = moduleRepository.save(module);

    assertNotNull(saved);
    assertEquals("TEST001", saved.getCode());
    assertEquals("Test Module", saved.getName());
  }

  /**
   * Test finding a module by code.
   */
  @Test
  void testFindByCode() {
    Module module = new Module("TEST002", "Another Test", false);
    moduleRepository.save(module);

    Optional<Module> found = moduleRepository.findById("TEST002");

    assertTrue(found.isPresent());
    assertEquals("Another Test", found.get().getName());
    assertEquals(false, found.get().getMnc());
  }

  /**
   * Test deleting a module.
   */
  @Test
  void testDeleteModule() {
    Module module = new Module("TEST003", "Delete Test", true);
    moduleRepository.save(module);

    moduleRepository.deleteById("TEST003");

    Optional<Module> found = moduleRepository.findById("TEST003");
    assertTrue(found.isEmpty());
  }

  /**
   * Test counting modules.
   */
  @Test
  void testCountModules() {
    long initialCount = moduleRepository.count();
    Module module = new Module("TEST004", "Count Test", false);
    moduleRepository.save(module);

    long newCount = moduleRepository.count();
    assertEquals(initialCount + 1, newCount);
  }
}
