package ma.dnaengineering.backend;

import ma.dnaengineering.backend.controller.CsvController;
import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.service.CsvService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CsvService csvService;

	@InjectMocks
	private CsvController csvController;

	@Test
	void contextLoads() {
	}
	@Test
	void uploadAndProcessCSV() throws Exception {
		Map<String, Object> mockResult = new HashMap<>();
		when(csvService.parseCSV((MultipartFile) Mockito.any())).thenReturn(mockResult);

		mockMvc.perform(multipart("/api/csv/upload")
						.file(new MockMultipartFile("file", "test.csv", "text/plain", "CSV content".getBytes())))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.employees").exists());

	}



}
