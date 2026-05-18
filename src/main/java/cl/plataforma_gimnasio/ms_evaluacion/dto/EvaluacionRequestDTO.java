package cl.plataforma_gimnasio.ms_evaluacion.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionRequestDTO {

    @NotNull(message = "El peso es obligatorio.")
    @Min(value = 30, message = "El peso minimo registrado debe ser de 30 kg.")
    @Max(value = 250, message = "El peso maximo registrado no puede exceder los 250 kg.")
    private Double pesoKg;

    @NotNull(message = "La estatura es obligatoria.")
    @Min(value = 100, message = "La estatura minima registrada debe ser de 100 cm.")
    @Max(value = 250, message = "La estatura maxima registrada no puede exceder los 250 cm.")
    private Integer estaturaCm;

    @NotNull(message = "El porcentaje de grasa es obligatorio.")
    @Min(value = 2, message = "El porcentaje minimo de grasa debe ser de 2%.")
    @Max(value = 60, message = "El porcentaje maximo de grasa no puede superar el 60%.")
    private Double porcentajeGrasa;

    @Size(max = 255, message = "Las observaciones medicas no pueden superar los 255 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ.,;\\s]*$", message = "Las observaciones solo pueden contener letras, numeros, espacios, puntos, comas o punto y coma.")
    private String observacionesMedicas;
}