package tcc2.stableMatching;

import tcc2.personas.Mentor;
import tcc2.personas.Student;
import tcc2.personas.Preference;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GaleShapleyTest {

    @Test
    void testPerfectMatch5x5() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");
        Student student4 = new Student("Student4");
        Student student5 = new Student("Student5");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");
        Mentor mentor4 = new Mentor("Mentor4");
        Mentor mentor5 = new Mentor("Mentor5");

        // Student Preferences (rank 1 indicates top preference)
        student1.addPreference(new Preference(mentor1, 1));
        student1.addPreference(new Preference(mentor2, 2));
        student1.addPreference(new Preference(mentor3, 3));
        student1.addPreference(new Preference(mentor4, 4));
        student1.addPreference(new Preference(mentor5, 5));

        student2.addPreference(new Preference(mentor2, 1));
        student2.addPreference(new Preference(mentor1, 2));
        student2.addPreference(new Preference(mentor3, 3));
        student2.addPreference(new Preference(mentor4, 4));
        student2.addPreference(new Preference(mentor5, 5));

        student3.addPreference(new Preference(mentor3, 1));
        student3.addPreference(new Preference(mentor1, 2));
        student3.addPreference(new Preference(mentor2, 3));
        student3.addPreference(new Preference(mentor4, 4));
        student3.addPreference(new Preference(mentor5, 5));

        student4.addPreference(new Preference(mentor4, 1));
        student4.addPreference(new Preference(mentor1, 2));
        student4.addPreference(new Preference(mentor2, 3));
        student4.addPreference(new Preference(mentor3, 4));
        student4.addPreference(new Preference(mentor5, 5));

        student5.addPreference(new Preference(mentor5, 1));
        student5.addPreference(new Preference(mentor1, 2));
        student5.addPreference(new Preference(mentor2, 3));
        student5.addPreference(new Preference(mentor3, 4));
        student5.addPreference(new Preference(mentor4, 5));

        // Mentor Preferences
        mentor1.addPreference(new Preference(student1, 1));
        mentor1.addPreference(new Preference(student2, 2));
        mentor1.addPreference(new Preference(student3, 3));
        mentor1.addPreference(new Preference(student4, 4));
        mentor1.addPreference(new Preference(student5, 5));

        mentor2.addPreference(new Preference(student2, 1));
        mentor2.addPreference(new Preference(student1, 2));
        mentor2.addPreference(new Preference(student3, 3));
        mentor2.addPreference(new Preference(student4, 4));
        mentor2.addPreference(new Preference(student5, 5));

        mentor3.addPreference(new Preference(student3, 1));
        mentor3.addPreference(new Preference(student1, 2));
        mentor3.addPreference(new Preference(student2, 3));
        mentor3.addPreference(new Preference(student4, 4));
        mentor3.addPreference(new Preference(student5, 5));

        mentor4.addPreference(new Preference(student4, 1));
        mentor4.addPreference(new Preference(student1, 2));
        mentor4.addPreference(new Preference(student2, 3));
        mentor4.addPreference(new Preference(student3, 4));
        mentor4.addPreference(new Preference(student5, 5));

        mentor5.addPreference(new Preference(student5, 1));
        mentor5.addPreference(new Preference(student1, 2));
        mentor5.addPreference(new Preference(student2, 3));
        mentor5.addPreference(new Preference(student3, 4));
        mentor5.addPreference(new Preference(student4, 5));

        Set<Student> students = Set.of(student1, student2, student3, student4, student5);
        Set<Mentor> mentors = Set.of(mentor1, mentor2, mentor3, mentor4, mentor5);

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions
        assertEquals(mentor1, student1.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor2, student2.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor3, student3.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor4, student4.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor5, student5.getCurrentMatch().getPreferredPersona());

        assertEquals(student1, mentor1.getCurrentMatch().getPreferredPersona());
        assertEquals(student2, mentor2.getCurrentMatch().getPreferredPersona());
        assertEquals(student3, mentor3.getCurrentMatch().getPreferredPersona());
        assertEquals(student4, mentor4.getCurrentMatch().getPreferredPersona());
        assertEquals(student5, mentor5.getCurrentMatch().getPreferredPersona());
    }

    @Test
    void testConflictingPreferences5x5() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");
        Student student4 = new Student("Student4");
        Student student5 = new Student("Student5");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");
        Mentor mentor4 = new Mentor("Mentor4");
        Mentor mentor5 = new Mentor("Mentor5");

        // Each student prefers Mentor1 the most, followed by the others in sequential order
        for (Student student : Arrays.asList(student1, student2, student3, student4, student5)) {
            student.addPreference(new Preference(mentor1, 1));
            student.addPreference(new Preference(mentor2, 2));
            student.addPreference(new Preference(mentor3, 3));
            student.addPreference(new Preference(mentor4, 4));
            student.addPreference(new Preference(mentor5, 5));
        }

        // Each mentor prefers Student1 the most, followed by the others in sequential order
        for (Mentor mentor : Arrays.asList(mentor1, mentor2, mentor3, mentor4, mentor5)) {
            mentor.addPreference(new Preference(student1, 1));
            mentor.addPreference(new Preference(student2, 2));
            mentor.addPreference(new Preference(student3, 3));
            mentor.addPreference(new Preference(student4, 4));
            mentor.addPreference(new Preference(student5, 5));
        }

        Set<Student> students = Set.of(student1, student2, student3, student4, student5);
        Set<Mentor> mentors = Set.of(mentor1, mentor2, mentor3, mentor4, mentor5);

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions
        assertEquals(mentor1, student1.getCurrentMatch().getPreferredPersona());
        assertEquals(student1, mentor1.getCurrentMatch().getPreferredPersona());

        assertEquals(mentor2, student2.getCurrentMatch().getPreferredPersona());
        assertEquals(student2, mentor2.getCurrentMatch().getPreferredPersona());

        assertEquals(mentor3, student3.getCurrentMatch().getPreferredPersona());
        assertEquals(student3, mentor3.getCurrentMatch().getPreferredPersona());

        assertEquals(mentor4, student4.getCurrentMatch().getPreferredPersona());
        assertEquals(student4, mentor4.getCurrentMatch().getPreferredPersona());

        assertEquals(mentor5, student5.getCurrentMatch().getPreferredPersona());
        assertEquals(student5, mentor5.getCurrentMatch().getPreferredPersona());
    }

    @Test
    void testOneSidedMatch5x5() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");
        Student student4 = new Student("Student4");
        Student student5 = new Student("Student5");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");
        Mentor mentor4 = new Mentor("Mentor4");
        Mentor mentor5 = new Mentor("Mentor5");

        // Each student has ranked the mentors
        student1.addPreference(new Preference(mentor1, 1));
        student1.addPreference(new Preference(mentor2, 2));
        student1.addPreference(new Preference(mentor3, 3));
        student1.addPreference(new Preference(mentor4, 4));
        student1.addPreference(new Preference(mentor5, 5));

        student2.addPreference(new Preference(mentor2, 1));
        student2.addPreference(new Preference(mentor1, 2));
        student2.addPreference(new Preference(mentor3, 3));
        student2.addPreference(new Preference(mentor4, 4));
        student2.addPreference(new Preference(mentor5, 5));

        student3.addPreference(new Preference(mentor3, 1));
        student3.addPreference(new Preference(mentor1, 2));
        student3.addPreference(new Preference(mentor2, 3));
        student3.addPreference(new Preference(mentor4, 4));
        student3.addPreference(new Preference(mentor5, 5));

        student4.addPreference(new Preference(mentor4, 1));
        student4.addPreference(new Preference(mentor1, 2));
        student4.addPreference(new Preference(mentor2, 3));
        student4.addPreference(new Preference(mentor3, 4));
        student4.addPreference(new Preference(mentor5, 5));

        student5.addPreference(new Preference(mentor5, 1));
        student5.addPreference(new Preference(mentor1, 2));
        student5.addPreference(new Preference(mentor2, 3));
        student5.addPreference(new Preference(mentor3, 4));
        student5.addPreference(new Preference(mentor4, 5));

        // Mentors have not ranked any students

        Set<Student> students = Set.of(student1, student2, student3, student4, student5);
        Set<Mentor> mentors = Set.of(mentor1, mentor2, mentor3, mentor4, mentor5);

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions
        assertEquals(mentor1, student1.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor2, student2.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor3, student3.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor4, student4.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor5, student5.getCurrentMatch().getPreferredPersona());
    }

    @Test
    void testStableMatchingScenario4() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");
        Student student4 = new Student("Student4");
        Student student5 = new Student("Student5");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");
        Mentor mentor4 = new Mentor("Mentor4");
        Mentor mentor5 = new Mentor("Mentor5");

        // Creating cyclic preferences
        student1.addPreference(new Preference(mentor2, 1));
        student2.addPreference(new Preference(mentor3, 1));
        student3.addPreference(new Preference(mentor4, 1));
        student4.addPreference(new Preference(mentor5, 1));
        student5.addPreference(new Preference(mentor1, 1));

        mentor1.addPreference(new Preference(student1, 1));
        mentor2.addPreference(new Preference(student2, 1));
        mentor3.addPreference(new Preference(student3, 1));
        mentor4.addPreference(new Preference(student4, 1));
        mentor5.addPreference(new Preference(student5, 1));

        // Filling other preferences for more randomness
        for (Student student : Arrays.asList(student1, student2, student3, student4, student5)) {
            student.addPreference(new Preference(mentor2, 2));
            student.addPreference(new Preference(mentor3, 3));
            student.addPreference(new Preference(mentor4, 4));
            student.addPreference(new Preference(mentor5, 5));
        }

        for (Mentor mentor : Arrays.asList(mentor1, mentor2, mentor3, mentor4, mentor5)) {
            mentor.addPreference(new Preference(student3, 2));
            mentor.addPreference(new Preference(student4, 3));
            mentor.addPreference(new Preference(student5, 4));
            mentor.addPreference(new Preference(student1, 5));
        }

        Set<Student> students = Set.of(student1, student2, student3, student4, student5);
        Set<Mentor> mentors = Set.of(mentor1, mentor2, mentor3, mentor4, mentor5);

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions
        // Given the cyclic nature of the top preferences, these assertions ensure that
        // the algorithm did not fall into a simple pattern based on these top preferences.
        assertNotEquals(mentor1, student1.getCurrentMatch().getPreferredPersona());
        assertNotEquals(mentor2, student2.getCurrentMatch().getPreferredPersona());
        assertNotEquals(mentor3, student3.getCurrentMatch().getPreferredPersona());
        assertNotEquals(mentor4, student4.getCurrentMatch().getPreferredPersona());
        assertNotEquals(mentor5, student5.getCurrentMatch().getPreferredPersona());

        // ... other assertions based on the specific results you observe.
    }
    @Test
    void testStableMatching3x3() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");

        // Student Preferences (higher rank indicates higher preference)
        student1.addPreference(new Preference(mentor3, 1));
        student1.addPreference(new Preference(mentor2, 2));
        student1.addPreference(new Preference(mentor1, 3));

        student2.addPreference(new Preference(mentor1, 1));
        student2.addPreference(new Preference(mentor2, 2));
        student2.addPreference(new Preference(mentor3, 3));

        student3.addPreference(new Preference(mentor2, 1));
        student3.addPreference(new Preference(mentor3, 2));
        student3.addPreference(new Preference(mentor1, 3));

        // Mentor Preferences
        mentor1.addPreference(new Preference(student1, 1));
        mentor1.addPreference(new Preference(student2, 2));
        mentor1.addPreference(new Preference(student3, 3));

        mentor2.addPreference(new Preference(student2, 1));
        mentor2.addPreference(new Preference(student1, 2));
        mentor2.addPreference(new Preference(student3, 3));

        mentor3.addPreference(new Preference(student3, 1));
        mentor3.addPreference(new Preference(student1, 2));
        mentor3.addPreference(new Preference(student2, 3));

        Set<Student> students = Set.of(student1, student2, student3);
        Set<Mentor> mentors = Set.of(mentor1, mentor2, mentor3);

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions
        assertEquals(mentor3, student1.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor1, student2.getCurrentMatch().getPreferredPersona());
        assertEquals(mentor2, student3.getCurrentMatch().getPreferredPersona());

        assertEquals(student1, mentor3.getCurrentMatch().getPreferredPersona());
        assertEquals(student2, mentor1.getCurrentMatch().getPreferredPersona());
        assertEquals(student3, mentor2.getCurrentMatch().getPreferredPersona());
    }

    @Test
    void testStableMatchingScenario5() {
        final int NUM_PERSONAS = 5; // Assuming 5 students and 5 mentors

        // Create Students and Mentors
        Set<Student> students = new HashSet<>();
        Set<Mentor> mentors = new HashSet<>();
        for (int i = 1; i <= NUM_PERSONAS; i++) {
            students.add(new Student("Student" + i));
            mentors.add(new Mentor("Mentor" + i));
        }

        // Generate random preferences for students
        List<Mentor> mentorList = new ArrayList<>(mentors);
        for (Student student : students) {
            Collections.shuffle(mentorList);
            for (int rank = 1; rank <= NUM_PERSONAS; rank++) {
                student.addPreference(new Preference(mentorList.get(rank - 1), rank));
            }
        }

        // Generate random preferences for mentors
        List<Student> studentList = new ArrayList<>(students);
        for (Mentor mentor : mentors) {
            Collections.shuffle(studentList);
            for (int rank = 1; rank <= NUM_PERSONAS; rank++) {
                mentor.addPreference(new Preference(studentList.get(rank - 1), rank));
            }
        }

        GaleShapley galeShapley = new GaleShapley();
        galeShapley.match(students, mentors);

        // Assertions: Ensure the match is stable
        assertTrue(isStableMatch(students, mentors));
    }

    private boolean isStableMatch(Set<Student> students, Set<Mentor> mentors) {
        for (Student student : students) {
            for (Mentor mentor : mentors) {
                // Check if student prefers this mentor over their current match and
                // the mentor prefers this student over their current match
                if (studentPrefersAnotherMentor(student, mentor) && mentorPrefersAnotherStudent(mentor, student)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean studentPrefersAnotherMentor(Student student, Mentor mentor) {
        int rankOfCurrentMentor = student.getCurrentMatch().getRank();
        int rankOfThisMentor = student.getPreferenceByUuid(mentor.getUuid()).getRank();
        return rankOfThisMentor < rankOfCurrentMentor;
    }

    private boolean mentorPrefersAnotherStudent(Mentor mentor, Student student) {
        int rankOfCurrentStudent = mentor.getCurrentMatch().getRank();
        int rankOfThisStudent = mentor.getPreferenceByUuid(student.getUuid()).getRank();
        return rankOfThisStudent < rankOfCurrentStudent;
    }
}
