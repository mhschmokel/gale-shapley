package tcc2.personas;

import lombok.Data;

import java.util.UUID;

public interface Persona {
    public UUID getUuid();

    public Preference getPreferenceByUuid(UUID uuid);
}
