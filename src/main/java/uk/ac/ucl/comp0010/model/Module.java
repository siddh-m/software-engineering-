package uk.ac.ucl.comp0010.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a course module in the grade management system.
 * Contains module information including code, name, and whether it is mandatory non-condonable.
 */
@Entity
@Table(name = "module")
public class Module {

  @Id
  @Column(name = "code", length = 10)
  private String code;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "mnc")
  private Boolean mnc;

  /**
   * Default constructor for JPA.
   */
  public Module() {
  }

  /**
   * Constructor with all fields.
   *
   * @param code the module code (e.g., COMP0010)
   * @param name the module name
   * @param mnc whether the module is mandatory non-condonable
   */
  public Module(String code, String name, Boolean mnc) {
    this.code = code;
    this.name = name;
    this.mnc = mnc;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getMnc() {
    return mnc;
  }

  public void setMnc(Boolean mnc) {
    this.mnc = mnc;
  }
}
