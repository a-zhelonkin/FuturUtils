package com.futur.common.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.util.StatusPrinter;
import com.futur.common.helpers.StringHelper;
import com.futur.common.helpers.date.DateHelper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;
import static org.slf4j.LoggerFactory.getLogger;

@SuppressWarnings("unused")
public final class LogInitializer {

    @NotNull
    private static final Logger LOG = getLogger(LogInitializer.class);

    private LogInitializer() {
        StringHelper.throwNonInitializeable();
    }

    public static void initLogFileName(@NotNull final String propertyName,
                                       @NotNull final String fileNamePrefix) {
        executeSafe(() -> {
            @NotNull final String fileName = fileNamePrefix + DateHelper.now() + ".log";
            @NotNull final Path folderPath = Paths.get("log");

            if (!Files.isDirectory(folderPath)) {
                Files.createDirectory(folderPath);
            }

            @NotNull final String absoluteFolderPath = folderPath.toAbsolutePath().toString();
            @NotNull final String logFilePath = absoluteFolderPath + File.separator + fileName;

            LOG.info("Path to log {}", logFilePath);

            System.setProperty(propertyName, logFilePath);

            @NotNull final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            @NotNull final ContextInitializer contextInitializer = new ContextInitializer(loggerContext);

            loggerContext.reset();
            contextInitializer.autoConfig();
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        });
    }

}
