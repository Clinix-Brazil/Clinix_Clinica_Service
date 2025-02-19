package clinix.com.clinicaservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.repository.ClinicaMedicoRepository;

@Service
public class ClinicaMedicoService {
    
    private final ClinicaMedicoRepository clinicaMedicoRepository;

    @Autowired
    public ClinicaMedicoService(ClinicaMedicoRepository clinicaMedicoRepository) {
        this.clinicaMedicoRepository = clinicaMedicoRepository;
    }

    public Optional<ClinicaMedico> verificarVinculo(Long medicoId, Long clinicaId) {
        return clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId);
    }

    public ClinicaMedico aprovarVinculo(Long medicoId, Long clinicaId) {

        ClinicaMedico cm =this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
        .orElse(null);
        if (cm != null){
            cm.setAprovado(true);
            return this.save(cm);
        }
        return null;
        
    }

    public boolean desaprovarVinculo(Long medicoId, Clinica clinica){
        
        ClinicaMedico cm =this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinica.getId())
        .orElse(null);
        if (cm != null){
            this.delete(cm);;
            return true;
        }
        return false;
        
    }

    private ClinicaMedico save(ClinicaMedico cm){
        return this.clinicaMedicoRepository.save(cm);
    }
    private void delete(ClinicaMedico cm){
        this.clinicaMedicoRepository.delete(cm);
    }


}
