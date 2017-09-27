package com.futur.common.helpers;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public final class BufferedImageHelper {

    private BufferedImageHelper() {
        StringHelper.throwNonInitializeable();
    }

    /**
     * Выносит массив байтов картинки
     *
     * @param image Картинка
     * @return Массив байтов картинки
     */
    @NotNull
    @Contract(pure = true)
    public static byte[] toBytes(@NotNull final BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    /**
     * Превращает массив байт в картинку
     *
     * @param bytes массив байт
     * @return картинка
     * @throws IOException мало ли что
     */
    @NotNull
    @Contract(pure = true)
    public static BufferedImage toImage(@NotNull final byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    /**
     * Создает версию пользовательской картинки Buffered Image, для редактирования и соханения байтов
     *
     * @param image Картинки
     * @return Версия картинки с пользовательским пространством
     */
    @NotNull
    @Contract(pure = true)
    public static BufferedImage getUserSpace(@NotNull final BufferedImage image) {
        @NotNull final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        @NotNull final Graphics2D graphics = newImage.createGraphics();

        graphics.drawRenderedImage(image, null);
        graphics.dispose();

        return newImage;
    }

}
