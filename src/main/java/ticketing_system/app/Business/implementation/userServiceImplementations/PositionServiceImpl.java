package ticketing_system.app.Business.implementation.userServiceImplementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.userServices.PositionService;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.userRepositories.PositionRepository;
import ticketing_system.app.preesentation.data.userDTOs.PositionDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The `PositionServiceImpl` class is an implementation of the `{@link PositionService}` interface and is responsible for managing positions within departments.
 * It provides methods for creating, retrieving, updating, and deleting positions.
 *
 * <p>This class uses the `ModelMapper` for converting between DTOs and entity objects, the `DepartmentServiceImpl` for department-related operations,
 * and the `JwtTokenProviderImpl` for user authorization.
 *
 * <p>Dependencies:
 * - `ModelMapper`: Used for object mapping between DTOs and entity objects.
 * - `PositionService`: Provides the required business logic for position management.
 * - `PositionRepository`: Provides access to position data stored in the database.
 * - `DepartmentServiceImpl`: Handles department-related operations.
 * - `UserImpematation`: Provides user-related functionality and information retrieval.
 * - `JwtTokenProviderImpl`: Handles JWT-based user authorization.
 *
 * <p>Example Usage:
 * PositionServiceImpl positionService = new PositionServiceImpl(positionRepository, departmentService, modelMapper, userImpematation, tokenProvider);
 * PositionDTO positionDTO = new PositionDTO(); // Initialize with position data
 * Position createdPosition = positionService.createPosition(positionCreatedEmail, departmentName, token, positionDTO);
 *
 * List<PositionDTO> positions = positionService.retrievePositions(token);
 * PositionDTO retrievedPosition = positionService.retrievePositionById(positionId, token);
 * Position updatedPosition = positionService.updatePosition(positionId, positionCreatedEmail, departmentName, token, positionDTO);
 * Position position = positionService.retrievePositionByName(positionName);
 * boolean deleted = positionService.deletePositionById(positionId, token);
 *
 * @author Maron
 * @version 1.0
 */
@Service
@Lazy
public class PositionServiceImpl implements PositionService {

    Timestamp currentTimestamp = Timestamp.from(Instant.now());
    String pattern = "yyyy-MM-dd HH:mm:ss";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
    String formattedTimestamp = currentTimestamp.toLocalDateTime().format(dateTimeFormatter);
    Timestamp currentTimestampFormatted = Timestamp.valueOf(formattedTimestamp);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private UserImpematation userImpematation;

    @Autowired
    private JwtTokenProviderImpl tokenProvider;

   /* @Autowired
    public PositionServiceImpl(UserImpematation userImpematation, DepartmentServiceImpl departmentService,ModelMapper modelMapper, PositionRepository positionRepository) {
        this.modelMapper = modelMapper;
        this.positionRepository = positionRepository;
        this.departmentService=departmentService;
    }*/

    public PositionDTO convertToDto(Positions positions){

        return modelMapper.map(positions, PositionDTO.class);
    }

    public Positions convertToPosition(PositionDTO positionDTO){

        return modelMapper.map(positionDTO, Positions.class);
    }

    public List<PositionDTO> convertToListDTOs(List<Positions> positionsList) {
        return positionsList.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Positions createPosition(String positionCreatedEmail,String departmentName,PositionDTO positionDTO) {
        if (positionDTO.getPositionName().isBlank() || positionDTO.getPositionName() == null) {
            throw new IllegalArgumentException("Item name can neither be null nor blank");
        }

        if (positionDTO == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Positions position = convertToPosition(positionDTO);

        //set created on
        position.setCreatedOn(currentTimestampFormatted);

        //set position
        Department positionDepartment = departmentService.retrieveDepartmentByName(departmentName);
        position.setDepartment(positionDepartment);

        //set created by
        Users createdByUsers = userImpematation.retrieveUserByEmail(positionCreatedEmail);
        System.out.println(createdByUsers);
        position.setCreatedBy(Math.toIntExact(createdByUsers.getId()));

            Positions savedPositions = positionRepository.save(position);
            return savedPositions;
    }

    @Override
    public List<PositionDTO> retrievePositions() {
            return convertToListDTOs(positionRepository.findAll());

    }

    @Override
    public PositionDTO retrievePositionById(Long positionId) {
        if (positionRepository.existsById(positionId)) {

                return this.convertToDto(positionRepository.findById(positionId).get());
        }
        throw new IllegalArgumentException("Positions not found");
    }

    @Override
    public Positions updatePosition(Long positionId,String positionCreatedEmail,String departmentName, PositionDTO positionDTO) {
        if (positionRepository.existsById(positionId)){
            System.out.println(positionId);

            Positions position = convertToPosition(positionDTO);
            //set created on
            position.setUpdatedOn(currentTimestampFormatted);

            //set position Id
            position.setPositionId(positionId);

            //set position
            Department positionDepartment = departmentService.retrieveDepartmentByName(departmentName);
            position.setDepartment(positionDepartment);

            //set created by
            Users createdByUsers = userImpematation.retrieveUserByEmail(positionCreatedEmail);
            System.out.println(createdByUsers);
            position.setUpdatedBy(createdByUsers.getCreatedBy());


                return positionRepository.save(position);
        }
        throw new IllegalArgumentException("Positions not found");

}

    @Override
    public Positions retrievePositionByName(String positionName) {
        System.out.println(positionName);
        Optional<Positions> position = positionRepository.findPositionByPositionName(positionName);
        if(position.isPresent()){
            System.out.println(position);
            return position.get();
        };
        //return convertToDto(positionRepository.findByPositionName(positionName));
        return null;
    }

    @Override
    public boolean deletePositionById(Long positionId) {
        if (positionRepository.existsById(positionId)){
            positionRepository.deleteById(positionId);
            return true;
        }
        throw new IllegalArgumentException("Positions not found");

    }
}
