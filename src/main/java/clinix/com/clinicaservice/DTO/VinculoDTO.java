package clinix.com.clinicaservice.DTO;

import java.time.LocalTime;

public record VinculoDTO(
    int clinica_id,
    int medicoId,
    LocalTime inicio_atendimento,
    LocalTime final_atendimento
    
){

};