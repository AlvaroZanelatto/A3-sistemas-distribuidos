package aula.projconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ProjConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjConfigApplication.class, args);
    }

}
