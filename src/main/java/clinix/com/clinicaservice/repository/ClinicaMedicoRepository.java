package clinix.com.clinicaservice.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import clinix.com.clinicaservice.model.ClinicaMedico;

@Repository
public interface ClinicaMedicoRepository extends JpaRepository<ClinicaMedico, Long> {

    Optional<ClinicaMedico> findByMedicoIdAndClinicaId(Long medicoId, Long clinicaId);

    List<ClinicaMedico> findByClinicaIdAndAprovado(Long id, boolean status);

    List<ClinicaMedico> findByClinicaID(Long id);

    @Query("SELECT cm FROM ClinicaMedico cm WHERE cm.clinica.id = :clinicaId AND cm.startTime < :horarioAbertura")
    List<ClinicaMedico> findHorariosBeforeClinicOpenning(@Param("clinicaId") Long clinicaId,
            @Param("horarioAbertura") LocalTime horarioAbertura);

    @Query("SELECT cm FROM ClinicaMedico cm WHERE cm.clinica.id = :clinicaId AND cm.endTime > :horarioFechamento")
    List<ClinicaMedico> findHorariosAfterClosing(@Param("clinicaId") Long clinicaId,
            @Param("horarioFechamento") LocalTime horarioFechamento);

}
