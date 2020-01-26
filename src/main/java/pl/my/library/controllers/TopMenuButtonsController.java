package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import pl.my.library.Main;

public class TopMenuButtonsController {

    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String LIST_BOOKS_FXML = "/fxml/ListBooks.fxml";
    public static final String STATS_FXML = "/fxml/Stats.fxml";
    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";
    //do tworzenia referencji i obsługi przyciskami zmian w borderPane trzeba odnieść się do Controllera wyżej
    //następnie settery trzeba stworzyć
    private MainController mainController;

    @FXML
    private ToggleGroup toggleButtons;


    @FXML
    public void openLibrary() {
        System.out.println("openLibrary");
        mainController.setCenter(LIBRARY_FXML);
    }

    @FXML
    public void openListBooks() {
        System.out.println("openListBooks");
        mainController.setCenter(LIST_BOOKS_FXML);
    }

    @FXML
    public void openStats() {
        System.out.println("openStats");
        mainController.setCenter(STATS_FXML);
    }

    @FXML
    public void addBook() {
        //ma zerować ustawienie toggle buttons
        if (toggleButtons.getSelectedToggle() != null) {   //sprawdza czy w grupie jest coś wybrane
            toggleButtons.getSelectedToggle().setSelected(false); //jeśli tak, to ustawiamy na 'niewybrany'
        }
        System.out.println("addBook");
        mainController.setCenter(ADD_BOOK_FXML);
    }


    public TopMenuButtonsController setMainController(MainController mainController) {
        this.mainController = mainController;
        return this;
    }
}
