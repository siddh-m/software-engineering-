
-- Reset tables so repeated context loads do not violate PK constraints.
DELETE FROM grade;
DELETE FROM registration;
DELETE FROM module;
DELETE FROM student;

-- Insert sample students
INSERT INTO student (id, first_name, last_name, username, email) VALUES
  (1, 'John', 'Doe', 'jdoe', 'john.doe@ucl.ac.uk'),
  (2, 'Jane', 'Smith', 'jsmith', 'jane.smith@ucl.ac.uk'),
  (3, 'Alice', 'Johnson', 'ajohnson', 'alice.johnson@ucl.ac.uk');

-- Insert sample modules
INSERT INTO module (code, name, mnc) VALUES
  ('COMP0010', 'Software Engineering', true),
  ('COMP0011', 'Mathematics and Statistics', false),
  ('COMP0012', 'Compilers', true);

-- Insert sample registrations
INSERT INTO registration (student_id, module_code) VALUES
  (1, 'COMP0010'),
  (1, 'COMP0011'),
  (2, 'COMP0010'),
  (2, 'COMP0012'),
  (3, 'COMP0011'),
  (3, 'COMP0012');

-- Insert sample grades
INSERT INTO grade (score, academic_year, student_id, module_code) VALUES
  (85, '2024-2025', 1, 'COMP0010'),
  (92, '2024-2025', 1, 'COMP0011'),
  (78, '2024-2025', 2, 'COMP0010'),
  (88, '2024-2025', 2, 'COMP0012'),
  (95, '2024-2025', 3, 'COMP0011');
