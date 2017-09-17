package com.futur.common.models;

import org.jetbrains.annotations.NotNull;

public final class FXMLPair<C, N> {

    @NotNull
    private final C controller;

    @NotNull
    private final N node;

    public FXMLPair(@NotNull final C controller, @NotNull final N node) {
        this.controller = controller;
        this.node = node;
    }

    @NotNull
    public C getController() {
        return controller;
    }

    @NotNull
    public N getNode() {
        return node;
    }

}
