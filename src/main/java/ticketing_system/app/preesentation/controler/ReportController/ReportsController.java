package ticketing_system.app.preesentation.controler.ReportController;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ticketing_system.app.Business.implementation.ReportService.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/reporting")
public class ReportsController {

    @Autowired
    private TicketReportService ticketReportService;
    @Autowired
    private UserReportService service;

    @Autowired
    private RoleReportService roleReportService;

    @Autowired
    private PositionReportService positionReportService;

    @Autowired
    private DepartmentReportService departmentReportService;


    @GetMapping("/users/{format}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> generateUserReport() {
        try {
            return service.exportReport();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/tickets/{format}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> generateTicketsReport() {
        try {
            return ticketReportService.exportReport();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/roles/{format}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> generateRolesReport() {
        try {
            return roleReportService.exportReport();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/positions/{format}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> generatePositionsReport() {
        try {
            return positionReportService.exportReport();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/departments/{format}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<byte[]> generateDepartmentsReport() {
        try {
            return departmentReportService.exportReport();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/ticket/{id}")
    //@PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> generateTicketReport(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            //userDetails
            return ticketReportService.exportTicketReport(id);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
