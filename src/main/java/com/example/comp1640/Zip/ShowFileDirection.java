package com.example.comp1640.Zip;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Controller
public class ShowFileDirection {

    @Autowired
    private DownloadService downloadService;

    @GetMapping("/showfile")
    public String showFiles(Model model) {
        String path = System.getProperty("user.dir");
        String subPath = "/upload-dir";
        String directoryPath = path + subPath;
        List<String> files = new ArrayList<>();

        // Create a File object for the directory
        File directory = new File(directoryPath);

        // Check if the directory exists
        if (directory.exists() && directory.isDirectory()) {
            // Get list of files in the directory
            File[] fileList = directory.listFiles();

            // Check if files exist in the directory
            if (fileList != null && fileList.length > 0) {
                System.out.println("Files in directory " + directoryPath + ":");
                // Iterate over the files and print their names
                for (File file : fileList) {
                    System.out.println(file.getName());
                    files.add(file.getName());
                }
                model.addAttribute("files", files);
            } else {
                System.out.println("No files found in directory " + directoryPath);
            }
        } else {
            System.out.println("Directory " + directoryPath + " does not exist or is not a directory.");
        }
        return "Contribution/ViewContribution"; // Assuming you have a template named "DownloadZip.html"
    }

    @GetMapping("/DownloadSelected")
    public void downloadZipFile(HttpServletResponse response, @RequestParam("selectedFiles") List<String> files) {
        List<String> SelectedList = new ArrayList<>();
        String path = System.getProperty("user.dir");
        String subPath = "\\upload-dir\\";
        for (int i = 0; i < files.size(); i++) {
            files.set(i, path + subPath +files.get(i));
            SelectedList.add(files.get(i));
            System.out.println(files.get(i));
        }
        downloadService.downloadZipFile(response, files);
    }
    @GetMapping("/DownloadAll")
    public void DownloadZipAll(HttpServletResponse response) {
        String path = System.getProperty("user.dir");
        String subPath = File.separator + "upload-dir" + File.separator;
        String directoryPath = path + subPath;
        System.out.println(directoryPath);
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        List<String> fileNames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String filepath = directoryPath + file.getName();
                fileNames.add(filepath);
            }
        } else {
            System.out.println("No files found in directory " + directoryPath);
        }
        downloadService.downloadZipFile(response,fileNames);
    }
}



