package clinix.com.clinicaservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clinix.com.clinicaservice.model.Clinica;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicaRepository extends JpaRepository<Clinica,Long> {

    Optional<Clinica> findByNomeFantasia(String nomeFantasia);

}
