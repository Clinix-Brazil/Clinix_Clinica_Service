package clinix.com.clinicaservice.model;

import java.security.Timestamp;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NullClinica extends Clinica{
    
    @Override
    public Long getId() {
        return 0l;
    }
    public TipoClinica getTipo() {
        return TipoClinica.CLINICA_GERAL;
    }
    public String getCnpj() {
        return "83.803.667/0001-50";
    }
    @Override
    public String getTelefone() {
        return "(00) 10000-0000";
    }

    @Override
    public LocalTime getHorarioAbertura(){
        return LocalTime.now();
    } 

    @Override
    public LocalTime getHorarioFechamento(){
        return LocalTime.now();
    } 

    public List<Long> getMedicos() {
        return Collections.emptyList(); // Lista vazia
    }

    public List<String> getEspecialidades() {
        return Collections.emptyList(); // Lista vazia
    }
}
