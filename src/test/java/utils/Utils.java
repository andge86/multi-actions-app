package utils;

import stepDefinitions.Hook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
    public static void installOrReinstallApp() throws IOException, InterruptedException {
        System.out.println("Installing the app");
        ProcessBuilder pb = new ProcessBuilder("adb", "install", "-r", Hook.APP_REL_PATH);
        Process pc = pb.start();
        pc.waitFor();
    }

    public static void uninstallApp() throws IOException, InterruptedException {
        System.out.println("Uninstalling the app");
        ProcessBuilder pb = new ProcessBuilder("adb", "uninstall", Hook.APP_PACKAGE);
        Process pc = pb.start();
        pc.waitFor();
    }

    public static boolean isAppInstalled() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("adb", "shell", "pm", "list", "packages", Hook.APP_PACKAGE);
        Process process = pb.start();
        process.waitFor();
        String line = "";
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = reader.readLine()) != null) {
                if (line.contains(Hook.APP_PACKAGE)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("App is not installed");
        return false;
    }
}
