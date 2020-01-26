package pl.my.library.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;
import java.util.ResourceBundle;

public class MainController {


    @FXML
    private BorderPane borderPane;

    @FXML  //wstrzyknięcie kontrollera
    private TopMenuButtonsController topMenuButtonsController;

    @FXML
    private void initialize() {
        topMenuButtonsController.setMainController(this);
    }

    public void setCenter(String fxmlPath) {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(parent); //ustawia w centralnym miejscu borderPane naszą formatkę
    }


    public void setModena() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    public void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }


    public void setAlwaysOnTop(ActionEvent event) {
        //musimy dostać się stąd do Stage
        Stage stage = (Stage) borderPane.getScene().getWindow();  //to jest Stage
        //aby można byłą "odklikać/cofnąć" metodę, używamy eventu
        boolean value = ((CheckMenuItem) event.getSource()).selectedProperty().get(); //pobiera wartość zaznaczenia
        stage.setAlwaysOnTop(value);

    }

    public void about(ActionEvent event) {
    }


    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }
}
