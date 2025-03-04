package clinix.com.clinicaservice;

import clinix.com.clinicaservice.rmi.ClinicaServiceImpl;
import com.clinix.api.interfaces.ClinicaService;
import com.clinix.api.interfaces.UsuarioService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
public class ClinicaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaserviceApplication.class, args);
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			ClinicaService clinicaService = new ClinicaServiceImpl();
			registry.rebind("ClinicaService", clinicaService);
			System.out.println("Serviço RMI de Clínica registrado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
