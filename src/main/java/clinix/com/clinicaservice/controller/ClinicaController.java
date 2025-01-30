package clinix.com.clinicaservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public Clinica update(@RequestBody Clinica clinic) {
        return this.clinicaService.update(clinic);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Clinica clinic) {
        return this.clinicaService.remove(clinic);
    }

}
