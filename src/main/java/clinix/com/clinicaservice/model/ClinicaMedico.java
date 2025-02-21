package clinix.com.clinicaservice.model;

import java.time.LocalTime;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinica_id", nullable = false)
    
    @JsonBackReference
    private Clinica clinica;

    @Column(nullable = false)
    private Boolean aprovado = false;

    @Column(name = "inicio_atendimento", nullable = false)
    private LocalTime startTime;

    @Column(name = "fim_atendimento", nullable = false)
    private LocalTime endTime;

    public ClinicaMedico( Clinica c, Long m, LocalTime s, LocalTime e){
        this.clinica = c;
        this.medicoId = m;
        this.startTime = s;
        this.endTime = e;
    }
}
