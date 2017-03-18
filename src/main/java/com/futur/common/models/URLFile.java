package com.futur.common.models;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;

public final class URLFile {

    @NotNull
    private final URL url;

    @NotNull
    private final File file;

    public URLFile(@NotNull final URL url, @NotNull final File file) {
        this.url = url;
        this.file = file;
    }

    @NotNull
    public URL getUrl() {
        return url;
    }

    @NotNull
    public File getFile() {
        return file;
    }

}
