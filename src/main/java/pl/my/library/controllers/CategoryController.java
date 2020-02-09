package pl.my.library.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pl.my.library.modelFX.CategoryFX;
import pl.my.library.modelFX.CategoryModel;

public class CategoryController {

    @FXML
    public Button deleteCategoryButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<CategoryFX> categoryComboBox;

    private CategoryModel categoryModel;

    @FXML
    public void initialize() {
        this.categoryModel = new CategoryModel();
        this.categoryModel.init(); //inicjalizacja listy w ComboBox
        this.categoryComboBox.setItems(this.categoryModel.getCategoryFXObservableList()); //ustawienie listy
        initBindings();

    }

    private void initBindings() {
        this.addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        this.deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryFXObjectPropertyProperty().isNull());

    }

    public void addCategoryOnAction() {
        categoryModel.saveCategoryInDataBase(categoryTextField.getText());
        categoryTextField.clear();
    }

    public void onActionDeleteButton() {
        this.categoryModel.deleteCategoryByID();
    }

    public void onActionComboBox() {
        System.out.println("onActionComboBox onActionComboBox");
        this.categoryModel.setCategoryFXObjectProperty(  this.categoryComboBox.getSelectionModel().getSelectedItem());
      //pobiera to co zostało wybrane w ComboBox i dodaje to do listy propertiesów


    }
}
