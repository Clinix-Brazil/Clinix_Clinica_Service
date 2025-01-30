package clinix.com.clinicaservice.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clinicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoClinica tipo;

    // Referência a Médicos (IDs externos)
    @ElementCollection
    @CollectionTable(name = "clinica_medicos", joinColumns = @JoinColumn(name = "clinica_id"))
    @Column(name = "medico_id")
    private List<Long> medicos = new ArrayList<>();

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura; // Horário de abertura da clínica

    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento; // Horário do encerramento das atividades da clínica

    @ElementCollection
    @CollectionTable(name = "clinica_especialidades", joinColumns = @JoinColumn(name = "clinica_id"))
    @Column(name = "especialidade")
    private List<String> especialidades = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
        name = "clinica_pacientes",
        joinColumns = @JoinColumn(name = "clinica_id")
    )
    @Column(name = "paciente_id")
    private List<Long> pacientes = new ArrayList<>(); // Lista de IDs dos pacientes que possuem algum vínculo com a clínica

    private String nomeFantasia;

    @Column(unique = true)
    private String cnpj;
    private String telefone;

    /*
    * TO DO
     * @Embedded
     * private Endereco endereco;
     */

    /*
     * TO DO
     * @OneToOne(cascade = CascadeType.ALL)
     * 
     * @JoinColumn(name = "comprovante_endereco_id", referencedColumnName = "id")
     * private Document comprovanteEndereco;
     */

    public void atualizar(Clinica outraClinica) {
        this.nomeFantasia = outraClinica.getNomeFantasia();
        this.cnpj = outraClinica.getCnpj();
        /*this.endereco = outraClinica.getEndereco(); */
        this.telefone = outraClinica.getTelefone();
        this.tipo = outraClinica.getTipo();
    }

    public boolean vincular(Long medico_id) {
        if (!this.medicos.contains(medico_id)) {
            this.medicos.add(medico_id);
            return true;
        }
        return false;
    }

    public boolean desvincular(Long medico_id) {
        if (this.medicos.contains(medico_id)) {
            this.medicos.remove(medico_id);
            return true;
        }
        return false;
    }

    public List<Long> listarMedicos() {
         return new ArrayList<>(this.medicos);
    }

    public List<Long> listarPacientes() {
        return new ArrayList<>(this.pacientes);
    }


}