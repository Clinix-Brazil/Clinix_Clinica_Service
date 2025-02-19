package clinix.com.clinicaservice.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_clinica_medico")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicaMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @ManyToOne
    @JoinColumn(name = "clinica_id", nullable = false)
    private Clinica clinica;

    private Boolean aprovado = false;

    ClinicaMedico( Clinica c, Long m ){
        this.clinica = c;
        this.medicoId = m;
    }
}
