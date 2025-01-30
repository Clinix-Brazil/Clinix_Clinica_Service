package clinix.com.clinicaservice.model;


import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clinicas")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "clinica_medico",
        joinColumns = @JoinColumn(name = "clinica_id"),
        inverseJoinColumns = @JoinColumn(name = "medico_id")
    )
    private List<Medico> medicos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "clinica_horarios", joinColumns = @JoinColumn(name = "clinica_id"))
    private List<Horario> horariosAtendimento = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "clinica_especialidades", joinColumns = @JoinColumn(name = "clinica_id"))
    @Column(name = "especialidade")
    private List<String> especialidades = new ArrayList<>();

    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioComum> pacientes = new ArrayList<>();

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(min = 14, max = 14)
    @Column(unique = true)
    private String cnpj;

    @Embedded
    @NotNull
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comprovante_endereco_id", referencedColumnName = "id")
    private Document comprovanteEndereco;

    @NotBlank
    @Size(min = 10, max = 15)
    private String telefone;

    // Métodos de negócio
    public void atualizar(Clinica outraClinica) {
        this.nome = outraClinica.getNome();
        this.cnpj = outraClinica.getCnpj();
        this.endereco = outraClinica.getEndereco();
        this.telefone = outraClinica.getTelefone();
        this.tipo = outraClinica.getTipo();
    }

    public boolean vincular(Medico medico) {
        if (!this.medicos.contains(medico)) {
            this.medicos.add(medico);
            medico.getClinicas().add(this);
            return true;
        }
        return false;
    }

    public boolean desvincular(Medico medico) {
        if (this.medicos.contains(medico)) {
            this.medicos.remove(medico);
            medico.getClinicas().remove(this);
            return true;
        }
        return false;
    }

    public List<Medico> listarMedicos() {
        return new ArrayList<>(this.medicos);
    }

    public List<Agendamento> listarAgendamentos() {
        return this.pacientes.stream()
                .flatMap(p -> p.getAgendamentos().stream())
                .toList();
    }

    public List<Paciente> listarPacientes() {
        return this.pacientes.stream()
                .filter(p -> p instanceof Paciente)
                .map(p -> (Paciente) p)
                .toList();
    }
}