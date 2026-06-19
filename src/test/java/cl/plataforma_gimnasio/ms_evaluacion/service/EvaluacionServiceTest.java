package cl.plataforma_gimnasio.ms_evaluacion.service;

import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionRequestDTO;
import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionResponseDTO;
import cl.plataforma_gimnasio.ms_evaluacion.exception.ResourceNotFoundException;
import cl.plataforma_gimnasio.ms_evaluacion.model.Evaluacion;
import cl.plataforma_gimnasio.ms_evaluacion.repository.EvaluacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias para EvaluacionService")
class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @InjectMocks
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacionMock;
    private EvaluacionRequestDTO evaluacionRequestDTO;

    @BeforeEach
    void setUp() {
        evaluacionMock = new Evaluacion();
        evaluacionMock.setIdEvaluacion(1);
        evaluacionMock.setPesoKg(75.5);
        evaluacionMock.setEstaturaCm(175);
        evaluacionMock.setPorcentajeGrasa(14.2);
        evaluacionMock.setObservacionesMedicas("Sano, apto para cardio de alta intensidad.");
        evaluacionMock.setFechaEvaluacion(LocalDateTime.now());

        evaluacionRequestDTO = new EvaluacionRequestDTO(75.5, 175, 14.2, "Sano, apto para cardio de alta intensidad.");
    }

    @Test
    @DisplayName("Debe retornar todas las evaluaciones registradas")
    void obtenerTodos_DebeRetornarListaDeEvaluaciones() {
        when(evaluacionRepository.findAll()).thenReturn(List.of(evaluacionMock));

        List<EvaluacionResponseDTO> resultado = evaluacionService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(75.5, resultado.get(0).getPesoKg());
        assertEquals(175, resultado.get(0).getEstaturaCm());
        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar una lista vacía cuando no existen evaluaciones")
    void obtenerTodos_DebeRetornarListaVacia_CuandoNoHayRegistros() {
        when(evaluacionRepository.findAll()).thenReturn(Collections.emptyList());

        List<EvaluacionResponseDTO> resultado = evaluacionService.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe retornar la evaluacion correspondiente cuando el ID existe")
    void obtenerPorId_DebeRetornarEvaluacion_CuandoIdExiste() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacionMock));

        EvaluacionResponseDTO resultado = evaluacionService.obtenerPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvaluacion());
        assertEquals(14.2, resultado.getPorcentajeGrasa());
        verify(evaluacionRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe al buscar")
    void obtenerPorId_DebeLanzarExcepcion_CuandoIdNoExiste() {
        when(evaluacionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> evaluacionService.obtenerPorId(99));
        verify(evaluacionRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Debe guardar una evaluacion de forma exitosa")
    void guardar_DebeRegistrarEvaluacionExitosamente() {
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacionMock);

        EvaluacionResponseDTO resultado = evaluacionService.guardar(evaluacionRequestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvaluacion());
        assertEquals(75.5, resultado.getPesoKg());
        assertNotNull(resultado.getFechaEvaluacion());
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

    @Test
    @DisplayName("Debe actualizar una evaluacion existente de forma exitosa")
    void actualizar_DebeModificarEvaluacion_CuandoIdExiste() {
        when(evaluacionRepository.findById(1)).thenReturn(Optional.of(evaluacionMock));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacionMock);

        EvaluacionResponseDTO resultado = evaluacionService.actualizar(1, evaluacionRequestDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvaluacion());
        verify(evaluacionRepository, times(1)).findById(1);
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe al actualizar")
    void actualizar_DebeLanzarExcepcion_CuandoIdNoExiste() {
        when(evaluacionRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> evaluacionService.actualizar(99, evaluacionRequestDTO));
        verify(evaluacionRepository, times(1)).findById(99);
        verify(evaluacionRepository, never()).save(any(Evaluacion.class));
    }

    @Test
    @DisplayName("Debe eliminar una evaluacion cuando el ID existe")
    void eliminar_DebeBorrarEvaluacion_CuandoIdExiste() {
        when(evaluacionRepository.existsById(1)).thenReturn(true);
        doNothing().when(evaluacionRepository).deleteById(1);

        assertDoesNotThrow(() -> evaluacionService.eliminar(1));
        verify(evaluacionRepository, times(1)).existsById(1);
        verify(evaluacionRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException cuando el ID no existe al eliminar")
    void eliminar_DebeLanzarExcepcion_CuandoIdNoExiste() {
        when(evaluacionRepository.existsById(99)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> evaluacionService.eliminar(99));
        verify(evaluacionRepository, times(1)).existsById(99);
        verify(evaluacionRepository, never()).deleteById(anyInt());
    }
}