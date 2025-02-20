package clinix.com.clinicaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.service.ClinicaService;


import java.util.List;

@RestController
@RequestMapping("/vinculos")
public class VinculationController {

    ClinicaService clinicaService;

    @Autowired
    public VinculationController(ClinicaService clinicaService) {
        this.clinicaService = clinicaService;
    }
    @GetMapping("/{clinic_id}")
    public List<Long> allMedics(@PathVariable Long clinic_id) {
        return this.clinicaService.getMedics(clinic_id);
    }
    
    @GetMapping("solicitacoes/{clinic_id}")
    public List<ClinicaMedico> allSolicitations(@PathVariable Long clinic_id) {
        return this.clinicaService.getSolicitations(clinic_id);
    }

    @PostMapping("solicitar/{clinic_id}/{medic_id}")
    public ClinicaMedico solicitateVinculation(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.solicitateVinculate(clinic_id, medic_id);
    }

    @DeleteMapping("recusar/{clinic_id}/{medic_id}")
    public boolean refuseVinculation(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.refuseSolicitation(clinic_id, medic_id);
    }

    @PutMapping("vincular/{clinic_id}/{medic_id}")
    public ClinicaMedico vinculate(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.vinculateMedic(clinic_id, medic_id);
    }

    @DeleteMapping("desvincular/{clinic_id}/{medic_id}")
    public ClinicaMedico desvinculate(@PathVariable Long clinic_id, @PathVariable Long medic_id) {
        return this.clinicaService.desvinculateMedic(clinic_id, medic_id);
    }

}
