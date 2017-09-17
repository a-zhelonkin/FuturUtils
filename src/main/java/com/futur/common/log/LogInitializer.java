package com.futur.common.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.util.ContextInitializer;
import ch.qos.logback.core.util.StatusPrinter;
import com.futur.common.helpers.StringHelper;
import com.futur.common.helpers.date.DateHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.futur.common.helpers.DevelopmentHelper.executeSafe;

@SuppressWarnings("unused")
@Slf4j
public final class LogInitializer {

    private LogInitializer() {
        StringHelper.throwNonInitializeable();
    }

    public static void initLogFileName(@NotNull final String propertyName,
                                       @NotNull final String fileNamePrefix) {
        executeSafe(() -> {
            @NotNull val fileName = fileNamePrefix + DateHelper.now() + ".log";
            @NotNull val folderPath = Paths.get("log");

            if (!Files.isDirectory(folderPath)) {
                Files.createDirectory(folderPath);
            }

            @NotNull val absoluteFolderPath = folderPath.toAbsolutePath().toString();
            @NotNull val logFilePath = absoluteFolderPath + File.separator + fileName;

            System.setProperty(propertyName, logFilePath);

            log.info("Path to log {}", logFilePath);

            @NotNull val loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            @NotNull val contextInitializer = new ContextInitializer(loggerContext);

            loggerContext.reset();
            contextInitializer.autoConfig();
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);
        });
    }

}
