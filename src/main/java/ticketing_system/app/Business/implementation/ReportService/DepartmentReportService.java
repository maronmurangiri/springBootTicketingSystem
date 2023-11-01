package ticketing_system.app.Business.implementation.ReportService;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import ticketing_system.app.percistance.repositories.userRepositories.DepartmentRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import ticketing_system.app.preesentation.data.ReportDTOs.DepartmentReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.PositionReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.TicketReportDTOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentReportService {

        @Autowired
        private DepartmentRepository repository;

        @Autowired
        private UserRepository userRepository;

    public DepartmentReportDTO convertToReportDTO(Department department){

        assert department != null;
        String director= "";
        Users departmentDirector = department.getDepartmentDirector();
        if (departmentDirector!=null) {
            director = departmentDirector.getFirstname();
        }
        String creatorName = "";
        if (department.getCreatedBy()!=null){
        long creatorId = (long) department.getCreatedBy();

            Users creator = userRepository.findById(creatorId).get();
            creatorName = creator.getFirstname();
        }

        return  new DepartmentReportDTO(
               department.getDepartmentId(),
                department.getDepartmentName(),
                director,
                creatorName,
                department.getCreatedOn()
        );
    }

    public List<DepartmentReportDTO> convertToListReportDTOs(List<Department> departmentList) {
        return departmentList.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }


        public ResponseEntity<byte[]> exportReport() throws FileNotFoundException, JRException {
            List<Department> departments = repository.findAll();
            List<DepartmentReportDTO> departmentReportDTOS = convertToListReportDTOs(departments);
            System.out.println(departments);
            System.out.println(departmentReportDTOS);

            //load file and compile it
            File file = ResourceUtils.getFile("classpath:departmentReportTemplate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(departmentReportDTOS);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "KeMaTCo");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] reportBytes = null;
            //String fileName = "tickets.pdf";
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=departments.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            //return reportBytes;
            // Return the PDF content as a response with appropriate headers
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);
        }

    public ResponseEntity<byte[]> exportDepartmentReport(Long departmentId) throws FileNotFoundException, JRException {
        Optional<Department> departments = repository.findById(departmentId);
        DepartmentReportDTO departmentReportDTOS = convertToReportDTO(departments.get());
        List<DepartmentReportDTO> singleDepartmentList = Arrays.asList(departmentReportDTOS);
        System.out.println(departments);
        System.out.println(departmentReportDTOS);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:departmentReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleDepartmentList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=department.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
    }
    }
