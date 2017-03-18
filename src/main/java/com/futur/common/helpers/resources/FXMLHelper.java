package com.futur.common.helpers.resources;

import com.futur.common.helpers.StringHelper;
import com.futur.common.models.FXMLPair;
import com.google.common.base.Preconditions;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;

@SuppressWarnings("unused")
public final class FXMLHelper {

    private FXMLHelper() {
        StringHelper.throwNonInitializeable();
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url) {
        return loadNode(url, null);
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url,
                                 @NotNull final String bundleName,
                                 @NotNull final Locale locale) {
        return loadNode(url, ResourceBundle.getBundle(bundleName, locale));
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url,
                                 @Nullable final ResourceBundle resourceBundle) {
        return Preconditions.checkNotNull(executeSafe(() -> FXMLLoader.load(url, resourceBundle)));
    }

    @NotNull
    public static <C, N> FXMLPair<C, N> loadFXML(@NotNull final URL url) {
        return loadFXML(url, null);
    }

    @NotNull
    public static <C, N> FXMLPair<C, N> loadFXML(@NotNull final URL url,
                                                 @NotNull final String bundleName,
                                                 @NotNull final Locale locale) {
        return loadFXML(url, ResourceBundle.getBundle(bundleName, locale));
    }

    @NotNull
    public static <C, N> FXMLPair<C, N> loadFXML(@NotNull final URL url,
                                                 @Nullable final ResourceBundle resourceBundle) {
        return Preconditions.checkNotNull(executeSafe(() -> {
            @NotNull final FXMLLoader loader = new FXMLLoader(url, resourceBundle);
            @NotNull final N load = loader.load();
            @NotNull final C controller = loader.getController();
            return new FXMLPair<>(controller, load);
        }));
    }

}
