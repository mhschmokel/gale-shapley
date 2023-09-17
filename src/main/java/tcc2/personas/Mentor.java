package tcc2.personas;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Mentor implements Persona {
    @NonNull
    private UUID uuid;

    @NonNull
    private String name;

    @EqualsAndHashCode.Exclude
    private final List<Preference> preferences = new ArrayList<Preference>();

    @EqualsAndHashCode.Exclude
    private boolean isMatched = false;

    private Preference  currentMatch;

    public Mentor(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    public void setCurrentMatch(Preference  student) {
        this.currentMatch = student;
        this.setMatched(student != null);
    }

    public void addPreference(Preference preference) {
        preferences.add(preference);
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
}
