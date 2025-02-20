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
    private final ClinicaMedicoService clinicaMedicoService;

    @Autowired
    public ClinicaService(ClinicaRepository clinicaRepository, ClinicaMedicoService clinicaMedicoService) {
        this.clinicaRepository = clinicaRepository;
        this.clinicaMedicoService = clinicaMedicoService;
    }

    public Clinica findById(Long id) {
        return this.clinicaRepository.findById(id).orElseThrow(() -> new RuntimeException("Clínica Não Encontrada"));
    }

    public Clinica findByNomeFantasia(String nomeFantasia) {
        return this.clinicaRepository.findByNomeFantasia(nomeFantasia).orElse(new NullClinica());
    }

    public List<Clinica> findAll() {
        return this.clinicaRepository.findAll();
    }

    public Clinica create(Clinica clinica) {
        return this.clinicaRepository.save(clinica);

    }

    public Boolean update(Clinica clinic) {
        /* clinic.atualizar(clinic); */
        try {
            this.clinicaRepository.save(clinic);
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


    public boolean checkExpediente(Long clinic_id, LocalTime horary_check) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        if (c.isNull() || !c.isDentroDoExpediente(horary_check))
            return false;
        return true;
    }

    @Transactional
    public boolean updateHorary(Long clinic_id, HorarioDTO new_horary) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        if (c.isNull())
            return false;

        if (!c.getHorarioAbertura().equals(new_horary.horaInicio()))
            this.updateStartHorary(c, new_horary.horaInicio());

        if (!c.getHorarioFechamento().equals(new_horary.horaFinal()))
            this.updateEndHorary(c, new_horary.horaFinal());
        this.clinicaRepository.save(c);
        return true;

    }

    private void updateStartHorary(Clinica c, LocalTime horaInicio) {
        c.setHorarioAbertura(horaInicio);
    }

    private void updateEndHorary(Clinica c, LocalTime horarioFechamento) {
        c.setHorarioFechamento(horarioFechamento);
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

        ClinicaMedico cm = this.clinicaMedicoService.solicitate(c, medic_id);

        c.addSolicitacao(cm);
        return cm;
    }

    @Transactional
    public ClinicaMedico vinculateMedic(Long clinic_id, Long medic_id) {
        return this.clinicaMedicoService.vincular(medic_id, clinic_id);
    }

    @Transactional
    public ClinicaMedico desvinculateMedic(Long clinic_id, Long medic_id) {
        return this.clinicaMedicoService.desvincular(medic_id, clinic_id);
    }

    
    public List<ClinicaMedico> getAllSolicitations(Long clinic_id) {
        Clinica c = this.clinicaRepository.findById(clinic_id).orElse(new NullClinica());
        return c.getMedicos_vinculos();
    }

    public List<ClinicaMedico> getSolicitationsByStatus(Long clinic_id, boolean status) {
        Clinica c = this.findById(clinic_id);
        return this.clinicaMedicoService.findByStatus(c, status);

    }

    @Transactional
    public boolean refuseSolicitation(Long clinic_id, Long medic_id) {
        Clinica c = this.findById(clinic_id);
        return this.clinicaMedicoService.refuseSolicitation(c, medic_id);
    }
}
