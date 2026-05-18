package cl.plataforma_gimnasio.ms_evaluacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluaciones_fisicas")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Integer idEvaluacion;

    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @Column(name = "estatura_cm", nullable = false)
    private Integer estaturaCm;

    @Column(name = "porcentaje_grasa", nullable = false)
    private Double porcentajeGrasa;

    @Column(name = "observaciones_medicas", nullable = true, length = 255)
    private String observacionesMedicas;

    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDateTime fechaEvaluacion;
}