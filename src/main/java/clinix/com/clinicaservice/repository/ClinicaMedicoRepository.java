package clinix.com.clinicaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinix.com.clinicaservice.model.ClinicaMedico;

@Repository
public interface ClinicaMedicoRepository extends JpaRepository<ClinicaMedico, Long> {
    
    java.util.Optional<ClinicaMedico> findByMedicoIdAndClinicaId(Long medicoId, Long clinicaId);
}