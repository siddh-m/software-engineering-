-- Sample data for Student Grade Management System

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
INSERT INTO grade (score, student_id, module_code) VALUES
  (85, 1, 'COMP0010'),
  (92, 1, 'COMP0011'),
  (78, 2, 'COMP0010'),
  (88, 2, 'COMP0012'),
  (95, 3, 'COMP0011');
