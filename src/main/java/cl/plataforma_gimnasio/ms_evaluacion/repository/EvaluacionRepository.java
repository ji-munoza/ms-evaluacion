package cl.plataforma_gimnasio.ms_evaluacion.repository;

import cl.plataforma_gimnasio.ms_evaluacion.model.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

}