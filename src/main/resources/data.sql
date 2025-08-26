INSERT INTO school (name) VALUES ('School');

INSERT INTO instructor (school_id, name)
SELECT (SELECT id FROM school WHERE name = 'School'), 'Prof Anna';
INSERT INTO instructor (school_id, name)
SELECT (SELECT id FROM school WHERE name = 'School'), 'Prof Bob';

INSERT INTO course (instructor_id, school_id, name)
SELECT (SELECT id FROM instructor WHERE name = 'Prof Anna'), (SELECT id FROM school WHERE name = 'School'), 'Humanities';
INSERT INTO course (instructor_id, school_id, name)
SELECT (SELECT id FROM instructor WHERE name = 'Prof Bob'), (SELECT id FROM school WHERE name = 'School'), 'Science';

INSERT INTO student (school_id, name)
SELECT (SELECT id FROM school WHERE name = 'School'), 'Ann';
INSERT INTO student (school_id, name)
SELECT (SELECT id FROM school WHERE name = 'School'), 'Bobby';

INSERT INTO course_students (courses_id, students_id)
SELECT (SELECT id FROM course WHERE name = 'Humanities'), (SELECT id FROM student WHERE name = 'Ann');
INSERT INTO course_students (courses_id, students_id)
SELECT (SELECT id FROM course WHERE name = 'Humanities'), (SELECT id FROM student WHERE name = 'Bobby');
INSERT INTO course_students (courses_id, students_id)
SELECT (SELECT id FROM course WHERE name = 'Science'), (SELECT id FROM student WHERE name = 'Ann');
INSERT INTO course_students (courses_id, students_id)
SELECT (SELECT id FROM course WHERE name = 'Science'), (SELECT id FROM student WHERE name = 'Bobby');