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

    public Clinica findByNomeFantasia(String nomeFantasia){
        return this.clinicaRepository.findByNomeFantasia(nomeFantasia).orElse(new NullClinica());        
    }

    public List<Clinica> findAll(){
        return this.clinicaRepository.findAll();        
    }

    public Clinica create(Clinica clinica){
        return this.clinicaRepository.save(clinica);

    }

    public Clinica update(Clinica clinic) {
        /* clinic.atualizar(clinic); */
        return this.clinicaRepository.save(clinic);    
    }

    public boolean remove(Long id) {
        try{
            this.clinicaRepository.deleteById(id);
            return true;    
        }catch(Exception e){
            return false;
        }
    }

    public boolean vinculateMedic(Long clinic_id, Long medic_id)
    {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        if (c.isNull())
            return false;
        return c.vincular(medic_id);
    
    }

    public boolean desvinculateMedic(Long clinic_id, Long medic_id)
    {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        if (c.isNull())
            return false;
        return c.desvincular(medic_id);
    
    }

    public List<Long> getPatients(Long clinic_id) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        return c.getPacientes();        

    }

    public List<Long> getMedics(Long clinic_id) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        return c.getMedicos();        
    }
    
}
