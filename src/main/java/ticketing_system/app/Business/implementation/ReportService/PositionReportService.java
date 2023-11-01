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
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.PositionRepository;
import ticketing_system.app.percistance.repositories.userRepositories.RoleRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import ticketing_system.app.preesentation.data.ReportDTOs.PositionReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.RoleReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.TicketReportDTOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PositionReportService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private  PositionRepository positionRepository;

        @Autowired
        private ModelMapper modelMapper;

    public PositionReportDTO convertToReportDTO(Positions positions){
        Department department = positions.getDepartment();
        String departmentName = "";
        if(department!=null){
            departmentName = department.getDepartmentName();
        }
        long creatorId = (long) positions.getCreatedBy();
        String creatorName = "";
        if (creatorId!=0){
           Users creator = userRepository.findById(creatorId).get();
           creatorName = creator.getFirstname();
        }

        return  new PositionReportDTO(
                positions.getPositionId(),
                positions.getPositionName(),
                departmentName,
                creatorName,
                positions.getCreatedOn()
                );
    }
    public List<PositionReportDTO> convertToListReportDTOs(List<Positions> positionList) {
        return positionList.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }
        public ResponseEntity<?> exportReport() throws FileNotFoundException, JRException {
            List<Positions> positions = positionRepository.findAll();
            List<PositionReportDTO> positionReportDTOs = convertToListReportDTOs(positions);
            System.out.println(positions);
            System.out.println(positionReportDTOs);

            //load file and compile it
            File file = ResourceUtils.getFile("classpath:positionReportTemplate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(positionReportDTOs);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "KeMaTCo");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] reportBytes = null;
            //String fileName = "tickets.pdf";
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=positions.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            //return reportBytes;
            // Return the PDF content as a response with appropriate headers
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        }


    public ResponseEntity<?> exportPositionReport(Long positionId) throws FileNotFoundException, JRException {
        Optional<Positions> positions = positionRepository.findById(positionId);
        PositionReportDTO positionReportDTO = convertToReportDTO(positions.get());
        List<PositionReportDTO> singlePositionList = Arrays.asList(positionReportDTO);


        //load file and compile it
        File file = ResourceUtils.getFile("classpath:positionReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singlePositionList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=position.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }
    }

