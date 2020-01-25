package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class MainController {


    @FXML
    private BorderPane borderPane;

    @FXML  //wstrzyknięcie kontrollera
    private TopMenuButtonsController topMenuButtonsController;

@FXML
    private void initialize() {
    topMenuButtonsController.setMainController(this);
}


}
