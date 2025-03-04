package clinix.com.clinicaservice.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinix.com.clinicaservice.DTO.HorarioDTO;
import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.service.ClinicaService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/horario")
public class HoraryController {

    ClinicaService clinicaService;

    @Autowired
    public HoraryController ( ClinicaService clinicaService){
        this.clinicaService = clinicaService;
    }

    @PutMapping("/check/{id}")
    public boolean checkHorary(@PathVariable Long id, @RequestBody HorarioDTO horary_check) {
        return this.clinicaService.checkExpediente(id, horary_check);
    }

    @GetMapping("/clinica/{id}")
    public Clinica updateClinicHorary(@PathVariable Long id, @RequestBody HorarioDTO horary) {
        return this.clinicaService.updateClinicHorary(id, horary);
    }

    @GetMapping("/{c_id}/medico/{m_id}")
    public ClinicaMedico updateMedicHorary(@PathVariable Long c_id, @PathVariable Long m_id, @RequestBody HorarioDTO h_dto){
        return this.clinicaService.updateMedicHorary(c_id, m_id, h_dto);
        
    }

}
