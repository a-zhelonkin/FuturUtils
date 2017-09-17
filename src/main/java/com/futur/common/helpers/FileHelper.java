package com.futur.common.helpers;

import com.google.common.base.Strings;
import lombok.val;
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

        @NotNull val file = new File(path);

        return file.exists() && file.isFile();
    }

    @NotNull
    public static String getFileExtension(@NotNull final File file) {
        return getFileExtension(file.getName());
    }

    @NotNull
    public static String getFileExtension(@NotNull final String path) {
        val indexOf = path.lastIndexOf(".");

        if (indexOf == -1 || indexOf == 0) {
            return "";
        } else {
            return path.substring(indexOf);
        }
    }

    @NotNull
    public static String cutNameFromPath(@NotNull final String path) {
        val indexOfSlash = path.lastIndexOf("\\");
        val indexOfDot = path.indexOf(".");

        return path.substring(indexOfSlash + 1, indexOfDot);
    }

    @NotNull
    public static String cutFolderFromPath(@NotNull final String path) {
        return path.substring(0, path.lastIndexOf("\\"));
    }

    @Nullable
    public static File[] listFiles(@NotNull final String file) {
        return listFiles(new File(file));
    }

    @Nullable
    public static File[] listFiles(@NotNull final File file) {
        if (!file.exists()) {
            return null;
        }

        if (!file.isDirectory()) {
            return null;
        }

        @Nullable final File[] files = file.listFiles();

        if (files == null || files.length == 0) {
            return null;
        }

        return files;
    }

    private static long folderSize(@NotNull final File directory) {
        @Nullable val files = listFiles(directory);
        if (files == null) {
            return 0;
        }

        long length = 0;
        for (@Nullable val file : files) {
            if (file != null) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += folderSize(file);
                }
            }
        }

        return length;
    }

}
