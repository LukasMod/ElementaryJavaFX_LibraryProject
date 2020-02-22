package pl.my.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import pl.my.library.modelFX.CategoryFx;
import pl.my.library.modelFX.CategoryModel;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class CategoryController {

    @FXML
    public Button deleteCategoryButton;
    @FXML
    public Button editCategoryButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private TextField categoryTextField;
    @FXML
    private ComboBox<CategoryFx> categoryComboBox;

    @FXML
    private TreeView<String> categoryTreeView;

    private CategoryModel categoryModel;

    @FXML
    public void initialize() {
        this.categoryModel = new CategoryModel();
        try {
            this.categoryModel.init(); //inicjalizacja listy w ComboBox
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        this.categoryComboBox.setItems(this.categoryModel.getCategoryFxObservableList()); //ustawienie listy
        this.categoryTreeView.setRoot(this.categoryModel.getRoot());
        initBindings();

    }

    private void initBindings() {
        this.addCategoryButton.disableProperty().bind(categoryTextField.textProperty().isEmpty());
        this.deleteCategoryButton.disableProperty().bind(this.categoryModel.categoryFXObjectPropertyProperty().isNull());
        this.editCategoryButton.disableProperty().bind(this.categoryModel.categoryFXObjectPropertyProperty().isNull());

    }

    public void addCategoryOnAction() {
        try {
            categoryModel.saveCategoryInDataBase(categoryTextField.getText());
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        categoryTextField.clear();
    }

    public void onActionDeleteButton() {
        try {
            this.categoryModel.deleteCategoryByID();
        } catch (ApplicationException | SQLException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
    }

    public void onActionComboBox() {
        System.out.println("onActionComboBox onActionComboBox");
        this.categoryModel.setCategoryFXObjectProperty(this.categoryComboBox.getSelectionModel().getSelectedItem());
        //pobiera to co zostało wybrane w ComboBox i dodaje to do listy propertiesów
    }

    public void onActionEditButton() {
        //Tworzymy okienko, które zawiera starą wartość z listy
        String newCategoryName = DialogsUtils.editDialog(this.categoryModel.getCategoryFXObjectProperty().getName());
        //po naciśnięciu:
        if (newCategoryName != null) {
            this.categoryModel.getCategoryFXObjectProperty().setName(newCategoryName);
            try {
                this.categoryModel.updateCategoryInDataBase();
            } catch (ApplicationException e) {
                DialogsUtils.errorDialog(e.getMessage());
            }
        }
    }
}

