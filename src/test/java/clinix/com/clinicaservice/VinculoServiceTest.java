package clinix.com.clinicaservice;

import clinix.com.clinicaservice.model.Clinica;
import clinix.com.clinicaservice.model.ClinicaMedico;
import clinix.com.clinicaservice.repository.ClinicaMedicoRepository;
import clinix.com.clinicaservice.service.VinculoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VinculoServiceTest {

    @Mock
    private ClinicaMedicoRepository clinicaMedicoRepository;

    @InjectMocks
    private VinculoService vinculoService;

    private ClinicaMedico clinicaMedico;

    private Clinica clinica;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Clinica clinica = new Clinica();
        clinica.setId(1L);

        clinicaMedico = new ClinicaMedico();
        clinicaMedico.setId(100L);
        clinicaMedico.setClinica(clinica);
        clinicaMedico.setMedicoId(50L);
    }

    @Test
    void shouldReturnClinicaMedicoWhenExists() {
        when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
                .thenReturn(Optional.of(clinicaMedico));

        Optional<ClinicaMedico> result = vinculoService.find(50L, 1L);

        assertTrue(result.isPresent());
        assertEquals(clinicaMedico, result.get());
        verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    }

    @Test
    @Description("Retorna um Optional Vazio quando um vínculo não é encontrado")
    void shouldReturnEmptyWhenNoClinicaMedicoFound() {
        when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(99L, 2L))
                .thenReturn(Optional.empty());

        Optional<ClinicaMedico> result = vinculoService.find(99L, 2L);

        assertFalse(result.isPresent());
        verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(99L, 2L);
    }

    @Test
    void shouldReturnClinicaMedicoWhenBothExist() {
        when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
                .thenReturn(Optional.of(clinicaMedico));

        Optional<ClinicaMedico> result = vinculoService.find(50L, 1L);

        assertTrue(result.isPresent());
        assertEquals(clinicaMedico, result.get());
        verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    }

    @Test
    void shouldThrowExceptionWhenVincularWithNonExistentIds() {
        when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(99L, 2L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vinculoService.vincular(99L, 2L));
        verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(99L, 2L);
    }

    @Test
    void shouldNotAllowVincularWhenAlreadyApproved() {
        ClinicaMedico clinicaMedico = new ClinicaMedico();
        clinicaMedico.setId(100L);
        clinicaMedico.setClinica(clinica);
        clinicaMedico.setMedicoId(50L);
        clinicaMedico.setAprovado(true);
    
        when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
                .thenReturn(Optional.of(clinicaMedico));
    
        assertThrows(RuntimeException.class, () -> vinculoService.vincular(50L, 1L));
        verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    }

@Test
void shouldThrowExceptionWhenDesvincularWithNonExistentIds() {
    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(99L, 2L))
            .thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> vinculoService.desvincular(99L, 2L));
    verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(99L, 2L);
}

@Test
void shouldNotDesvincularWhenRequestNotApproved() {
    // Given
    ClinicaMedico clinicaMedico = new ClinicaMedico();
    clinicaMedico.setId(100L);
    clinicaMedico.setClinica(clinica);
    clinicaMedico.setMedicoId(50L);
    clinicaMedico.setAprovado(false);

    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
            .thenReturn(Optional.of(clinicaMedico));

    // When
    assertThrows(RuntimeException.class, () -> vinculoService.desvincular(50L, 1L));

    // Then
    verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    verify(clinicaMedicoRepository, never()).delete(any());
}

@Test
void shouldVerifyResponseWhenDesvincularWithValidIdsAndClinicaMedicoIsAprovado() {
    // Given
    ClinicaMedico clinicaMedico = new ClinicaMedico();
    clinicaMedico.setId(100L);
    clinicaMedico.setClinica(clinica);
    clinicaMedico.setMedicoId(50L);
    clinicaMedico.setAprovado(true);

    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
            .thenReturn(Optional.of(clinicaMedico));

    // When
    ClinicaMedico result = vinculoService.desvincular(50L, 1L);

    // Then
    assertNotNull(result);
    assertEquals(clinicaMedico, result);
    verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    verify(clinicaMedicoRepository, times(1)).delete(clinicaMedico);
}


@Test
void shouldDesvincularClinicaMedicoWhenItHasPendingSolicitacoes() {
    // Given
    ClinicaMedico clinicaMedico = new ClinicaMedico();
    clinicaMedico.setId(100L);
    clinicaMedico.setClinica(clinica);
    clinicaMedico.setMedicoId(50L);
    clinicaMedico.setAprovado(false);

    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(50L, 1L))
            .thenReturn(Optional.of(clinicaMedico));

    // When
    ClinicaMedico result = vinculoService.desvincular(50L, 1L);

    // Then
    assertNotNull(result);
    assertEquals(clinicaMedico, result);
    verify(clinicaMedicoRepository, times(1)).findByMedicoIdAndClinicaId(50L, 1L);
    verify(clinicaMedicoRepository, times(1)).delete(clinicaMedico);
}

@Test
public void shouldThrowExceptionWhenSolicitateMethodIsCalledWithNullClinic() {
    // given
    Long medic_id = 1L;
    Clinica clinic = null;

    // when
    Throwable exception = assertThrows(RuntimeException.class,
            () -> vinculoService.solicitate(clinic, medic_id));

    // then
    assert (exception.getMessage().equals("clinic cannot be null"));
}
@Test
public void shouldCreateNewClinicaMedicoWhenSolicitateMethodIsCalledWithValidClinicAndMedicId() {
    // given
    Long clinicId = 1L;
    Long medicId = 1L;
    Clinica clinic = new Clinica();
    clinic.setId(clinicId);

    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicId, clinicId)).thenReturn(Optional.empty());
    when(clinicaMedicoRepository.save(any(ClinicaMedico.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // when
    ClinicaMedico returnedClinicaMedico = vinculoService.solicitate(clinic, medicId);

    // then
    assertNotNull(returnedClinicaMedico);
    assertEquals(clinicId, returnedClinicaMedico.getClinica().getId());
    assertEquals(medicId, returnedClinicaMedico.getMedicoId());
    verify(clinicaMedicoRepository).save(any(ClinicaMedico.class));
}

@Test
@DisplayName("Deve lançar uma RunTimeException quando uma solicitação de vínculo já existe")
public void shouldThrowExceptionWhenSolicitationAlreadyExists() {
    // given
    Clinica clinica = new Clinica();
    clinica.setId(1L);
    Long medicoId = 1L;

    Optional<ClinicaMedico> existingSolicitation = Optional.of(new ClinicaMedico(clinica, medicoId));
    when(clinicaMedicoRepository.findByMedicoIdAndClinicaId(medicoId, clinica.getId()))
            .thenReturn(existingSolicitation);

    // when
    Throwable exception = assertThrows(RuntimeException.class,
            () -> vinculoService.solicitate(clinica, medicoId));

    // then
    assert (exception.getMessage().equals("Já existe uma solicitação de vínculo para este médico."));
}

}
