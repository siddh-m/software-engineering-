package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents a grade assigned to a student for a specific module.
 * Links students and modules with their corresponding scores.
 */
@Entity
@Table(name = "grade")
public class Grade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "score")
  private Integer score;

  @ManyToOne
  @JoinColumn(name = "student_id", referencedColumnName = "id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "module_code", referencedColumnName = "code")
  private Module module;

  /**
   * Default constructor for JPA.
   */
  public Grade() {
  }

  /**
   * Constructor with all fields.
   *
   * @param score the grade score
   * @param student the student who received the grade
   * @param module the module for which the grade was awarded
   */
  public Grade(Integer score, Student student, Module module) {
    this.score = score;
    this.student = student;
    this.module = module;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }
}
