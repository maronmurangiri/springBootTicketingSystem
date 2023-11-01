package ticketing_system.app.Business.servises.TicketServices.implementation.TicketImplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ticketing_system.app.Business.servises.TicketServices.TaskService;
import ticketing_system.app.Business.servises.TicketServices.utilities.TaskMapper;
import ticketing_system.app.exceptions.TaskNotFoundException;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.percistance.repositories.TicketRepositories.TasksRepository;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;

/**
 * the task service implementation.
 * @author kamar baraka.*/

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;
    private final TaskMapper taskMapper;
    private final ModelMapper mapper;

    /**
     * create a task.
     * @param taskDTO a {@link TaskDTO} object
     * @return {@link TaskDTO}*/
    @Override
    public Tasks createTaskGetTask(@Validated final TaskDTO taskDTO) {

        log.warn("local service entry {}", taskDTO);
        /*convert to a Task*/
        Tasks task = mapper.map(taskDTO, Tasks.class);
        task.setDescription(taskDTO.description());
        log.warn("task {}", task);
        /*persist*/
        return tasksRepository.save(task);
    }

    @Override
    public void updateTaskById(final long taskId,  TaskDTO taskDTO) {

        /*check if the task exists*/
        Tasks savedTask = tasksRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("no such task"));
        /*convert DTO to task*/
        Tasks task = taskMapper.mapToTask(taskDTO);
        task.setTaskId(savedTask.getTaskId());
        /*update the task*/
        tasksRepository.save(task);
    }

    @Override
    public void deleteTaskById(@Validated final long taskId) {

        /*check if the task exists and delete*/
        tasksRepository.findById(taskId).ifPresent(tasks -> tasksRepository.deleteById(tasks.getTaskId()));
        tasksRepository.findById(taskId).ifPresentOrElse(
                task -> tasksRepository.deleteById(task.getTaskId()),
                () -> {
                    throw new TaskNotFoundException("no such task");
                }
        );

    }
}
