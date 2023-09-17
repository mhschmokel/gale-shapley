package tcc2.stableMatching;

import tcc2.personas.Mentor;
import tcc2.personas.Student;
import tcc2.personas.Preference;

import java.util.Set;

public class GaleShapley implements StableMatching {

    @Override
    public void match(Set<Student> students, Set<Mentor> mentors) {
        while (hasUnmatchedStudentOrMentor(students, mentors)) {
            for (Student student : students) {
                if (!student.isMatched()) {
                    Preference studentPreferredMentor = getTopPreferredMentor(student);

                    if (studentPreferredMentor != null) {
                        Mentor preferredMentor = (Mentor) studentPreferredMentor.getPreferredPersona();


                        // Check if the mentor is already matched
                        if (preferredMentor.getCurrentMatch() == null) {
                            // Mentor is unmatched, engage them
                            student.setCurrentMatch(studentPreferredMentor);
                            preferredMentor.setCurrentMatch(preferredMentor.getPreferenceByUuid(student.getUuid()));
                        } else {
                            // Check mentor's preference
                            Student currentMatch = (Student) preferredMentor.getCurrentMatch().getPreferredPersona();

                            if (mentorPrefersNewStudent(preferredMentor, currentMatch, student)) {
                                // Reject current match and match with new student
                                currentMatch.setCurrentMatch(null); // undo the current student
                                student.setCurrentMatch(studentPreferredMentor);
                                preferredMentor.setCurrentMatch(preferredMentor.getPreferenceByUuid(student.getUuid()));
                            }
                        }
                    }
                }
            }
        }
    }

    private Preference getTopPreferredMentor(Student student) {
        return student.getPreferences().stream()
                .filter(preference -> ((Mentor) preference.getPreferredPersona()).getCurrentMatch() == null)
                .max((p1, p2) -> Integer.compare(p1.getRank(), p2.getRank()))
                .orElse(null);
    }

    private boolean mentorPrefersNewStudent(Mentor mentor, Student currentStudent, Student newStudent) {
        int rankCurrent = mentor.getCurrentMatch().getRank();
        int rankNew = mentor.getPreferenceByUuid(newStudent.getUuid()).getRank();

        return rankNew > rankCurrent;
    }


    private boolean hasUnmatchedStudentOrMentor(Set<Student> students, Set<Mentor> mentors) {
        final var hasUnmatchedStudent = students.stream().anyMatch(student -> !student.isMatched());
        final var hasUnmatchedMentor = mentors.stream().anyMatch(mentor -> !mentor.isMatched());

        return hasUnmatchedStudent || hasUnmatchedMentor;
    }
}
