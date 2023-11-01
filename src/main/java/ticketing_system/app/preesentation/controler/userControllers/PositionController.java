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
import ticketing_system.app.Business.implementation.ReportService.PositionReportService;
import ticketing_system.app.Business.implementation.userServiceImplementations.PositionServiceImpl;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.preesentation.data.userDTOs.PositionDTO;

import java.io.FileNotFoundException;


/**
 * The `PositionController` class provides a REST API for managing positions within the ticketing system.
 * It offers endpoints for creating, updating, retrieving, and deleting positions.
 *
 * <p>Dependencies:
 * - `PositionServiceImpl`: Handles position-related operations.
 *
 * <p>Example Usage:
 * PositionController positionController = new PositionController(positionService);
 * ResponseEntity<?> createdPosition = positionController.createPosition(authorizationHeader, positionCreatedEmail, departmentName, positionDTO);
 * ResponseEntity<?> updatedPosition = positionController.updatePosition(positionId, authorizationHeader, userUpdatedEmail, departmentName, positionDTO);
 * ResponseEntity<?> allPositions = positionController.retrievePositions(authorizationHeader);
 * ResponseEntity<?> positionById = positionController.retrievePositionById(authorizationHeader, positionId);
 * ResponseEntity<?> deletedPosition = positionController.deletePositionById(positionId, authorizationHeader);
 *
 * @author Maron
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/position")
@Tag(name = "CRUD Rest APIs for position")
public class PositionController {
        private PositionServiceImpl positionService;

        @Autowired
    PositionReportService positionReportService;

        @Autowired
        public PositionController(PositionServiceImpl positionService) {
            this.positionService = positionService;
        }

    @Operation(description = "Create position REST API")
        @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> createPosition(@RequestParam("positionCreatedEmail") String positionCreatedEmail, @RequestParam("departmentName") String departmentName, @RequestBody PositionDTO positionDTO){
            try {
                //String token = authorizationHeader;
                System.out.println(positionCreatedEmail);
                Positions positionCreated = positionService.createPosition(positionCreatedEmail,departmentName,positionDTO);
                Long positionId = positionCreated.getPositionId();
                return positionReportService.exportPositionReport(positionId);
            }
            catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    @Operation(description = "Update position by Id REST API")
        @PutMapping("/update/{positionId}")
    @PreAuthorize("hasAuthority('admin')")
        public ResponseEntity<?> updateUser(@PathVariable("positionId") Long positionId,@RequestParam("userUpdatedEmail") String userUpdatedEmail,@RequestParam("departmentName") String departmentName, @RequestBody PositionDTO positionDTO){
            try {
                System.out.println(positionId);
                //String token = authorizationHeader;
               positionService.updatePosition(positionId,userUpdatedEmail,departmentName,positionDTO);
                return positionReportService.exportPositionReport(positionId);
            }catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
    }

    @Operation(description = "Get all positions REST API")
    @GetMapping("/retrieve")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> retrievePositions(){
        try {
            //String token = authorizationHeader;
            positionService.retrievePositions();
            return positionReportService.exportReport();

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "Get position by Id REST API")
    @GetMapping("/retrieveById/{positionId}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('agent')")
    public ResponseEntity<?> retrievePositionById(@PathVariable("positionId") Long positionId) {
        try {
            //String token = authorizationHeader;
            positionService.retrievePositionById(positionId);
            return positionReportService.exportPositionReport(positionId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(description = "Delete Position by Id REST API")
    @DeleteMapping("/deleteById/{positionId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deletePositionById(@PathVariable("positionId") Long positionId){
        try {
            //String token = authorizationHeader;
            if (positionService.deletePositionById(positionId)) {
                return ResponseEntity.ok("Position successfully deleted");
            }else{
                return ResponseEntity.badRequest().body("Not deleted. Try later..........");
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
