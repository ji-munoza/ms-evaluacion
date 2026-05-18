package cl.plataforma_gimnasio.ms_evaluacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionResponseDTO {
    private Integer idEvaluacion;
    private Double pesoKg;
    private Integer estaturaCm;
    private Double porcentajeGrasa;
    private String observacionesMedicas;
    private LocalDateTime fechaEvaluacion;
}