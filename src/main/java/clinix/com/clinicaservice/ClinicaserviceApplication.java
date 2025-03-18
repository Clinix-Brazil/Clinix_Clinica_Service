package clinix.com.clinicaservice;

import clinix.com.clinicaservice.rmi.ClinicaServiceImpl;
import com.clinix.api.interfaces.ClinicaService;
import com.clinix.api.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
@EnableCaching
public class ClinicaserviceApplication {

    @Autowired
    private static ClinicaService clinicaService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClinicaserviceApplication.class, args);
        clinicaService = context.getBean(ClinicaServiceImpl.class);
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("ClinicaService", clinicaService);
            System.out.println("Serviço RMI de Clínica registrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
