package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a student in the grade management system.
 * Contains student personal information and identification.
 */
@Entity
@Table(name = "student")
public class Student {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "first_name", length = 30)
  private String firstName;

  @Column(name = "last_name", length = 30)
  private String lastName;

  @Column(name = "username", length = 30)
  private String username;

  @Column(name = "email", length = 50)
  private String email;

  /**
   * Default constructor for JPA.
   */
  public Student() {
  }

  /**
   * Constructor with all fields.
   *
   * @param id the student ID
   * @param firstName the student's first name
   * @param lastName the student's last name
   * @param username the student's username
   * @param email the student's email address
   */
  public Student(Integer id, String firstName, String lastName, String username, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
