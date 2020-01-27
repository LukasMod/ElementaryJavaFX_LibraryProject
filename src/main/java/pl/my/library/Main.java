package pl.my.library;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.my.library.utils.FxmlUtils;

import java.util.Locale;

public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //ustawienie lokalizacji
        Locale.setDefault(new Locale("pl"));

        Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
        stage.show();
    }
}
