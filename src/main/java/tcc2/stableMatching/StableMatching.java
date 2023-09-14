package tcc2.stableMatching;

import tcc2.personas.Mentor;
import tcc2.personas.Student;

import java.util.Set;

public interface StableMatching {
    public void match(Set<Student> students, Set<Mentor> mentors);
}
