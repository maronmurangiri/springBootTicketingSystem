package ticketing_system.app.Business.servises.TicketServices;


import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;

/**
 * the task service.
 * @author kamar baraka.*/

public interface TaskService {

    Tasks createTaskGetTask(TaskDTO taskDTO);

    void updateTaskById(long taskId, TaskDTO taskDTO);

    void deleteTaskById(long taskId);
}