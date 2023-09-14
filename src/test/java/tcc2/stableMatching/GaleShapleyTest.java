package tcc2.stableMatching;

import tcc2.personas.Mentor;
import tcc2.personas.Student;
import tcc2.personas.Preference;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GaleShapleyTest {

    @Test
    void testStableMatching() {
        // Students
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");

        // Mentors
        Mentor mentor1 = new Mentor("Mentor1");
        Mentor mentor2 = new Mentor("Mentor2");
        Mentor mentor3 = new Mentor("Mentor3");

        // Student Preferences (higher rank indicates higher preference)
        student1.addPreference(new Preference(mentor3, 3));
        student1.addPreference(new Preference(mentor2, 2));
        student1.addPreference(new Preference(mentor1, 1));

        student2.addPreference(new Preference(mentor1, 3));
        student2.addPreference(new Preference(mentor2, 2));
        student2.addPreference(new Preference(mentor3, 1));

        student3.addPreference(new Preference(mentor2, 3));
        student3.addPreference(new Preference(mentor3, 2));
        student3.addPreference(new Preference(mentor1, 1));

        // Mentor Preferences
        mentor1.addPreference(new Preference(student1, 3));
        mentor1.addPreference(new Preference(student2, 2));
        mentor1.addPreference(new Preference(student3, 1));

        mentor2.addPreference(new Preference(student2, 3));
        mentor2.addPreference(new Preference(student1, 2));
        mentor2.addPreference(new Preference(student3, 1));

        mentor3.addPreference(new Preference(student3, 3));
        mentor3.addPreference(new Preference(student1, 2));
        mentor3.addPreference(new Preference(student2, 1));

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
}
