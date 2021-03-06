package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import pl.my.library.modelFX.AuthorFx;
import pl.my.library.modelFX.AuthorModel;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class AuthorController {


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addButton;
    @FXML
    private TableView<AuthorFx> authorTableView;
    @FXML
    private TableColumn<AuthorFx, String> nameColumn;
    @FXML
    private TableColumn<AuthorFx, String> surnameColumn;
    @FXML
    private MenuItem deleteMenuItem;

    private AuthorModel authorModel;

    @FXML
    void initialize() {
        //chcemy aby AuthorCotroller operował na 'authorFXObjectProperty' z AuhorModel
        this.authorModel = new AuthorModel();
        //inicjalizacja listy Authorów(TableView)
        try {
            this.authorModel.init();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        binding();
        bindingsTableView();


    }

    private void bindingsTableView() {
        //TableView:
        this.authorTableView.setItems(this.authorModel.getAuthorFxObservableList());
        //nasza kolumna name, za pomocą setCellValueFactory ma operować na nameProperty, które działa na AuthorFx
        //w ten sposób kolumny są zbindowane z propertkami z AuthorFx
        this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());

        //włączenie edytowalności kolumn
        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //podpinamy się pod zaznaczanie wiersza (już nie element kolumnowy)
        this.authorTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.authorModel.setAuthorFxObjectPropertyEdit(newValue);
        });
    }

    private void binding() {
        //this.authorModel.getAuthorFXObjectProperty();  -nie chcemy poszczególnych pól, a cały obiekt
        this.authorModel.authorFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.authorModel.authorFxObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty());
        this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));

        //MenuItem - wykorzystujemy, że jest propertka i elimijujemy wyjątek usuwania,
        //jeśli nic nie zaznaczono (brak autorów).
        //Jeżeli wypisany properties jest nullem, to nie ma zaznaczać, tym samym nie będzie menuContext
        this.deleteMenuItem.disableProperty().bind(this.authorTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    public void addAuthorOnAction() {
        try {
            this.authorModel.saveAuthorInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        //czyszczenie pola po dodaniu authora
        this.nameTextField.clear();
        this.surnameTextField.clear();
    }

    public void onEditCommitName(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        //getAuthorFxObjectPropertyEdit - wie jaki wiersz zaznaczono(podpięcia wiersza wyżej)
        // i zaciąga wprowadzoną nową wartość w komórce
        this.authorModel.getAuthorFxObjectPropertyEdit().setName(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<AuthorFx, String> authorFxStringCellEditEvent) {
        this.authorModel.getAuthorFxObjectPropertyEdit().setSurname(authorFxStringCellEditEvent.getNewValue());
        updateInDatabase();
    }

    private void updateInDatabase() {
        try {
            this.authorModel.saveAuthorEditInDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void deleteAuthorOnAction() {
        try {
            this.authorModel.deleteAuthorInDatabase();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }

    }
}
