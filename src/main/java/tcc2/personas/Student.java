package tcc2.personas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Student implements Persona {
    @NonNull
    private UUID uuid;

    @NonNull
    private String name;

    @EqualsAndHashCode.Exclude
    private final Set<Preference> preferences = new HashSet<Preference>();

    @EqualsAndHashCode.Exclude
    private final Set<Preference> remainingPreferences = new HashSet<Preference>();

    @EqualsAndHashCode.Exclude
    private final Set<Preference> rejectedPreferences = new HashSet<Preference>();

    @EqualsAndHashCode.Exclude
    private boolean isMatched = false;

    @EqualsAndHashCode.Exclude
    private boolean noMatchAvailable;

    private Preference currentMatch;

    public Student(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public void dismissCurrentMatch() {
        this.currentMatch = null;
        this.setMatched(false);
    }
    public void setCurrentMatch(Persona  mentor) {
        Preference p = this.getPreferenceByUuid(mentor.getUuid());

        if (p == null) {
            p = new Preference(mentor, 0);
        }

        this.currentMatch = p;
        this.setMatched(true);
    }

    public void addPreference(Preference preference) {
        preferences.add(preference);
        remainingPreferences.add(preference);
    }

    @Override
    public void rejectPreference(Preference p) {
        remainingPreferences.remove(p);
        rejectedPreferences.add(p);
    }

    @Override
    public Preference getPreferenceByUuid(UUID uuid) {
        Preference preference = null;
        for (Preference p : preferences) {
            if (p.getPreferredPersona().getUuid() == uuid) {
                preference = p;
            }
        }
        return preference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name); // Exclude preferences and other fields that may cause cyclic reference
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return Objects.equals(uuid, other.uuid) &&
                Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Student[ID: " + uuid + ", Name: " + name + "]";
    }
}
