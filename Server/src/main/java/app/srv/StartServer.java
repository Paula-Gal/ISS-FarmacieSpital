package app.srv;

import app.services.IServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.rmi.RmiServiceExporter;

@SpringBootApplication
@ComponentScan("app")
@EntityScan("app")
public class StartServer {

    @Bean
    RmiServiceExporter exporter(IServices server) {
        Class<IServices> servicesInterface = IServices.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(servicesInterface);
        exporter.setService(server);
        exporter.setServiceName(servicesInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(StartServer.class, args);
    }
}
