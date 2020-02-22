package pl.my.library.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.my.library.modelFX.AuthorFx;
import pl.my.library.modelFX.BookFx;
import pl.my.library.modelFX.CategoryFx;
import pl.my.library.modelFX.ListBooksModel;
import pl.my.library.utils.DialogsUtils;
import pl.my.library.utils.FxmlUtils;
import pl.my.library.utils.exceptions.ApplicationException;

import java.io.IOException;
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
    private TableColumn<BookFx, BookFx> deleteColumn;
    @FXML
    private TableColumn<BookFx, BookFx> editColumn;

    @FXML
    private ComboBox<CategoryFx> categoryComboBox;
    @FXML
    private ComboBox<AuthorFx> authorComboBox;
    @FXML
    private Button clearAuthorButton;
    @FXML
    private Button clearCategoryButton;


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
        this.listBooksModel.categoryFxObjectPropertyModelProperty().bind(this.categoryComboBox.valueProperty());
        this.listBooksModel.authorFxObjectPropertyModelProperty().bind(this.authorComboBox.valueProperty());


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
        //musimy opakować nasz BookFx aby kolumna mogła pracować. Sam BookFx nie jest obiektem obserwowanym,
        //stąd potrzeba opakowania z użcyiem SimpleObjectProperty
        this.deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        //dodawanie przycisku.  Budujemy komórki w naszych kolumnach. Każda komórka pracuje na BookFx.
        this.deleteColumn.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            //każda komóka ma mieć przycisk:
            Button button = createButton("/icons/delete.png");

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                if (!empty) {  //gdy nie jest pusty, ustaw przycisk
                    //komórka jest NODE, więc możemy odwołać się do setGraphic
                    setGraphic(button);
                    setAlignment(Pos.CENTER);
                    button.setOnAction(event -> {
                        try {
                            listBooksModel.deleteBook(item);
                        } catch (ApplicationException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }
                    });
                }
            }
        });
        this.editColumn.setCellFactory(param -> new TableCell<BookFx, BookFx>() {
            //każda komóka ma mieć przycisk:
            Button button = createButton("/icons/edit.png");

            @Override
            protected void updateItem(BookFx item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                if (!empty) {  //gdy nie jest pusty, ustaw przycisk
                    //komórka jest NODE, więc możemy odwołać się do setGraphic
                    setGraphic(button);
                    setAlignment(Pos.CENTER);
                    button.setOnAction(event -> {
                        //dzieki loaderowi możemy załadować formatke i pobrać controller
                        FXMLLoader loader = FxmlUtils.getLoader("/fxml/AddBook.fxml");
                        Scene scene = null;
                        try {
                            scene = new Scene(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //odwołanie do Controllera
                        BookController controller = loader.getController();
                        controller.getBookModel().setBookFxObjectProperty(item);
                        controller.bindings(); //musieliśmy ustawić obustronne bindowanie,

                        //wyświetla formatkę
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);  //okno modalne będzie nad aplikacją
                        stage.showAndWait();

                    });
                }
            }
        });
    }

    //Tworzymy przycisk w całości w Javie
    private Button createButton(String path) {
        Button button = new Button();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        button.setGraphic(imageView);
        return button;
    }

    public void filterOnActionComboBox() {
        this.listBooksModel.filterBooksList();
    }

    public void clearCategoryComboBox() {
        this.categoryComboBox.getSelectionModel().clearSelection();
    }

    public void clearAuthorComboBox() {
        this.authorComboBox.getSelectionModel().clearSelection();
    }
}

