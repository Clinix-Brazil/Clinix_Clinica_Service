package clinix.com.clinicaservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import clinix.com.clinicaservice.model.ClinicaMedico;



@Repository
public interface ClinicaMedicoRepository extends JpaRepository<ClinicaMedico, Long> {
    
    Optional<ClinicaMedico> findByMedicoIdAndClinicaId(Long medicoId, Long clinicaId);

    List<ClinicaMedico> findByClinicaIdAndAprovado(Long id, boolean status);
}