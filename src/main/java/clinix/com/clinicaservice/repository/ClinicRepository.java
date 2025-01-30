package clinix.com.clinicaservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clinix.com.clinicaservice.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic,Long> {

    Optional<Clinic> findByFantasyName(String fantasyName);

}
