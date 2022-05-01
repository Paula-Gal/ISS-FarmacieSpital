package app.client;

import app.client.controllers.LoginController;
import app.services.IServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class StartClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        IServices server = getServer();

        Scene loginSc = startLoginView(primaryStage, server);

        primaryStage.setScene(loginSc);
        primaryStage.show();
    }


    private IServices getServer() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        return (IServices) factory.getBean("services");
    }

    private Scene startLoginView(Stage stage, IServices services) throws IOException {

//        FXMLLoader loginView = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
//        loginView.setController(new LoginController());
        FXMLLoader loginView = new FXMLLoader();
        loginView.setLocation(getClass().getResource("/views/LoginView.fxml"));
        Parent loginRoot = loginView.load();
        LoginController loginController = loginView.getController();


        Scene main = new Scene(loginRoot);
        loginController.setServer(services, main);

        return main;
    }
}
