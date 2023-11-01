package ticketing_system.app.preesentation.controler.userControllers;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ticketing_system.app.preesentation.data.userDTOs.UserDTO;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reporting")
public class ReportingController {
    /*
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport() throws Exception {
        // Retrieve data from the REST endpoint
        ResponseEntity<UserDTO[]> responseEntity = restTemplate.exchange(
                "/api/v1/user/retrieve", // The URL of your /retrieve endpoint
                HttpMethod.GET,
                null,
                UserDTO[].class
        );

        // Check if the request was successful
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            UserDTO[] data = responseEntity.getBody();

            // Load and compile the JasperReport template
            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("/userReportTemplate.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            // Populate data for the report
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(List.of(data));

            // Create parameters for the report (if needed)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Sample Report");

            // Generate the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to PDF
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);

            // Prepare the response
            byte[] reportBytes = byteArrayOutputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report.pdf");

            return ResponseEntity.ok().headers(headers).body(reportBytes);
        } else {
            // Handle the case where the request to the /retrieve endpoint was not successful
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        }
    }*/
}
