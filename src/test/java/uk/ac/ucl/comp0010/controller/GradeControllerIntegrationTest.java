package uk.ac.ucl.comp0010.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Integration tests for GradeController using MockMvc.
 */
@SpringBootTest
@AutoConfigureMockMvc
class GradeControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private ModuleRepository moduleRepository;

  /**
   * Set up test data before each test.
   */
  @BeforeEach
  void setUp() {
    Student student = new Student(500, "Integration", "Test", "itest", "itest@ucl.ac.uk");
    studentRepository.save(student);

    Module module = new Module("INTEG001", "Integration Test Module", true);
    moduleRepository.save(module);
  }

  /**
   * Test adding a grade via REST endpoint.
   */
  @Test
  void testAddGradeEndpoint() throws Exception {
    String jsonRequest = "{"
        + "\"student_id\": \"500\","
        + "\"module_code\": \"INTEG001\","
        + "\"score\": \"95\""
        + "}";

    mockMvc.perform(post("/grades/addGrade")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.score").value(95));
  }

  /**
   * Test adding grade with invalid data.
   */
  @Test
  void testAddGradeInvalidData() throws Exception {
    String jsonRequest = "{"
        + "\"student_id\": \"999\","
        + "\"module_code\": \"INVALID\","
        + "\"score\": \"95\""
        + "}";

    mockMvc.perform(post("/grades/addGrade")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonRequest))
        .andExpect(status().isNotFound());
  }
}
