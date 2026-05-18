package cl.plataforma_gimnasio.ms_evaluacion.service;

import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionRequestDTO;
import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionResponseDTO;
import cl.plataforma_gimnasio.ms_evaluacion.exception.ResourceNotFoundException;
import cl.plataforma_gimnasio.ms_evaluacion.model.Evaluacion;
import cl.plataforma_gimnasio.ms_evaluacion.repository.EvaluacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public List<EvaluacionResponseDTO> obtenerTodos() {
        log.info("Iniciando obtencion de todas las evaluaciones");
        return evaluacionRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public EvaluacionResponseDTO obtenerPorId(Integer id) {
        log.info("Iniciando obtencion de evaluacion con ID: {}", id);
        return evaluacionRepository.findById(id)
                .map(this::convertirAResponseDTO)
                .orElseThrow(() -> {
                    log.error("Error al obtener evaluacion: El ID {} no existe.", id);
                    return new ResourceNotFoundException("La evaluacion con ID " + id + " no existe.");
                });
    }

    public EvaluacionResponseDTO guardar(EvaluacionRequestDTO dto) {
        log.info("Iniciando registro de nueva evaluacion fisica: Peso {}kg, Estatura {}cm", dto.getPesoKg(), dto.getEstaturaCm());

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setPesoKg(dto.getPesoKg());
        evaluacion.setEstaturaCm(dto.getEstaturaCm());
        evaluacion.setPorcentajeGrasa(dto.getPorcentajeGrasa());
        evaluacion.setObservacionesMedicas(dto.getObservacionesMedicas());
        evaluacion.setFechaEvaluacion(LocalDateTime.now());

        Evaluacion evaluacionGuardada = evaluacionRepository.save(evaluacion);
        log.info("Evaluacion guardada con exito. Nuevo ID asignado: {}", evaluacionGuardada.getIdEvaluacion());

        return convertirAResponseDTO(evaluacionGuardada);
    }

    public EvaluacionResponseDTO actualizar(Integer id, EvaluacionRequestDTO dto) {
        log.info("Iniciando actualizacion de evaluacion con ID: {}", id);

        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Error al actualizar: El ID {} no existe.", id);
                    return new ResourceNotFoundException("La evaluacion con ID " + id + " no existe.");
                });

        evaluacion.setPesoKg(dto.getPesoKg());
        evaluacion.setEstaturaCm(dto.getEstaturaCm());
        evaluacion.setPorcentajeGrasa(dto.getPorcentajeGrasa());
        evaluacion.setObservacionesMedicas(dto.getObservacionesMedicas());

        Evaluacion evaluacionActualizada = evaluacionRepository.save(evaluacion);
        log.info("Evaluacion con ID {} actualizada con exito.", id);

        return convertirAResponseDTO(evaluacionActualizada);
    }

    public void eliminar(Integer id) {
        log.warn("Iniciando eliminacion de evaluacion con ID: {}", id);

        if (!evaluacionRepository.existsById(id)) {
            log.error("Error al eliminar: No existe la evaluacion con ID: {}", id);
            throw new ResourceNotFoundException("La evaluacion con ID " + id + " no existe.");
        }

        evaluacionRepository.deleteById(id);
        log.info("Evaluacion con ID {} eliminada correctamente.", id);
    }

    private EvaluacionResponseDTO convertirAResponseDTO(Evaluacion evaluacion) {
        EvaluacionResponseDTO response = new EvaluacionResponseDTO();
        response.setIdEvaluacion(evaluacion.getIdEvaluacion());
        response.setPesoKg(evaluacion.getPesoKg());
        response.setEstaturaCm(evaluacion.getEstaturaCm());
        response.setPorcentajeGrasa(evaluacion.getPorcentajeGrasa());
        response.setObservacionesMedicas(evaluacion.getObservacionesMedicas());
        response.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
        return response;
    }
}