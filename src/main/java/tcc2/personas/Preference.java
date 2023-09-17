package tcc2.personas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Preference {
    private Persona preferredPersona;
    private int rank;

    @Override
    public String toString() {
        return "Preference[Rank: " + rank + ", Persona ID: " + preferredPersona.getUuid() + "]";
    }
}
