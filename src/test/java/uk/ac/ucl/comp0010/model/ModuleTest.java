package uk.ac.ucl.comp0010.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Module model class.
 */
class ModuleTest {

  private Module module;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    module = new Module();
  }

  /**
   * Test default constructor.
   */
  @Test
  void testDefaultConstructor() {
    assertNotNull(module);
  }

  /**
   * Test constructor with all parameters.
   */
  @Test
  void testParameterizedConstructor() {
    Module testModule = new Module("COMP0010", "Software Engineering", true);

    assertEquals("COMP0010", testModule.getCode());
    assertEquals("Software Engineering", testModule.getName());
    assertTrue(testModule.getMnc());
  }

  /**
   * Test getCode and setCode methods.
   */
  @Test
  void testGetSetCode() {
    module.setCode("COMP0011");
    assertEquals("COMP0011", module.getCode());
  }

  /**
   * Test getName and setName methods.
   */
  @Test
  void testGetSetName() {
    module.setName("Mathematics and Statistics");
    assertEquals("Mathematics and Statistics", module.getName());
  }

  /**
   * Test getMnc and setMnc methods.
   */
  @Test
  void testGetSetMnc() {
    module.setMnc(true);
    assertTrue(module.getMnc());
  }

  /**
   * Test setting MNC to false.
   */
  @Test
  void testSetMncFalse() {
    module.setMnc(false);
    assertEquals(false, module.getMnc());
  }
}
