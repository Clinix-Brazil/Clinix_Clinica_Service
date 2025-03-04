package clinix.com.clinicaservice.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clinix.com.clinicaservice.DTO.HorarioDTO;
import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.model.HorarioTipo;
import clinix.com.clinicaservice.repository.ClinicaMedicoRepository;

@Service
public class HoraryService {
    private ClinicaMedicoRepository clinicaMedicoRepository;
    private VinculoService vinculoService;

    @Autowired
    public HoraryService(ClinicaMedicoRepository clinicaMedicoRepository, VinculoService vinculoService) {
        this.clinicaMedicoRepository = clinicaMedicoRepository;
        this.vinculoService = vinculoService;
    }

    public boolean checkExpediente(Clinica c, HorarioDTO horary_check) {
        boolean r1 = c.isDentroDoExpediente(horary_check.horaInicio());
        boolean r2 = c.isDentroDoExpediente(horary_check.horaFinal());
        return r1 && r2;

    }

    public boolean checkExpediente(Clinica c, LocalTime horary_check) {
        return c.isDentroDoExpediente(horary_check);
    }

    /**
     * Updates the working hours of a doctor associated with a specific clinic.
     *
     * @param c          the clinic for which the doctor's working hours are being
     *                   updated
     * @param m_id       the ID of the doctor whose working hours are being updated
     * @param new_horary the new working hours in the form of a {@link HorarioDTO}
     *                   object
     * @return the updated {@link ClinicaMedico} object
     * @throws RuntimeException if the new working hours are not within the clinic's
     *                          operating hours,
     *                          or if the doctor-clinic association is not found
     */
    public ClinicaMedico alterarHorarioMedico(Clinica c, Long m_id, HorarioDTO new_horary) {
        boolean check = this.checkExpediente(c, new_horary);
        if (!check) {
            throw new RuntimeException(
                    String.format("Horario inserido inválido, tente valores entre %t a %t ",
                            c.getHorarioAbertura(), c.getHorarioFechamento()));
        }
        ClinicaMedico cm = this.vinculoService.find(m_id, c.getId()).orElseThrow(() -> {
            throw new RuntimeException("Vinculo não encontrado");
        });
        cm.setStartTime(new_horary.horaInicio());
        cm.setStartTime(new_horary.horaFinal());
        return this.vinculoService.save(cm);
    }

    private ClinicaMedico atualizarHorario(ClinicaMedico cm, LocalTime newTime, HorarioTipo tipo) {
        if (tipo == HorarioTipo.START) {
            cm.setStartTime(newTime);
        } else if (tipo == HorarioTipo.END) {
            cm.setEndTime(newTime);
        }
        return vinculoService.save(cm);
    }

    public Clinica alterarHorarioClinica(Clinica c, HorarioDTO novoHorario) {

        // Atualiza os horários da clínica
        c.setHorarioAbertura(novoHorario.horaInicio());
        c.setHorarioFechamento(novoHorario.horaFinal());
        
        // Obtém todos os vínculos que são anteriores ao novo horário de inicio da clínica
        List<ClinicaMedico> medicosAntesHorarioInicio = this.clinicaMedicoRepository
        .findHorariosBeforeClinicOpenning(c.getId(), novoHorario.horaInicio());
        
        for (ClinicaMedico cm : medicosAntesHorarioInicio){
            this.atualizarHorario(cm, novoHorario.horaInicio(), HorarioTipo.START);
        }
        
        // Obtém todos os vínculos que são posteriores ao novo horário de fechamento da clínica
        List<ClinicaMedico> medicosDepoisHorarioFinal = this.clinicaMedicoRepository.findHorariosAfterClosing(c.getId(),
        novoHorario.horaFinal());
        for (ClinicaMedico cm : medicosDepoisHorarioFinal){
            this.atualizarHorario(cm, novoHorario.horaInicio(), HorarioTipo.END);
        }

        return c;
    }

}
