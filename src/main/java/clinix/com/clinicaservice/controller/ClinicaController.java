package clinix.com.clinicaservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.service.ClinicaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/clinicas")
public class ClinicaController {
    ClinicaService clinicaService;

    @Autowired
    public ClinicaController( ClinicaService clinicaService){
        this.clinicaService = clinicaService;
    }

    @GetMapping()
    public List<Clinica> listar() {
        return this.clinicaService.findAll();
    }
    
    @PostMapping()
    public Clinica create(@RequestBody Clinica clinic) {    
        return this.clinicaService.create(clinic);
    }

    @GetMapping("/{id}")
    public Clinica index (@PathVariable Long id){
        return this.clinicaService.findById(id);
    }
    
    @PutMapping("/{id}")
    public Boolean update(@RequestBody Clinica clinic) {
        return this.clinicaService.update(clinic);
    }
    
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return this.clinicaService.remove(id);
    }

    @GetMapping("/patients/{id}")
    public List<Long> getPatients (@PathVariable Long id){
        return this.clinicaService.getPatients(id);
    }

    @GetMapping("/medics/{id}")
    public List<Long> getMedics (@PathVariable Long id){
        return this.clinicaService.getMedics(id);
    }
    
}
