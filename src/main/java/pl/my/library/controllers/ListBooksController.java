package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.my.library.modelFX.AuthorFx;
import pl.my.library.modelFX.BookFx;
import pl.my.library.modelFX.CategoryFx;
import pl.my.library.modelFX.ListBooksModel;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.exceptions.ApplicationException;

import java.time.LocalDate;

//wypisujemy elementy z .fxml
//analogiczne są w BookFx
//bindujemy - potrzebny jest ListBooksModel dla kontrolera (i związany z tym converter)
//aplikujemy w controller utworzoną ObservableList w ListBooksModel i metodę init
//bindowanie tabeli i kolumn z listą


public class ListBooksController {
    @FXML
    private TableView<BookFx> booksTableView;
    @FXML
    private TableColumn<BookFx, String> titleColumn;
    @FXML
    private TableColumn<BookFx, String> descColumn;
    @FXML
    private TableColumn<BookFx, AuthorFx> authorColumn;
    @FXML
    private TableColumn<BookFx, CategoryFx> categoryColumn;
    @FXML
    private TableColumn<BookFx, Number> ratingColumn;
    @FXML
    private TableColumn<BookFx, String> isbnColumn;
    @FXML
    private TableColumn<BookFx, LocalDate> releaseColumn;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private Button xButton;
    @FXML
    private Button xButton2;


    private ListBooksModel listBooksModel;

    @FXML
    void initialize() {
        this.listBooksModel = new ListBooksModel();
        try {
            this.listBooksModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        //bindowanie ComboBoxów
        this.categoryComboBox.setItems(this.listBooksModel.getCategoryFxObservableList());
        this.authorComboBox.setItems(this.listBooksModel.getAuthorFxObservableList());
        //powiązania "wyboru" z ComboBoxów. Wartość z wybrana w comboBOxie będzie przpisana do propertki
        this.listBooksModel.categoryFxObjectPropertyProperty().bind(this.categoryComboBox.valueProperty());
        this.listBooksModel.authorFxObjectPropertyProperty().bind(this.authorComboBox.valueProperty());


        //do tabeli podłączamy wszystkie elementy z listy z modelu
        this.booksTableView.setItems(this.listBooksModel.getBookFxObservableList());
        //teraz na każdej kolumnie ustawiamy formułę setCellValueFactory
        this.titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        this.descColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        this.ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());
        this.isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        this.releaseColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
        this.authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorFxObjectProperty());
        this.categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryFxObjectProperty());


    }

    public void filterOnActionComboBox() {
        this.listBooksModel.categoryFxObjectPropertyProperty().get();
    }
}

