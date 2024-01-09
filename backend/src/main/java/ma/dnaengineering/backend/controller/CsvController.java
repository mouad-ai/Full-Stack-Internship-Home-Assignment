package ma.dnaengineering.backend.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.dnaengineering.backend.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
@CrossOrigin
public class CsvController {


    private final CsvService csvService;

    public CsvController(CsvService service){
        csvService = service;
    }
    @GetMapping("/parse")
    public ResponseEntity<Map<String, Object>> parseCSV() {
        try {
            Map<String, Object> result = csvService.parseCSV("data/employees.csv");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadAndProcessCSV(@RequestPart("file") MultipartFile file) {
        try {
            Map<String, Object> result = csvService.parseCSV(file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}