
package dto;

import domein.Vak;
import utils.Kleur;

public record SpelerDTO(String gebruikersnaam, Kleur kleur, Vak[][] koninkrijk) {
}
