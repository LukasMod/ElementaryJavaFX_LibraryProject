package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.my.library.Main;

public class TopMenuButtonsController {

    //do tworzenia referencji i obsługi przyciskami zmian w borderPane trzeba odnieść się do Controllera wyżej
    //następnie settery trzeba stworzyć
    private MainController mainController;

    @FXML
    public void openLibrary() {
        System.out.println("openLibrary");
    }

    @FXML
    public void openListBooks() {
        System.out.println("openListBooks");
    }

    @FXML
    public void openStats() {
        System.out.println("openStats");
    }

    public TopMenuButtonsController setMainController(MainController mainController) {
        this.mainController = mainController;
        return this;
    }
}
