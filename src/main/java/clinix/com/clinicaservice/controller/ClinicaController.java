package clinix.com.clinicaservice.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinix.com.clinicaservice.DTO.HorarioDTO;
import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.service.ClinicaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/clinic")
public class ClinicaController {
    ClinicaService clinicaService;

    @Autowired
    public ClinicaController( ClinicaService clinicaService){
        this.clinicaService = clinicaService;
    }

    @GetMapping("/list")
    public List<Clinica> listar() {
        return this.clinicaService.findAll();
    }
    @GetMapping("/{id}")
    public Clinica index (@PathVariable Long id){
        return this.clinicaService.findById(id);
    }

    @PostMapping("/register")
    public Clinica create(@RequestBody Clinica clinic) {    
        return this.clinicaService.create(clinic);
    }
    
    @PutMapping("/{id}")
    public Boolean update(@RequestBody Clinica clinic) {
        return this.clinicaService.update(clinic);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return this.clinicaService.remove(id);
    }

    @PutMapping("vinculate/{clinic_id}/{medic_id}")
    public Boolean vinculate(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.vinculateMedic(clinic_id, medic_id);
    }

    @PutMapping("desvinculate/{clinic_id}/{medic_id}")
    public boolean desvinculate(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.desvinculateMedic(clinic_id, medic_id);
    }

    @GetMapping("/patients/{id}")
    public List<Long> getPatients (@PathVariable Long id){
        return this.clinicaService.getPatients(id);
    }

    @GetMapping("/medics/{id}")
    public List<Long> getMedics (@PathVariable Long id){
        return this.clinicaService.getMedics(id);
    }

    @PutMapping("/horary/check/{id}")
    public boolean checkHorary(@PathVariable Long id, @RequestBody LocalTime horary_check) {
        return this.clinicaService.checkExpediente(id,horary_check);
    }
    @PutMapping("/horary/update/{id}")
    public boolean updateHorary(@PathVariable Long id, @RequestBody HorarioDTO horary) {
        return this.clinicaService.updateHorary(id,horary);
    }
    
}
