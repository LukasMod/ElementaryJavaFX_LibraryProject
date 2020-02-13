package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.my.library.modelFX.AuthorModel;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.exceptions.ApplicationException;

public class AuthorController {


    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button addButton;

    private AuthorModel authorModel;

    @FXML
    void initialize() {
//chcemy aby AuthorCotroller operował na 'authorFXObjectProperty' z AuhorModel
        this.authorModel = new AuthorModel();
        //this.authorModel.getAuthorFXObjectProperty();  -nie chcemy poszczególnych pól, a cały obiekt
        this.authorModel.authorFXObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.authorModel.authorFXObjectPropertyProperty().get().surnameProperty().bind(this.surnameTextField.textProperty());
this.addButton.disableProperty().bind(this.nameTextField.textProperty().isEmpty().or(this.surnameTextField.textProperty().isEmpty()));
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
}
