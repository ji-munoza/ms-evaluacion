package cl.plataforma_gimnasio.ms_evaluacion.controller;

import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionRequestDTO;
import cl.plataforma_gimnasio.ms_evaluacion.dto.EvaluacionResponseDTO;
import cl.plataforma_gimnasio.ms_evaluacion.service.EvaluacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gimnasio/evaluaciones")
@RequiredArgsConstructor
@Slf4j
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDTO>> obtenerTodos() {
        log.info("Solicitud recibida para obtener todas las evaluaciones");
        List<EvaluacionResponseDTO> evaluaciones = evaluacionService.obtenerTodos();
        if (evaluaciones.isEmpty()) {
            log.warn("No se obtuvo ninguna evaluacion, la lista esta vacia");
            return ResponseEntity.noContent().build();
        }
        log.info("Obtenidas {} evaluaciones", evaluaciones.size());
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDTO> obtenerPorId(@PathVariable Integer id) {
        log.info("Solicitud recibida para obtener evaluacion con ID: {}", id);
        EvaluacionResponseDTO evaluacion = evaluacionService.obtenerPorId(id);
        log.info("Obtenida evaluacion con ID: {}", id);
        return ResponseEntity.ok(evaluacion);
    }

    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> guardar(@Valid @RequestBody EvaluacionRequestDTO dto) {
        log.info("Solicitud recibida para crear evaluacion fisica. Peso: {}kg", dto.getPesoKg());
        EvaluacionResponseDTO nuevaEvaluacion = evaluacionService.guardar(dto);
        log.info("Evaluacion creada exitosamente con ID: {}", nuevaEvaluacion.getIdEvaluacion());
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEvaluacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponseDTO> actualizar(@PathVariable Integer id, @Valid @RequestBody EvaluacionRequestDTO dto) {
        log.info("Solicitud recibida para actualizar evaluacion con ID: {}", id);
        EvaluacionResponseDTO evaluacionActualizada = evaluacionService.actualizar(id, dto);
        log.info("Evaluacion con ID {} modificada exitosamente.", id);
        return ResponseEntity.ok(evaluacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.warn("Solicitud recibida para eliminar evaluacion con ID: {}", id);
        evaluacionService.eliminar(id);
        log.info("Eliminada evaluacion con ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}