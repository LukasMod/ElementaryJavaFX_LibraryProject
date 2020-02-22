package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        validation(); //binduje przycisk zapisu/dodania książki, aby nie działał bez wypełnienia wszystkich pól
    }

    private void validation() {
        this.addButton.disableProperty().bind(this.authorComboBox.valueProperty().isNull()
                .or(this.categoryComboBox.valueProperty().isNull()
                        .or(this.titleTextField.textProperty().isEmpty()
                                .or(this.descriptionTextArea.textProperty().isEmpty()
                                        .or(this.isbnTextField.textProperty().isEmpty()
                                                .or(this.releaseDatePicker.valueProperty().isNull()))))));
    }

    public void bindings() {  //upubliczniona, ponieważ używamy jej także do edycji książek w formatce ListBooks
        //podłączenie ComboBoxów
        this.categoryComboBox.setItems(this.bookModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.bookModel.getAuthorFxObservableList());


        //Propertki dla każdej pozycji   - POŹNIEJ PODMIANA NA OBUSTRONNE BINDOWANIE + odwrócone wartości(poniżej)
//        this.bookModel.getBookFxObjectProperty().categoryFxObjectProperty().bind(this.categoryComboBox.valueProperty());
//        this.bookModel.getBookFxObjectProperty().authorFxObjectProperty().bind(this.authorComboBox.valueProperty());
//        this.bookModel.getBookFxObjectProperty().titleProperty().bind(this.titleTextField.textProperty());
//        this.bookModel.getBookFxObjectProperty().descriptionProperty().bind(this.descriptionTextArea.textProperty());
//        this.bookModel.getBookFxObjectProperty().ratingProperty().bind(this.ratingSlider.valueProperty());
//        this.bookModel.getBookFxObjectProperty().isbnProperty().bind(this.isbnTextField.textProperty());
//        this.bookModel.getBookFxObjectProperty().releaseDateProperty().bind(this.releaseDatePicker.valueProperty());


        this.categoryComboBox.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().categoryFxObjectProperty());
        this.authorComboBox.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().authorFxObjectProperty());
        this.titleTextField.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().titleProperty());
        this.descriptionTextArea.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().descriptionProperty());
        this.ratingSlider.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().ratingProperty());
        this.isbnTextField.textProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().isbnProperty());
        this.releaseDatePicker.valueProperty().bindBidirectional(this.bookModel.getBookFxObjectProperty().releaseDateProperty());

    }


    public void addBookOnAction() {
        try {
            this.bookModel.saveBookInDatabase();
            clearFields(); //czyści pola
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    private void clearFields() {
        this.categoryComboBox.getSelectionModel().clearSelection();
        this.authorComboBox.getSelectionModel().clearSelection();
        this.titleTextField.clear();
        this.descriptionTextArea.clear();
        this.ratingSlider.setValue(1);
        this.isbnTextField.clear();
        this.releaseDatePicker.getEditor().clear();
    }

    public BookModel getBookModel() {
        return bookModel;
    }
}
