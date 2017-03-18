package com.futur;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.net.URL;
import java.util.Locale;

import static com.futur.common.helpers.resources.ResourcesHelper.getInternalUrl;
import static com.futur.common.helpers.resources.ResourcesHelper.loadNode;

public class ResourcesHelperTest extends Application {

    @Test
    public void LocaleTest() {
//        launch();
    }

    @Override
    public void start(@NotNull final Stage primaryStage) throws Exception {
        @NotNull final URL internalUrl = getInternalUrl("views/layout_main.fxml");
        @NotNull final Parent root = loadNode(internalUrl, "i18n", new Locale("ru", "RU"));

        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

}
