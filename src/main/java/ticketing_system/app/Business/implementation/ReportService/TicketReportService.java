package ticketing_system.app.Business.implementation.ReportService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.preesentation.data.ReportDTOs.TicketReportDTOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketReportService {

    @Autowired
    private TicketsRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public TicketReportDTOs convertToReportDTO(Tickets tickets){
        Users agent = tickets.getAgentAssigned();
        String firstName = "";
        if (agent!=null){
            firstName = agent.getFirstname();
        }
        List<Tasks> task = tickets.getTasks();
        List<String> description = Collections.singletonList("");
        if (task!=null){
            description = task.stream().map(Tasks::getDescription).toList();
        }
        return  new TicketReportDTOs(tickets.getTicketId(),
                tickets.getTicketName(),
                tickets.getDescription(),
                tickets.getStatus(),
                firstName,
                tickets.getCreatedOn(),tickets.getDeadline(),description);
    }

    public List<TicketReportDTOs> convertToListReportDTOs(List<Tickets> ticketsList) {
        return ticketsList.stream()
                .map(this::convertToReportDTO)
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> exportTicketReport(Long ticketId) throws FileNotFoundException, JRException {
        Optional<Tickets> tickets = repository.findById(ticketId);

        TicketReportDTOs ticketReportDTOs = convertToReportDTO(tickets.get());
        List<TicketReportDTOs> singleTicketList = Arrays.asList(ticketReportDTOs);

        System.out.println(tickets);
        System.out.println(ticketReportDTOs);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:ticketReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleTicketList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;

        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

       // JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/maron/DevelhopeTReport/PDFReports/tickets.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=tickets.pdf");
        //"attachment; filename=/home/maron/DevelhopeTReport/PDFReports/tickets.pdf"
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
        //return "report generated in path : /home/maron/DevelhopeTReport/PDFReports/tickets.pdf" ;
    }


    public ResponseEntity<?> exportReport() throws FileNotFoundException, JRException {
        List<Tickets> tickets = repository.findAll();
        List<TicketReportDTOs> ticketReportDTOs = convertToListReportDTOs(tickets);

        System.out.println(tickets);
        System.out.println(ticketReportDTOs);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:ticketReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ticketReportDTOs);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            //JasperExportManager.exportReportToPdfFile(jasperPrint, "/home/maron/DevelhopeTReport/PDFReports/tickets.pdf");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=tickets.pdf");
        //"attachment; filename=/home/maron/DevelhopeTReport/PDFReports/tickets.pdf"
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
       // return "report generated in path : /home/maron/DevelhopeTReport/PDFReports/tickets.pdf" ;

    }
}