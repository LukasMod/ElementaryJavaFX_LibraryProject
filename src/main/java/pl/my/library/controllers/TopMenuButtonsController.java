package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class TopMenuButtonsController {

    public static final String LIBRARY_FXML = "/fxml/Library.fxml";
    public static final String LIST_BOOKS_FXML = "/fxml/ListBooks.fxml";
    public static final String STATS_FXML = "/fxml/Stats.fxml";
    public static final String ADD_BOOK_FXML = "/fxml/AddBook.fxml";
    public static final String ADD_CATEGORY_FXML = "/fxml/AddCategory.fxml";
    public static final String ADD_AUTHOR_FXML = "/fxml/AddAuthor.fxml";
    //do tworzenia referencji i obsługi przyciskami zmian w borderPane trzeba odnieść się do Controllera wyżej
    //następnie settery trzeba stworzyć
    private MainController mainController;

    @FXML
    private ToggleGroup toggleButtons;


    @FXML
    public void openLibrary() {
        mainController.setCenter(LIBRARY_FXML);
    }

    @FXML
    public void openListBooks() {
        mainController.setCenter(LIST_BOOKS_FXML);
    }

    @FXML
    public void openStats() {
        mainController.setCenter(STATS_FXML);
    }

    @FXML
    public void addBook() {
        //ma zerować ustawienie toggle buttons
        resetToggleButtons();
        mainController.setCenter(ADD_BOOK_FXML);
    }

    public void setMainController(MainController mainController) { this.mainController = mainController; }
    @FXML
    public void addCategory() {
        resetToggleButtons();
        mainController.setCenter(ADD_CATEGORY_FXML);
    }
    @FXML
    public void addAuthor() {
        resetToggleButtons();
        mainController.setCenter(ADD_AUTHOR_FXML);
    }

    private void resetToggleButtons() {
        if (toggleButtons.getSelectedToggle() != null) {   //sprawdza czy w grupie jest coś wybrane
            toggleButtons.getSelectedToggle().setSelected(false); //jeśli tak, to ustawiamy na 'niewybrany'
        }
    }

}
