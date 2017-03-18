package com.futur.common.helpers.resources;

import com.futur.common.annotations.PrepareURL;
import com.futur.common.helpers.StringHelper;
import com.futur.common.models.FXMLPair;
import com.futur.common.models.URLFile;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressWarnings("unused")
public final class ResourcesHelper {

    @NotNull
    private static final Logger LOG = LoggerFactory.getLogger(ResourcesHelper.class);

    @NotNull
    private static final ClassLoader classLoader = ResourcesHelper.class.getClassLoader();

    @NotNull
    private static final ImmutableList<String> internalLocations = ImmutableList.of("", "resources/");

    @NotNull
    private static final ImmutableList<String> externalLocations = ImmutableList.of(
            "",
            "resources/",
            "../res/",
            "externalResources/");

    private ResourcesHelper() {
        StringHelper.throwNonInitializeable();
    }

    public static void checkURL(@NotNull final Class<?> clazz) {
        @NotNull final Field[] fields = clazz.getFields();
        for (@NotNull final Field field : fields) {
            if (field.isAnnotationPresent(PrepareURL.class)) {
                try {
                    Preconditions.checkNotNull(field.get(null));
                } catch (Throwable e) {
                    LOG.error(e.getCause().toString());
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static URL getInternalUrl(@NotNull final String resourceName) {
        return checkNotNull(((Supplier<URL>) () -> {
            for (@NotNull final String location : internalLocations) {
                @Nullable final URL url = classLoader.getResource(location + resourceName);
                if (url != null) {
                    LOG.debug("Internal resource founded: {}", url);
                    return url;
                }
            }

            LOG.info("Internal resource not founded: {}", resourceName);
            return null;
        }).get(), "Not founded resourceName = " + resourceName);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static URL getExternalUrl(@NotNull final String resourceName,
                                     @NotNull final Class resourceLocator) {
        return checkNotNull(getExternalUrlFile(resourceName, resourceLocator).getUrl());
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static URLFile getExternalUrlFile(@NotNull final String resourceName,
                                             @NotNull final Class resourceLocator) {
        return checkNotNull(((Supplier<URLFile>) () -> {
            @Nullable final File resourceFile = findExternalResourceFile(resourceName, resourceLocator);

            if (resourceFile == null) {
                return null;
            } else {
                return executeSafe(() -> {
                    @NotNull final URL url = new URL("file", "", -1, resourceFile.getAbsolutePath());
                    return new URLFile(url, resourceFile);
                });
            }
        }).get());
    }

    @Nullable
    private static File findExternalResourceFile(@NotNull final String resourceName,
                                                 @NotNull final Class resourceLocator) {
        @Nullable final Path basePath = findBaseExternalLocation(resourceLocator);

        if (basePath != null) {
            for (String location : externalLocations) {
                @NotNull final File file = basePath.resolve(location).resolve(resourceName).normalize().toFile();
                if (file.exists()) {
                    LOG.info("External resource founded: {}", file);
                    return file;
                }
            }
        }

        LOG.info("External resource not founded: {}", resourceName);
        return null;
    }

    @Nullable
    private static Path findBaseExternalLocation(@NotNull final Class resourceLocator) {
        @Nullable final CodeSource codeSource = resourceLocator.getProtectionDomain().getCodeSource();

        if (codeSource == null) {
            return null;
        } else {
            return executeSafe(() -> Paths.get(codeSource.getLocation().toURI()).getParent());
        }
    }

    @NotNull
    public static <C, N> FXMLPair<C, N> loadFXML(@NotNull final URL url) {
        return Preconditions.checkNotNull(executeSafe(() -> {
            @NotNull final FXMLLoader loader = new FXMLLoader(url);
            @NotNull final N load = loader.load();
            @NotNull final C controller = loader.getController();
            return new FXMLPair<>(controller, load);
        }));
    }

    @NotNull
    public static <C, N> FXMLPair<C, N> loadFXML(@NotNull final URL url,
                                                 @NotNull final String bundleName,
                                                 @NotNull final Locale locale) {
        return Preconditions.checkNotNull(executeSafe(() -> {
            @NotNull final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundleName, locale);
            @NotNull final FXMLLoader loader = new FXMLLoader(url);
            loader.setResources(resourceBundle);
            @NotNull final N load = loader.load();
            @NotNull final C controller = loader.getController();
            return new FXMLPair<>(controller, load);
        }));
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url) {
        return Preconditions.checkNotNull(executeSafe(() -> FXMLLoader.load(url)));
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url,
                                 @NotNull final String bundleName,
                                 @NotNull final Locale locale) {
        return loadNode(url, ResourceBundle.getBundle(bundleName, locale));
    }

    @NotNull
    public static <T> T loadNode(@NotNull final URL url,
                                 @NotNull final ResourceBundle resourceBundle) {
        return Preconditions.checkNotNull(executeSafe(() -> {
            @NotNull final FXMLLoader loader = new FXMLLoader(url);
            loader.setResources(resourceBundle);
            return loader.load();
        }));
    }

}
