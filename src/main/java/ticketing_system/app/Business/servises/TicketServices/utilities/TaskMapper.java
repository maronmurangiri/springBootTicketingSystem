package ticketing_system.app.Business.servises.TicketServices.utilities;


import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskPresentationDTO;

/**
 * service to map tickets to DTOs.
 * @author kamar baraka.*/


public interface TaskMapper {

    Tasks mapToTask(TaskDTO taskDTO);
    TaskDTO mapToDTO(Tasks tasks);
    TaskPresentationDTO mapToPreDTO(Tasks tasks);
}
