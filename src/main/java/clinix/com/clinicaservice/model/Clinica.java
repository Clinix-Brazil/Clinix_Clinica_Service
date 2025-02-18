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
import jakarta.persistence.Version;
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

    @Column(name="gerente_id", nullable = false)
    private Long gerenteId;
    
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

    @Version  // Controle de concorrência otimista
    private Integer version = 0;
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
        this.especialidades = outraClinica.getEspecialidades();
    }

    public Boolean vincular(Long medico_id) {
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

/*
 * 20:00 — 06:00
 * agendar para 05:00 true
 * agendar para 15:00 false
 *08:00 — 16:00 
 * agendar para 05:00 false
 * agendar para 15:00 true
 */
public boolean isDentroDoExpediente(LocalTime horario) {
    // Caso 1: Se o horário de abertura for menor que o de fechamento (funcionamento normal no mesmo dia)
    if (horarioAbertura.isBefore(horarioFechamento)) {
        return !horario.isBefore(horarioAbertura) && !horario.isAfter(horarioFechamento);
    }
    // Caso 2: Funcionamento atravessa a meia-noite (Ex: 20:00 - 06:00)
    return horario.isAfter(horarioAbertura) || horario.isBefore(horarioFechamento);
}

    public List<Long> listarMedicos() {
         return new ArrayList<>(this.medicos);
    }

    public List<Long> listarPacientes() {
        return new ArrayList<>(this.pacientes);
    }

    public Boolean isNull(){
        return false;
    }


    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoClinica tipo) {
        this.tipo = tipo;
    }

    /**
     * @return Long return the gerenteId
     */
    public Long getGerenteId() {
        return gerenteId;
    }

    /**
     * @param gerenteId the gerenteId to set
     */
    public void setGerenteId(Long gerenteId) {
        this.gerenteId = gerenteId;
    }

    /**
     * @return List<Long> return the medicos
     */
    public List<Long> getMedicos() {
        return medicos;
    }

    /**
     * @param medicos the medicos to set
     */
    public void setMedicos(List<Long> medicos) {
        this.medicos = medicos;
    }

    /**
     * @param especialidades the especialidades to set
     */
    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    /**
     * @param nomeFantasia the nomeFantasia to set
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return Integer return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

}