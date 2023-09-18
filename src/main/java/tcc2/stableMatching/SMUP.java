package tcc2.stableMatching;

import tcc2.personas.Mentor;
import tcc2.personas.Preference;
import tcc2.personas.Student;

import java.util.Comparator;
import java.util.Set;

public class SMUP implements StableMatching {

    @Override
    public void match(Set<Student> students, Set<Mentor> mentors) {
        while (hasUnmatchedStudentOrMentor(students, mentors)) {
            for (Student student : students) {
                if (!student.isMatched()) {
                    Preference studentPreferredMentor = getTopPreferredMentor(student);

                    if (studentPreferredMentor != null) {
                        Mentor preferredMentor = (Mentor) studentPreferredMentor.getPreferredPersona();

                        // Check if the mentor is already matched
                        if (preferredMentor.getCurrentMatch() == null
                            && studentPreferredMentor.getRank() > -1) {
                            // Mentor is unmatched, engage them
                            student.setCurrentMatch(preferredMentor);
                            preferredMentor.setCurrentMatch(student);
                        } else {
                            // Check mentor's preference
                            Student currentMatch = (Student) preferredMentor.getCurrentMatch().getPreferredPersona();

                            if (mentorPrefersNewStudent(preferredMentor, currentMatch, student)) {
                                // Reject current match and match with new student
                                currentMatch.rejectPreference(currentMatch.getCurrentMatch());
                                currentMatch.dismissCurrentMatch(); // undo the current student
                                currentMatch.rejectPreference(preferredMentor.getPreferenceByUuid(currentMatch.getUuid()));

                                student.setCurrentMatch(preferredMentor);
                                preferredMentor.setCurrentMatch(student);
                            } else {
                                // Mentor prefers current match, so mark this preference as rejected for the student
                                student.rejectPreference(studentPreferredMentor);
                            }
                        }
                    }
                }
            }
        }
    }

    private Preference getTopPreferredMentor(Student student) {
        return student.getRemainingPreferences().stream()
                .filter(p -> p.getRank() > 0) // Only consider ranks greater than 0
                .min(Comparator.comparingInt(Preference::getRank))
                .orElse(null);
    }

    private boolean mentorPrefersNewStudent(Mentor mentor, Student currentStudent, Student newStudent) {
        if (mentor.getPreferences().isEmpty()) {
            return false;
        }

        int rankCurrent = mentor.getCurrentMatch().getRank();
        int rankNew = mentor.getPreferenceByUuid(newStudent.getUuid()).getRank();

        if (rankNew < 0) {
            newStudent.rejectPreference(newStudent.getPreferenceByUuid(mentor.getUuid()));
            mentor.rejectPreference(mentor.getPreferenceByUuid(newStudent.getUuid()));
            return false;
        }

        return rankNew < rankCurrent;
    }

    private boolean hasUnmatchedStudentOrMentor(Set<Student> students, Set<Mentor> mentors) {
        for (Student s : students) {
            if (s.getRemainingPreferences().isEmpty()) {
                s.setNoMatchAvailable(true);
            }
        }



        final var hasUnmatchedStudent = students.stream().anyMatch(student -> !student.isMatched() && !student.getNoMatchAvailable());
        final var hasUnmatchedMentor = mentors.stream().anyMatch(mentor -> !mentor.isMatched());

        return hasUnmatchedStudent || hasUnmatchedMentor;
    }
}
