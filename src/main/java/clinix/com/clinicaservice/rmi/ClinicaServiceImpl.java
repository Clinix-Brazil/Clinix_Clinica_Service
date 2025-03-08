package clinix.com.clinicaservice.rmi;

import clinix.com.clinicaservice.repository.ClinicaRepository;
import com.clinix.api.dto.ClinicaRmiDTO;
import com.clinix.api.interfaces.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@Service
public class ClinicaServiceImpl extends UnicastRemoteObject implements ClinicaService {

    private final ClinicaRepository clinicaRepository;

    public ClinicaServiceImpl(ClinicaRepository clinicaRepository) throws RemoteException {
        super();
        this.clinicaRepository = clinicaRepository;
    }

    @Override
    public ClinicaRmiDTO getClinicaPorId(Long id) throws RemoteException {
        return clinicaRepository.findById(id)
                .map(clinica -> new ClinicaRmiDTO(clinica.getId(), clinica.getNomeFantasia()))
                .orElse(null);
    }
}
