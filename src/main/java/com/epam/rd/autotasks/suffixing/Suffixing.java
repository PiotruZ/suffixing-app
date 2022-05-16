package com.epam.rd.autotasks.suffixing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Suffixing {

    public static void main(String[] args) throws IOException {

        String PROPERTIES_PATH = args[0];

        Logger logger = Logger.getLogger(Suffixing.class.getName());
        Properties properties = new Properties();

        try(var input = new FileInputStream(PROPERTIES_PATH)){
            properties.load(input);
        }
        String mode = properties.getProperty("mode");
        String suffix = properties.getProperty("suffix");
        String files = properties.getProperty("files");

        if (Objects.equals(mode, "copy")) {
            logger.log(Level.SEVERE, "Mode is not recognized: " + mode);
            throw new RuntimeException();
        }
        if (suffix == null || suffix.isBlank()) {
            logger.log(Level.SEVERE, "No suffix is configured");
            throw new RuntimeException();
        }
        if (files == null || files.isBlank()) {
            logger.log(Level.WARNING, "No files are configured to be copied/moved");
            throw new RuntimeException();
        }
        for (String file : files.split(":")) {
            File f = new File(file);
            if (!f.exists()) {
                logger.log(Level.SEVERE, "No such file: "+f);
            } else {
                int at = file.lastIndexOf('.');
                String newFile = file.substring(0, at)+"-"+suffix+file.substring(at);
                File dest = new File(newFile);
                logger.log(Level.INFO, f + " -> "+dest);
            }
        }


    }

}
