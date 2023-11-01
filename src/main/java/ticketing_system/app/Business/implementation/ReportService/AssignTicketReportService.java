package ticketing_system.app.Business.implementation.ReportService;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.TicketRepositories.TicketsRepository;
import ticketing_system.app.preesentation.data.ReportDTOs.AssignTicketReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.TicketReportDTOs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignTicketReportService {

        @Autowired
        private TicketsRepository repository;

        public AssignTicketReportDTO convertToReportDTO(Tickets tickets){
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
            return  new AssignTicketReportDTO(tickets.getTicketId(),
                    tickets.getTicketName(),
                    tickets.getStatus(),
                    tickets.getPriority(),
                    firstName,
                    tickets.getTag(),description,tickets.getCreatedOn(),tickets.getDeadline());
        }

        public List<AssignTicketReportDTO> convertToListReportDTOs(List<Tickets> ticketsList) {
            return ticketsList.stream()
                    .map(this::convertToReportDTO)
                    .collect(Collectors.toList());
        }


        public ResponseEntity<?> exportTicketReport(Long ticketId) throws FileNotFoundException, JRException {
            Optional<Tickets> tickets = repository.findById(ticketId);

            AssignTicketReportDTO ticketReportDTOs = convertToReportDTO(tickets.get());
            List<AssignTicketReportDTO> singleTicketList = Arrays.asList(ticketReportDTOs);

            System.out.println(tickets);
            System.out.println(ticketReportDTOs);

            //load file and compile it
            File file = ResourceUtils.getFile("classpath:assignTicketReportTemplate.jrxml");
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
}
