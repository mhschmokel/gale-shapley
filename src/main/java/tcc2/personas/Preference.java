package tcc2.personas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Preference {
    private Persona preferredPersona;
    private int rank;
}
