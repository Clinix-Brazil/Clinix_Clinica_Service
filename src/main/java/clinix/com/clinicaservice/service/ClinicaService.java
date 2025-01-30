package clinix.com.clinicaservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.NullClinica;
import clinix.com.clinicaservice.repository.ClinicaRepository;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;

    @Autowired
    public ClinicaService(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    public Clinica findById(Long id){
        return this.clinicaRepository.findById(id).orElse(new NullClinica());        
    }

    public Clinica findByFantasyName(String fantasyName){
        
        return this.clinicaRepository.findByFantasyName(fantasyName).orElse(new NullClinica());        
    }
    public List<Clinica> findAll(){
        return this.clinicaRepository.findAll();        
    }

    public void delete(Long id) {
        /*TO DO */
        this.clinicaRepository.deleteById(id);
    }
    
}
