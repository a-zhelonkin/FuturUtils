package com.futur.common.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class FXMLPair<C, N> {

    @Getter
    @NotNull
    private final C controller;

    @Getter
    @NotNull
    private final N node;

}
