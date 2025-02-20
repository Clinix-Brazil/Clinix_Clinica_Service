package clinix.com.clinicaservice.model;

import java.time.LocalTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NullClinica extends Clinica{
    
    @Override
    public Long getId() {
        return 0l;
    }
    @Override
    public TipoClinica getTipo() {
        return TipoClinica.CLINICA_GERAL;
    }
    @Override
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

    @Override
    public Boolean isNull(){
        return true;
    }
}
