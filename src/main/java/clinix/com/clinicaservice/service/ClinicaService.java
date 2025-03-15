package clinix.com.clinicaservice.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.DTO.HorarioDTO;
import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.model.NullClinica;
import clinix.com.clinicaservice.repository.ClinicaRepository;
import jakarta.transaction.Transactional;

@Service
public class ClinicaService {

    private final ClinicaRepository clinicaRepository;
    private final VinculoService vinculoService;
    private final HoraryService horaryService;

    @Autowired
    public ClinicaService(ClinicaRepository clinicaRepository,
            VinculoService vinculoService,
            HoraryService horaryService) {
        this.clinicaRepository = clinicaRepository;
        this.vinculoService = vinculoService;
        this.horaryService = horaryService;
    }

    public Clinica findById(Long id) {
        return this.clinicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Clínica Não Encontrada"));
    }

    public Clinica findByNomeFantasia(String nomeFantasia) {
        return this.clinicaRepository.findByNomeFantasia(nomeFantasia).orElseThrow(() -> new RuntimeException("Clínica Não Encontrada"));
    }

    public List<Clinica> findAll() {
        return this.clinicaRepository.findAll();
    }

    public Clinica create(Clinica clinica) {
        return this.save(clinica);

    }

    public Clinica save(Clinica c){
        return this.clinicaRepository.save(c);
    }

    public Boolean update(Clinica clinic) {
        /* clinic.atualizar(clinic); */
        try {
            this.save(clinic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean remove(Long id) {
        try {

            if (!this.clinicaRepository.findById(id).isEmpty()) {
                this.clinicaRepository.deleteById(id);
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public List<Long> getPatients(Long clinic_id) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        return c.getPacientes();

    }

    /**
     * Procura pelos médicos de uma determinada clínica com vínculos igual a
     * verdeiro
     * 
     * @param clinic_id
     * @return lista de IDs de médicos
     */
    public List<Long> getMedics(Long clinic_id) {
        List<ClinicaMedico> cms = this.getSolicitationsByStatus(clinic_id, true);
        return cms.stream()
                .map(ClinicaMedico::getMedicoId)
                .toList();
    }

    /**
     * Procura pelas solicitações de vínculo determinada clínica
     *
     * 
     * @param clinic_id
     * @return lista de solicitações
     */
    public List<ClinicaMedico> getSolicitations(Long clinic_id) {
        return this.getSolicitationsByStatus(clinic_id, false);
    }

    @Transactional
    public ClinicaMedico solicitateVinculate(Long clinic_id, Long medic_id) {
        Clinica c = this.findById(clinic_id);

        ClinicaMedico cm = this.vinculoService.solicitate(c, medic_id);

        c.addSolicitacao(cm);
        return cm;
    }

    @Transactional
    public ClinicaMedico vinculateMedic(Long clinic_id, Long medic_id) {
        return this.vinculoService.vincular(medic_id, clinic_id);
    }

    @Transactional
    public ClinicaMedico desvinculateMedic(Long clinic_id, Long medic_id) {
        return this.vinculoService.desvincular(medic_id, clinic_id);
    }

    public List<ClinicaMedico> getAllSolicitations(Long clinic_id) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        return c.getMedicos_vinculos();
    }

    public List<ClinicaMedico> getSolicitationsByStatus(Long clinic_id, boolean status) {
        Clinica c = this.findById(clinic_id);
        return this.vinculoService.findByStatus(c, status);

    }

    @Transactional
    public boolean refuseSolicitation(Long clinic_id, Long medic_id) {
        Clinica c = this.findById(clinic_id);
        return this.vinculoService.refuseSolicitation(c, medic_id);
    }

    // Checa se um determinado horário está de acordo com o horário de abertura e
    // encerramento de uma clínica
    public boolean checkExpediente(Long clinic_id, HorarioDTO horary_check) {
        Clinica c = this.findById(clinic_id);
        return this.horaryService.checkExpediente(c, horary_check);

    }

    @Transactional
    public Clinica updateClinicHorary(Long clinic_id, HorarioDTO new_horary) {
        Clinica c = this.findById(clinic_id);
        this.horaryService.alterarHorarioClinica(c, new_horary);
        return this.save(c);
        
    }
    public ClinicaMedico updateMedicHorary(Long clinic_id, Long m_id, HorarioDTO h_dto) {
        Clinica c = this.findById(clinic_id);
        return this.horaryService.alterarHorarioMedico(c,m_id, h_dto);
        
    }
}
