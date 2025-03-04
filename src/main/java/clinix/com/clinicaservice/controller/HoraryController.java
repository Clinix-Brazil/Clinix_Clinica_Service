package clinix.com.clinicaservice.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinix.com.clinicaservice.DTO.HorarioDTO;
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

    @GetMapping("/clinica/{id}")
    public String getHorary(@PathVariable Long id){
        /* to do */
        return new String();
    }

    @GetMapping("/{c_id}/medico/{m_id}")
    public String getHorary(@PathVariable Long c_id, @PathVariable Long m_id){
        /* to do */
        return new String();
    }

    @GetMapping("/clinica/{id}")
    public boolean updateHorary(@PathVariable Long id, @RequestBody HorarioDTO horary) {
        return this.clinicaService.updateHorary(id, horary);
    }

    @GetMapping("/{c_id}/medico/{m_id}")
    public String updateHorary(@PathVariable Long c_id, @PathVariable Long m_id){
        /* to do */
        return new String();
    }
    

    @PutMapping("/check/{id}")
    public boolean checkHorary(@PathVariable Long id, @RequestBody LocalTime horary_check) {
        return this.clinicaService.checkExpediente(id, horary_check);
    }

}
