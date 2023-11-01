package ticketing_system.app.preesentation.controler.userControllers;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ticketing_system.app.Business.implementation.ReportService.DepartmentReportService;
import ticketing_system.app.Business.implementation.userServiceImplementations.DepartmentServiceImpl;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.preesentation.data.userDTOs.DepartmentDTO;

import java.io.FileNotFoundException;


/**
 * The `DepartmentController` class provides a REST API for managing departments within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting departments.
 *
 * <p>Dependencies:
 * - `DepartmentServiceImpl`: Handles department-related operations.
 *
 * <p>Example Usage:
 * DepartmentController departmentController = new DepartmentController(departmentService);
 * ResponseEntity<?> createdDepartment = departmentController.createDepartment(authorizationHeader, departmentCreatedEmail, departmentDirectorEmail, departmentDTO);
 * ResponseEntity<?> updatedDepartment = departmentController.updateDepartment(departmentId, authorizationHeader, departmentUpdatedEmail, departmentDirectorEmail, departmentDTO);
 * ResponseEntity<?> allDepartments = departmentController.retrieveDepartments(authorizationHeader);
 * ResponseEntity<?> departmentById = departmentController.retrieveDepartmentById(authorizationHeader, departmentId);
 * ResponseEntity<?> deletedDepartment = departmentController.deleteDepartmentById(departmentId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 * */
@RestController
@RequestMapping("/api/v1/department")
//@Api(value = "CRUD Rest APIs for department")
@Tag(name = "CRUD Rest APIs for department")
public class DepartmentController {
    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentReportService departmentReportService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(description = "Create department REST API")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createDepartment(@RequestParam("departmentCreatedEmail") String departmentCreatedEmail, @RequestParam("departmentDirectorEmail") String departmentDirectorEmail, @RequestBody DepartmentDTO departmentDTO){
        try {
            //String token = authorizationHeader;
            System.out.println(departmentCreatedEmail);
            Department department = departmentService.createDepartment(departmentCreatedEmail,departmentDirectorEmail, departmentDTO);
            assert department !=null;
            Long departmentId = department.getDepartmentId();
            return departmentReportService.exportDepartmentReport(departmentId);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "update department REST API")
    @PutMapping("/update/{departmentId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateDepartment(@PathVariable("departmentId") Long departmentId,@RequestParam("departmentUpdatedEmail") String departmentUpdatedEmail,@RequestParam("departmentDirectorEmail") String departmentDirectorEmail, @RequestBody DepartmentDTO departmentDTO){
        try {
            //String token = authorizationHeader;
            System.out.println(departmentId);
              departmentService.updateDepartment(departmentId,departmentUpdatedEmail,departmentDirectorEmail,departmentDTO);
             return departmentReportService.exportDepartmentReport(departmentId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "Get all departments REST API")
    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> retrievePositions(){
       try {
           //String token = authorizationHeader;

           if (departmentService.retrieveDepartments().isEmpty()) {
               return ResponseEntity.notFound().build();
           }
           departmentService.retrieveDepartments();
           return departmentReportService.exportReport();
       }catch (IllegalArgumentException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       } catch (JRException | FileNotFoundException e) {
           throw new RuntimeException(e);
       }
    }

    @Operation(description = "Get department by Id REST API")
    @GetMapping("/retrieveById/{departmentId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> retrieveDepartmentById(@PathVariable("departmentId") Long departmentId){
        try {
           // String token = authorizationHeader;
           // departmentService.retrieveDepartmentById(departmentId);
            return departmentReportService.exportDepartmentReport(departmentId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "Delete department by Id REST API")
    @DeleteMapping("/deleteById/{departmentId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('agent')")
    public ResponseEntity<?> deleteDepartmentById(@PathVariable("departmentId") Long departmentId){
        try {
            //String token = authorizationHeader;
            if (departmentService.deleteDepartmentById(departmentId)) {
                return ResponseEntity.ok("Department successfully deleted");
            }
            else{
                return ResponseEntity.badRequest().body("Department not deleted. Try later..........");
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
