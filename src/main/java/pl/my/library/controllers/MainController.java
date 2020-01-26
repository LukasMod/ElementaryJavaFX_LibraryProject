package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
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


}
