package com.futur.common.helpers;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@SuppressWarnings("unused")
public final class FileHelper {

    private FileHelper() {
        StringHelper.throwNonInitializeable();
    }

    public static boolean fileExist(@Nullable final String path) {
        if (Strings.isNullOrEmpty(path)) {
            return false;
        }

        @NotNull final File file = new File(path);

        return file.exists() && file.isFile();
    }

    @NotNull
    public static String getFileExtension(@NotNull final File file) {
        return getFileExtension(file.getName());
    }

    @NotNull
    public static String getFileExtension(@NotNull final String path) {
        final int indexOf = path.lastIndexOf(".");

        if (indexOf == -1 || indexOf == 0) {
            return "";
        } else {
            return path.substring(indexOf);
        }
    }

    @NotNull
    public static String cutNameFromPath(@NotNull final String path) {
        final int indexOfSlash = path.lastIndexOf("\\");
        final int indexOfDot = path.indexOf(".");

        return path.substring(indexOfSlash + 1, indexOfDot);
    }

    @NotNull
    public static String cutFolderFromPath(@NotNull final String path) {
        final int indexOfSlash = path.lastIndexOf("\\");

        return path.substring(0, indexOfSlash);
    }

}
