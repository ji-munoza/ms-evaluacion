package cl.plataforma_gimnasio.ms_evaluacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsEvaluacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEvaluacionApplication.class, args);
	}

}
