package com.futur.common.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;

@RequiredArgsConstructor
public final class URLFile {

    @Getter
    @NotNull
    private final URL url;

    @Getter
    @NotNull
    private final File file;

}
