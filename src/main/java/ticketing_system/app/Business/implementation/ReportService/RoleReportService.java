package ticketing_system.app.Business.implementation.ReportService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Role;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import ticketing_system.app.percistance.repositories.userRepositories.RoleRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.preesentation.data.ReportDTOs.RoleReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.TicketReportDTOs;
import ticketing_system.app.preesentation.data.ReportDTOs.UserReportDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleReportService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RoleReportDTO convertToReportDTO(Role role){
        Long roleCreatorId = (long) role.getCreatedBy();
        System.out.println(roleCreatorId);
        String createdBy = "Not Set";
        if (roleCreatorId!=0) {
            Users roleCreator = userRepository.findById(roleCreatorId).get();

            System.out.println(roleCreator);

            createdBy = roleCreator.getFirstname();
            System.out.println(createdBy);
        }
        return  new RoleReportDTO(role.getRoleId(),
                role.getRoleName(),
                createdBy,
                role.getCreatedOn());
    }
    public List<RoleReportDTO> convertToListReportDTOs(List<Role> roleList) {
        return roleList.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }
    public ResponseEntity<?> exportReport() throws FileNotFoundException, JRException {
        List<Role> roles = repository.findAll();
        List<RoleReportDTO> roleReportDTO = convertToListReportDTOs(roles);
        System.out.println(roles);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:roleReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(roleReportDTO);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=roles.pdf");
        //"attachment; filename=/home/maron/DevelhopeTReport/PDFReports/tickets.pdf"
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }

    public ResponseEntity<?> exportRoleReport(Long roleId) throws FileNotFoundException, JRException {
        Optional<Role> role = repository.findById(roleId);
        System.out.println(role);
        RoleReportDTO roleReportDTO = convertToReportDTO(role.get());
        List<RoleReportDTO> singleRoleList = Arrays.asList(roleReportDTO);

        System.out.println(role);
        System.out.println(roleReportDTO);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:roleReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleRoleList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;

        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=role.pdf");

        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }
}