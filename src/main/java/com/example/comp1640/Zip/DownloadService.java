package com.example.comp1640.Zip;


import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface DownloadService {
    void downloadZipFile(HttpServletResponse response, List<String> listOfFileNames);
}
