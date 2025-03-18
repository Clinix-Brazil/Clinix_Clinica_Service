package clinix.com.clinicaservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    private Optional<ClinicaMedico> get_Optional(Long medicoId, Long clinicaId) {
        return clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId);
    }
    @Cacheable(value = "vinculos", key = "#medicoId + '-' + #clinicaId")
    public ClinicaMedico find(Long medicoId, Long clinicaId) {
        return clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
                .orElseThrow(() -> new RuntimeException(" Solicitação de Vínculo não encontrada "));

    }
    public ClinicaMedico vincular(Long medicoId, Long clinicaId) {
        ClinicaMedico cm = this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
                .orElseThrow(() -> new RuntimeException(" Solicitação de Vínculo não encontrada "));
        if (!cm.getAprovado()) {
            cm.setAprovado(true);
            return this.save(cm);
        }
        throw new RuntimeException("Solitação já havia sido aprovada.");
    }

    public ClinicaMedico desvincular(Long medicoId, Long clinicaId) {
        ClinicaMedico cm = this.clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinicaId)
                .orElseThrow(() -> new RuntimeException(" Solicitação de Vínculo não encontrada "));
        if (cm != null) {
            this.delete(cm);
            return cm;
        }
        return null;
    }
    @Cacheable(value = "vinculos", key = "#cm.medicoId + '-' + #cm.clinica.id")
    @CachePut(value = "vinculos", key = "#cm.medicoId + '-' + #cm.clinica.id")
    public ClinicaMedico save(ClinicaMedico cm) {
        return this.clinicaMedicoRepository.save(cm);
    }
    @CacheEvict(value = "vinculos", key = "#cm.getMedicoId() + '-' + #cm.getClinica().getId()")
    private void delete(ClinicaMedico cm) {
        this.clinicaMedicoRepository.delete(cm);
    }

    /**
     * Cria uma solicitação de vínculo para um médico
     * 
     * @throws RunTimeException caso já exista uma solicitação de vínculo para o
     *                          mesmo médico e aquela clínica
     * @param clinic_id
     * @param medic_id
     * @return
     */
    public ClinicaMedico solicitate(Clinica clinic, Long medic_id) {
        // Verificar se já existe um vínculo ou solicitação para esse médico e clínica
        Optional<ClinicaMedico> existingSolicitation = this.get_Optional(medic_id, clinic.getId());

        if (existingSolicitation.isPresent()) {
            throw new RuntimeException("Já existe uma solicitação de vínculo para este médico.");
        }
        ClinicaMedico cm = new ClinicaMedico(clinic, medic_id);
        return this.save(cm);
    }

    public boolean refuseSolicitation(Clinica clinic, Long medic_id) {
        // Buscar a solicitação no banco
        Optional<ClinicaMedico> solicitation = this.get_Optional(medic_id, clinic.getId());

        if (solicitation.isEmpty()) {
            throw new RuntimeException("Solicitação de vínculo não encontrada.");
        }
        this.delete(solicitation.get());
        return true;
    }

    @Cacheable(value = "vinculosPorStatus", key = "#clinic.getId() + '-' + #status")
    public List<ClinicaMedico> findByStatus(Clinica clinic, boolean status) {
        return this.clinicaMedicoRepository.findByClinicaIdAndAprovado(clinic.getId(), status);
    }

    @Cacheable(value = "vinculosPorClinica", key = "#clinic.getId()")
    public List<ClinicaMedico> findByClinica(Clinica clinic) {
        return this.clinicaMedicoRepository.findByClinicaId(clinic.getId());
    }

}
