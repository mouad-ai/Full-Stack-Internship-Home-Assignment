package ma.dnaengineering.backend.service;

import ma.dnaengineering.backend.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CsvService {

    public Map<String, Object> parseCSV(MultipartFile file) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("employees", parseEmployees(file));
        result.put("averageSalaries", calculateAverageSalaries(file));
        return result;
    }

    public Map<String, Object> parseCSV(String filePath) throws IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("employees", parseEmployees(filePath));
        result.put("averageSalaries", calculateAverageSalaries(filePath));
        return result;
    }

    private List<Employee> parseEmployees(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            boolean headerSkipped = false;
            String line;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 4) {
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(fields[0]));
                    employee.setName(fields[1]);
                    employee.setJob(fields[2]);
                    employee.setSalary(Double.parseDouble(fields[3]));
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    private Map<String, String> calculateAverageSalaries(MultipartFile file) throws IOException {
        List<Employee> employees = parseEmployees(file);

        Map<String, List<Employee>> employeesByJobTitle = new HashMap<>();
        for (Employee employee : employees) {
            employeesByJobTitle.computeIfAbsent(employee.getJob(), k -> new ArrayList<>()).add(employee);
        }

        Map<String, String> averageSalaries = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (Map.Entry<String, List<Employee>> entry : employeesByJobTitle.entrySet()) {
            String jobTitle = entry.getKey();
            List<Employee> jobTitleEmployees = entry.getValue();

            double averageSalary = jobTitleEmployees.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0.0);

            String formattedAverageSalary = decimalFormat.format(averageSalary);
            averageSalaries.put(jobTitle, formattedAverageSalary);
        }

        return averageSalaries;
    }

    private List<Employee> parseEmployees(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath))))) {
            boolean headerSkipped = false;
            String line;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length == 4) {
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(fields[0]));
                    employee.setName(fields[1]);
                    employee.setJob(fields[2]);
                    employee.setSalary(Double.parseDouble(fields[3]));
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    private Map<String, String> calculateAverageSalaries(String filePath) throws IOException {
        List<Employee> employees = parseEmployees(filePath);

        Map<String, List<Employee>> employeesByJobTitle = new HashMap<>();
        for (Employee employee : employees) {
            employeesByJobTitle.computeIfAbsent(employee.getJob(), k -> new ArrayList<>()).add(employee);
        }

        Map<String, String> averageSalaries = new HashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        for (Map.Entry<String, List<Employee>> entry : employeesByJobTitle.entrySet()) {
            String jobTitle = entry.getKey();
            List<Employee> jobTitleEmployees = entry.getValue();

            double averageSalary = jobTitleEmployees.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0.0);

            String formattedAverageSalary = decimalFormat.format(averageSalary);
            averageSalaries.put(jobTitle, formattedAverageSalary);
        }

        return averageSalaries;
    }
}
