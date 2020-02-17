package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.my.library.datbase.models.Book;
import pl.my.library.modelFX.AuthorFx;
import pl.my.library.modelFX.BookModel;
import pl.my.library.modelFX.CategoryFx;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.exceptions.ApplicationException;

public class BookController {
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private Slider ratingSlider;
    @FXML
    private TextField isbnTextField;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField titleTextField;
    @FXML
    private Button addButton;

    private BookModel bookModel;

    @FXML
    public void initialize() {
        this.bookModel = new BookModel();
        try {
            this.bookModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindings();
    }

    private void bindings() {
        //podłączenie ComboBoxów
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());


        //Propertki dla każdej pozycji
        this.bookModel.getBookFxObjectProperty().categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().authorFxObjectPropertyProperty().bind(this.authorComboBox.valueProperty());
        this.bookModel.getBookFxObjectProperty().titleProperty().bind(this.titleTextField.textProperty());
        this.bookModel.getBookFxObjectProperty().descriptionProperty().bind(this.descriptionTextArea.textProperty());
        this.bookModel.getBookFxObjectProperty().ratingProperty().bind(this.ratingSlider.valueProperty());
        this.bookModel.getBookFxObjectProperty().isbnProperty().bind(this.isbnTextField.textProperty());
        this.bookModel.getBookFxObjectProperty().releaseDateProperty().bind(this.releaseDatePicker.valueProperty());
    }


    public void addBookOnAction() {
        System.out.println(this.bookModel.getBookFxObjectProperty().toString());
    }
}
