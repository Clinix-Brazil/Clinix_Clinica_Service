package clinix.com.clinicaservice;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.NullClinica;
import clinix.com.clinicaservice.model.TipoClinica;
import clinix.com.clinicaservice.repository.ClinicaRepository;
import clinix.com.clinicaservice.service.ClinicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClinicaServiceTest {

    @Mock
    private ClinicaRepository clinicaRepository;

    @InjectMocks
    private ClinicaService clinicaService;

    @Captor
    private ArgumentCaptor<Long> clinicaIdCaptor;

    private static final Logger logger = LoggerFactory.getLogger(ClinicaServiceTest.class);

    private Clinica clinica1;
    private Clinica clinica2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        clinica1 = new Clinica();
        clinica1.setId(1l);
        clinica1.setNomeFantasia("Teste");
        clinica1.setTipo(TipoClinica.ESPECIALIZADA);
        clinica1.setGerenteId(1l);
        clinica1.setCnpj("9495685958");
        
        this.clinica2 = new Clinica();
        clinica2.setId(2l);
        clinica2.setNomeFantasia("Teste");
        clinica2.setTipo(TipoClinica.ESPECIALIZADA);
        clinica2.setGerenteId(1l);
        clinica2.setCnpj("94956712");

    }

    @Test
    @Description("Deve retornar a clínica através de um detemrinado Id")
    public void shouldReturnCorrectClinicaObjectWhenIdIsValid() {
        when(clinicaRepository.findById(1l)).thenReturn(Optional.of(clinica1));

        Clinica returnedClinica = clinicaService.findById(1L);

        logger.info("Clinica retornada: {}", returnedClinica);
        assertEquals(clinica1, returnedClinica);

    }

    @Test
    @DisplayName("Deve lançar uma RunTimeException quando o nome fantasia não é encontrado no banco de dados")
    public void shouldReturnDefaultClinicaWhenNomeFantasiaNotFound() {
        // given
        String nonExistentNomeFantasia = "ClinicaInexistente";
        when(clinicaRepository.findByNomeFantasia(nonExistentNomeFantasia)).thenReturn(Optional.empty());

        // when
        Throwable exception = assertThrows(RuntimeException.class,
                () -> clinicaService.findByNomeFantasia(nonExistentNomeFantasia));

        // then
        assert (exception.getMessage().equals("Clínica Não Encontrada"));
    }

    @Test
    @DisplayName("Deve retornar uma RunTimeException quando não achar o Id de uma clínica quando for remover")
    public void shouldThrowExceptionWhenClinicaIdNotFound() {
        // given
        Long clinicaId = 1L;
        when(clinicaRepository.findById(clinicaId)).thenReturn(Optional.empty());

        // when
        Throwable exception = assertThrows(RuntimeException.class, () -> clinicaService.findById(clinicaId));

        // then
        assert (exception.getMessage().equals("Clínica Não Encontrada"));
    }

    @Test
    @DisplayName("Deve retornar a clínica correta quando há várias clínicas com o mesmo nomeFantasia")
    public void shouldReturnCorrectClinicaObjectWhenMultipleClinicasHaveSameNomeFantasia() {
        // given
        String nomeFantasia = "ClinicaExistente";
        when(clinicaRepository.findByNomeFantasia(nomeFantasia)).thenReturn(Optional.of(clinica1));

        // when
        Clinica returnedClinica = clinicaService.findByNomeFantasia(nomeFantasia);

        // then
        assertEquals(clinica1, returnedClinica);
    }

    @Test
    @Description("Deve retornar false ao remover uma clínica que não existe")
    public void shouldReturnFalseWhenRemovingNonExistentClinica() {
        // given
        Long nonExistentClinicaId = 999L;
        when(clinicaRepository.findById(nonExistentClinicaId)).thenReturn(Optional.empty());

        // when
        boolean result = clinicaService.remove(nonExistentClinicaId);

        // then
        assertThat(result, is(false));
    }

    @Test
    @DisplayName("Deve Verificar Se a Clínica foi removida do Banco de Dados")
    public void shouldVerifyDatabaseInteractionWhenRemovingClinica() {
        // given
        Long clinicaId = 1L;
        when(clinicaRepository.findById(clinicaId)).thenReturn(Optional.of(clinica1));

        // when
        boolean result = clinicaService.remove(clinicaId);

        // then
        assertTrue(result);
        verify(clinicaRepository).deleteById(clinicaIdCaptor.capture());
        assertEquals(clinicaId, clinicaIdCaptor.getValue());
    }

    

    
}