package uk.ac.ucl.comp0010.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.ac.ucl.comp0010.model.Grade;
import uk.ac.ucl.comp0010.model.Module;
import uk.ac.ucl.comp0010.model.Student;
import uk.ac.ucl.comp0010.repository.GradeRepository;
import uk.ac.ucl.comp0010.repository.ModuleRepository;
import uk.ac.ucl.comp0010.repository.StudentRepository;

/**
 * Unit tests for GradeController.
 */
public class GradeControllerTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private ModuleRepository moduleRepository;

  @Mock
  private GradeRepository gradeRepository;

  @InjectMocks
  private GradeController gradeController;

  private Student student;
  private Module module;
  private Grade grade;

  /**
   * Set up test data and mocks before each test.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    student = new Student(1, "John", "Doe", "jdoe", "john.doe@ucl.ac.uk");
    module = new Module("COMP0010", "Software Engineering", true);
    grade = new Grade(85, student, module);
    grade.setId(1);
  }

  /**
   * Test successful grade addition.
   */
  @Test
  void testAddGradeSuccess() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    when(studentRepository.findById(1)).thenReturn(Optional.of(student));
    when(moduleRepository.findById("COMP0010")).thenReturn(Optional.of(module));
    when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(85, response.getBody().getScore());
  }

  /**
   * Test adding grade with missing student_id parameter.
   */
  @Test
  void testAddGradeMissingStudentId() {
    Map<String, String> params = new HashMap<>();
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /**
   * Test adding grade with missing module_code parameter.
   */
  @Test
  void testAddGradeMissingModuleCode() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("score", "85");

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /**
   * Test adding grade with missing score parameter.
   */
  @Test
  void testAddGradeMissingScore() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /**
   * Test adding grade when student is not found.
   */
  @Test
  void testAddGradeStudentNotFound() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "999");
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    when(studentRepository.findById(999)).thenReturn(Optional.empty());

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Test adding grade when module is not found.
   */
  @Test
  void testAddGradeModuleNotFound() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "INVALID");
    params.put("score", "85");

    when(studentRepository.findById(1)).thenReturn(Optional.of(student));
    when(moduleRepository.findById("INVALID")).thenReturn(Optional.empty());

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * Test adding grade with invalid student_id format.
   */
  @Test
  void testAddGradeInvalidStudentIdFormat() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "invalid");
    params.put("module_code", "COMP0010");
    params.put("score", "85");

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /**
   * Test adding grade with invalid score format.
   */
  @Test
  void testAddGradeInvalidScoreFormat() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");
    params.put("score", "not-a-number");

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  /**
   * Test adding grade with different valid score.
   */
  @Test
  void testAddGradeWithDifferentScore() {
    Map<String, String> params = new HashMap<>();
    params.put("student_id", "1");
    params.put("module_code", "COMP0010");
    params.put("score", "92");

    Grade newGrade = new Grade(92, student, module);
    newGrade.setId(2);

    when(studentRepository.findById(1)).thenReturn(Optional.of(student));
    when(moduleRepository.findById("COMP0010")).thenReturn(Optional.of(module));
    when(gradeRepository.save(any(Grade.class))).thenReturn(newGrade);

    ResponseEntity<Grade> response = gradeController.addGrade(params);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(92, response.getBody().getScore());
  }
}
