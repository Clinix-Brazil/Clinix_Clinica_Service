package clinix.com.clinicaservice.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.repository.ClinicaMedicoRepository;

@Service
public class VinculoService {

    private final ClinicaMedicoRepository clinicaMedicoRepository;

    @Autowired
    public VinculoService(ClinicaMedicoRepository clinicaMedicoRepository) {
        this.clinicaMedicoRepository = clinicaMedicoRepository;
    }

    public Optional<ClinicaMedico> find(Long medicoId, Long clinicaId) {
        return clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId);
    }

    public ClinicaMedico    vincular(Long medicoId, Long clinicaId) {
        ClinicaMedico cm = this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
                .orElseThrow(()-> new RuntimeException(" Solicitação de Vínculo não encontrada "));
        if (! cm.getAprovado() ) {
            cm.setAprovado(true);
            return this.save(cm);
        }
        throw new RuntimeException("Solitação já havia sido aprovada.");
    }

    public ClinicaMedico desvincular(Long medicoId, Long clinicaId) {
        ClinicaMedico cm = this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
                .orElseThrow(()-> new RuntimeException(" Solicitação de Vínculo não encontrada "));
        if (cm != null) {
            this.delete(cm);
            return cm;
        }
        return null;
    }

    public ClinicaMedico save(ClinicaMedico cm) {
        return this.clinicaMedicoRepository.save(cm);
    }

    private void delete(ClinicaMedico cm) {
        this.clinicaMedicoRepository.delete(cm);
    }

    /**
     * Cria uma solicitação de vínculo para um médico
     * @throws RunTimeException caso já exista uma solicitação de vínculo para o mesmo médico e aquela clínica
     * @param clinic_id
     * @param medic_id
     * @return
     */
    public ClinicaMedico solicitate(Clinica clinic, Long medic_id) {
        // Verificar se já existe um vínculo ou solicitação para esse médico e clínica
        Optional<ClinicaMedico> existingSolicitation = this.find(medic_id, clinic.getId());

        if (existingSolicitation.isPresent()) {
            throw new RuntimeException("Já existe uma solicitação de vínculo para este médico.");
        }
        ClinicaMedico cm = new ClinicaMedico(clinic, medic_id);
        return this.save(cm);
    }
    public boolean refuseSolicitation(Clinica clinic, Long medic_id) {
        // Buscar a solicitação no banco
        Optional<ClinicaMedico> solicitation = this.find(medic_id, clinic.getId());
    
        if (solicitation.isEmpty()) {
            throw new RuntimeException("Solicitação de vínculo não encontrada.");
        }
        this.delete(solicitation.get());
        return true;
    }

    public List<ClinicaMedico> findByStatus(Clinica clinic, boolean status){
        return this.clinicaMedicoRepository.findByClinicaIdAndAprovado(clinic.getId(), status);
    }
    public List<ClinicaMedico> findByClinica(Clinica clinic){
        return this.clinicaMedicoRepository.findByClinicaId(clinic.getId());
    }

    
}
