package clinix.com.clinicaservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.model.Clinic;
import clinix.com.clinicaservice.model.NullClinic;
import clinix.com.clinicaservice.repository.ClinicRepository;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    @Autowired
    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public Clinic findById(Long id){
        return this.clinicRepository.findById(id).orElse(new NullClinic());        
    }

    public Clinic findByFantasyName(String fantasyName){
        
        return this.clinicRepository.findByFantasyName(fantasyName).orElse(new NullClinic());        
    }
    public List<Clinic> findAll(){
        return this.clinicRepository.findAll();        
    }

    public void delete(Long id) {
        /*TO DO */
        this.clinicRepository.deleteById(id);
    }
    
}
