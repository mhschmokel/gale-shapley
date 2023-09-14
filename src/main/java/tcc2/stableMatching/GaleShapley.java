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
                    Preference preferredMentorPreference = getTopPreferredMentor(student);

                    if (preferredMentorPreference != null) {
                        Mentor preferredMentor = (Mentor) preferredMentorPreference.getPreferredPersona();

                        // Check if the mentor is already matched
                        if (preferredMentor.getCurrentMatch() == null) {
                            // Mentor is unmatched, engage them
                            student.setCurrentMatch(preferredMentorPreference);
                            preferredMentor.setCurrentMatch(new Preference(student, preferredMentorPreference.getRank()));
                        } else {
                            // Check mentor's preference
                            Student currentMatch = (Student) preferredMentor.getCurrentMatch().getPreferredPersona();

                            if (mentorPrefersNewStudent(preferredMentor, currentMatch, student)) {
                                // Reject current match and match with new student
                                currentMatch.setCurrentMatch(null); // unmatch the current student
                                student.setCurrentMatch(preferredMentorPreference);
                                preferredMentor.setCurrentMatch(new Preference(student, preferredMentorPreference.getRank()));
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
        int rankCurrent = mentor.getPreferences().stream()
                .filter(pref -> pref.getPreferredPersona().getUuid().equals(currentStudent.getUuid()))
                .findFirst().orElse(new Preference(null, -1)).getRank();

        int rankNew = mentor.getPreferences().stream()
                .filter(pref -> pref.getPreferredPersona().equals(newStudent))
                .findFirst().orElse(new Preference(null, -1)).getRank();

        return rankNew > rankCurrent;
    }

    private boolean hasUnmatchedStudentOrMentor(Set<Student> students, Set<Mentor> mentors) {
        final var hasUnmatchedStudent = students.stream().anyMatch(student -> !student.isMatched());
        final var hasUnmatchedMentor = mentors.stream().anyMatch(mentor -> !mentor.isMatched());

        return hasUnmatchedStudent || hasUnmatchedMentor;
    }
}
