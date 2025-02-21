package clinix.com.clinicaservice.service;

import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.DTO.HorarioDTO;
import clinix.com.clinicaservice.model.Clinica;

@Service
public class HoraryService {
    
    public boolean checkExpediente(Long clinic_id, HorarioDTO horary_check) {
        Clinica c = this.findById(clinic_id);

    }
    
}
