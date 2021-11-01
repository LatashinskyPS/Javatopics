package com.latashinsky.banksservice.helpers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryHelper {
    public static boolean mkdirIfNotExist(String dirPath) {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            return !new File(dirPath).mkdir();
        }
        return false;
    }
}
