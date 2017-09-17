package com.futur.common.helpers.resources;

import com.futur.common.annotations.PrepareURL;
import com.futur.common.helpers.DevelopmentHelper;
import com.futur.common.helpers.StringHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;
import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@SuppressWarnings("unused")
public final class ResourcesHelper {

    @NotNull
    private static final ClassLoader classLoader = ResourcesHelper.class.getClassLoader();

    @NotNull
    private static final ImmutableList<String> internalLocations = ImmutableList.of("", "resources/");

    @NotNull
    private static final ImmutableList<String> externalLocations = ImmutableList.of(
            "",
            "resources/",
            "../res/",
            "externalResources/",
            "libs/"
    );

    private ResourcesHelper() {
        StringHelper.throwNonInitializeable();
    }

    public static void checkURL(@NotNull final Class<?> clazz) {
        @NotNull val fields = clazz.getFields();
        for (@NotNull val field : fields) {
            if (field.isAnnotationPresent(PrepareURL.class)) {
                @NotNull val annotation = field.getAnnotation(PrepareURL.class);
                if (annotation.required()) {
                    try {
                        Preconditions.checkNotNull(field.get(null));
                    } catch (Throwable e) {
                        log.error(e.getCause().toString());
                    }
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static URL getInternalUrl(@NotNull final String resourceName) {
        return checkNotNull(findInternalUrl(resourceName), "Internal resource not founded: " + resourceName);
    }

    @Nullable
    private static URL findInternalUrl(@NotNull final String resourceName) {
        for (@NotNull val location : internalLocations) {
            @Nullable val url = classLoader.getResource(location + resourceName);
            if (url != null) {
                log.debug("Internal resource founded: {}", url);
                return url;
            }
        }

        return null;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    public static URL getExternalUrl(@NotNull final String resourceName,
                                     @NotNull final Class resourceLocator) {
        return checkNotNull(findExternalUrl(resourceName, resourceLocator));
    }

    @Nullable
    public static URL findExternalUrl(@NotNull final String resourceName,
                                      @NotNull final Class resourceLocator) {
        @Nullable final File resourceFile = findExternalResourceFile(resourceName, resourceLocator);

        return DevelopmentHelper.ifNotNull(resourceFile, ResourcesHelper::wrapFile);
    }

    @Nullable
    private static File findExternalResourceFile(@NotNull final String resourceName,
                                                 @NotNull final Class resourceLocator) {
        @Nullable val basePath = findBaseExternalLocation(resourceLocator);

        if (basePath != null) {
            for (@NotNull val location : externalLocations) {
                @NotNull val file = basePath.resolve(location).resolve(resourceName).normalize().toFile();
                if (file.exists()) {
                    log.info("External resource founded: {}", file);
                    return file;
                }
            }
        }

        log.info("External resource not founded: {}", resourceName);
        return null;
    }

    @Nullable
    private static URL wrapFile(@NotNull final File file) {
        return executeSafe(() -> new URL("file", "", -1, file.getAbsolutePath()));
    }

    @Nullable
    private static Path findBaseExternalLocation(@NotNull final Class resourceLocator) {
        @Nullable val codeSource = resourceLocator.getProtectionDomain().getCodeSource();

        if (codeSource == null) {
            return null;
        } else {
            return executeSafe(() -> Paths.get(codeSource.getLocation().toURI()).getParent());
        }
    }

}
