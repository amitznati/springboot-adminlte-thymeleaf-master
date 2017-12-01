package com.amdocs.owa.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CSVFileController {
	
	@RequestMapping("/import-file")
    public String importCSVFile(Model model) {
		
        return "csv/import";
    }

    @PostMapping("/validate")
    public String validateCSVFile(@RequestParam("file") MultipartFile file,Model model) {
		
    	String contents = "sdfdsf";
    	String inputFile = "";
		try {//C:\\p4\\VFRO\\amitz_AMITZ02_OWA-SWP2_3806\\springboot-adminlte-thymeleaf-master\\src\\main\\resources\\
			String url = getClass().getClassLoader().getResource("static/csv-tamplets/ChangeConfiguration.csv").getPath().substring(1);
			contents = new String(Files.readAllBytes(Paths.get(url)));
			inputFile =  new String(file.getBytes());
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
		model.addAttribute("ChangeConfiguration" , contents);
		model.addAttribute("inputFile" , inputFile);
    	//Read more: http://javarevisited.blogspot.com/2015/09/how-to-read-file-into-string-in-java-7.html#ixzz4yoqOHpAJ
        return "csv/validate";
    }
    
    @GetMapping("/validate")
    public String loadCSVFile(Model model) {
    	
    	return "csv/validate";
    }

}
