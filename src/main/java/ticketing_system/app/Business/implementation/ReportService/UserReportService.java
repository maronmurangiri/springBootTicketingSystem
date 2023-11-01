package ticketing_system.app.Business.implementation.ReportService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.preesentation.data.ReportDTOs.UserReportDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserReportService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserReportDTO convertToReportDTO(Users users){
        Role role = users.getRole();
        String roleName = "Not set";
        String positionName = "Not set";

        if (role !=null){
            roleName = role.getRoleName();
        }
        Positions positions  = users.getPositions();
        if (positions !=null){
            positionName = positions.getPositionName();
        }
        Long creatorById = (long) users.getCreatedBy();
        String creatorName = "";
        if (creatorById!=0) {
            Users creator = repository.findById(creatorById).get();
            creatorName = creator.getFirstname();
        }

        return new UserReportDTO(
                users.getId(),
                users.getFirstname(),
                users.getSurname(),
                users.getEmail(),
                roleName,
                positionName,
                creatorName,
                users.getUpdatedOn()
        );

    }

    public List<UserReportDTO> convertToListReportDTOs(List<Users> usersList) {
        return usersList.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> exportUserReport(Long userId) throws FileNotFoundException, JRException {
        Optional<Users> users = repository.findById(userId);

        UserReportDTO userReportDTO = convertToReportDTO(users.get());
        List<UserReportDTO> singleUserList = Arrays.asList(userReportDTO);


        System.out.println(users);
        System.out.println(singleUserList);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:userReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleUserList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=users.pdf");
        //"attachment; filename=/home/maron/DevelhopeTReport/PDFReports/tickets.pdf"
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }

    public ResponseEntity<?> exportReport() throws FileNotFoundException, JRException {
        List<Users> users = repository.findAll();
        List<UserReportDTO> userReportDTOS = convertToListReportDTOs(users);
        System.out.println(users);
        System.out.println(userReportDTOS);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:userReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userReportDTOS);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=users.pdf");
        //"attachment; filename=/home/maron/DevelhopeTReport/PDFReports/tickets.pdf"
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }
}