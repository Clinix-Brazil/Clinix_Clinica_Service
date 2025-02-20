package clinix.com.clinicaservice.model;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
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

    @Column(name = "gerente_id", nullable = false)
    private Long gerenteId;

    // Referência a Médicos (IDs externos)
    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClinicaMedico> medicos_vinculos = new ArrayList<>();

    @Column(name = "horario_abertura")
    private LocalTime horarioAbertura; // Horário de abertura da clínica

    @Column(name = "horario_fechamento")
    private LocalTime horarioFechamento; // Horário do encerramento das atividades da clínica

    @ElementCollection
    @CollectionTable(name = "clinica_especialidades", joinColumns = @JoinColumn(name = "clinica_id"))
    @Column(name = "especialidade")
    private List<String> especialidades = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "clinica_pacientes", joinColumns = @JoinColumn(name = "clinica_id"))
    @Column(name = "paciente_id")
    private List<Long> pacientes = new ArrayList<>(); // Lista de IDs dos pacientes que possuem algum vínculo com a
                                                      // clínica

    private String nomeFantasia;

    @Column(unique = true)
    private String cnpj;
    private String telefone;

    @Version // Controle de concorrência otimista
    private Integer version = 0;

    public void atualizar(Clinica outraClinica) {
        this.nomeFantasia = outraClinica.getNomeFantasia();
        this.cnpj = outraClinica.getCnpj();

        this.telefone = outraClinica.getTelefone();
        this.tipo = outraClinica.getTipo();
        this.especialidades = outraClinica.getEspecialidades();
    }
/* 
    public ClinicaMedico encontrarVinculo(Long medicoId){

        return this.medicos_vinculos.stream()
        .filter( m -> m.getId().equals(medicoId))
        .findFirst()
        .orElse(null);
    
    } */ 

    public Boolean addSolicitacao(ClinicaMedico solicitacao) {
            return this.medicos_vinculos.add(solicitacao);
    }

    public boolean removerVinculo(ClinicaMedico vinculo){
        if (this.medicos_vinculos.contains(vinculo)){
            this.medicos_vinculos.remove(vinculo);
            return true;
        }
        return false;
    }

    public boolean isDentroDoExpediente(LocalTime horario) {
        // Caso 1: Se o horário de abertura for menor que o de fechamento (funcionamento
        // normal no mesmo dia)
        if (horarioAbertura.isBefore(horarioFechamento)) {
            return !horario.isBefore(horarioAbertura) && !horario.isAfter(horarioFechamento);
        }
        // Caso 2: Funcionamento atravessa a meia-noite (Ex: 20:00 - 06:00)
        return horario.isAfter(horarioAbertura) || horario.isBefore(horarioFechamento);
    }

    public List<ClinicaMedico> listarMedicos_vinculos() {
        return new ArrayList<>(this.medicos_vinculos);
    }

    public List<Long> listarPacientes() {
        return new ArrayList<>(this.pacientes);
    }

    public Boolean isNull() {
        return false;
    }

}