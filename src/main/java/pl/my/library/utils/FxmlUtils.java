package pl.my.library.utils;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

//odpowiada za ładowanie fxmli
public class FxmlUtils {


    //ZAMIENIA:
    //    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/BorderPaneMain.fxml"));
    //    //podpinamy Bundle
    //    ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
    //        loader.setResources(bundle);
    //NA METODY:


    public static Pane fxmlLoader(String fxmlPath) {
        //  "/fxml/BorderPaneMain.fxml"

        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        try {
            return loader.load(); //zwraca załadowana formatkę, w tym przypadku Pane
        } catch (Exception e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        return null;
    }

    public static FXMLLoader getLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.setResources(getResourceBundle());
        return loader;   //zwraca całego loadera, nie tylko Pane, jak wyżej (potem można wyciągnąc z tego Controller)
    }

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("bundles.messages");
    }


}
