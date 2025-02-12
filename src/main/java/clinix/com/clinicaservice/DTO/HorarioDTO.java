package clinix.com.clinicaservice.DTO;

import java.time.LocalTime;

public record HorarioDTO(
LocalTime horaInicio, 
LocalTime horaFinal
){} 
