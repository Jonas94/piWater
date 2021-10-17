package com.example.piwater.utils;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class RaspberryHelper {

    public boolean isPi() {
        String osRelease = osRelease();
        return osRelease != null && osRelease.contains("Raspbian");
    }

    private String readFirstLine(File file) {
        String firstLine = null;
        if (file.canRead()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(fis));
                firstLine = bufferedReader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return firstLine;
    }

    public String osRelease() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Linux")) {
            final File osRelease = new File("/etc", "os-release");
            return readFirstLine(osRelease);
        }
        return null;
    }
}