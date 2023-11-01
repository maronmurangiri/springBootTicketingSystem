package ticketing_system.app.Business.servises.TicketServices.utilities.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.TicketServices.utilities.TaskMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Tasks;
import ticketing_system.app.preesentation.data.TicketData.TaskDTO;
import ticketing_system.app.preesentation.data.TicketData.TaskPresentationDTO;

/**
 * implementation of the task mapper.
 * @author kamar baraka.*/

@Service
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {

    /*inject the ModelMapper*/
    private final ModelMapper mapper;

    /**
     * map {@link TaskDTO} to {@link Tasks}*/
    @Override
    public Tasks mapToTask(TaskDTO taskDTO) {

        /*map TaskDTO to Tasks*/
        return mapper.map(taskDTO, Tasks.class);
    }

    /**
     * map {@link Tasks} to {@link TaskDTO}*/
    @Override
    public TaskDTO mapToDTO(Tasks tasks) {

        return mapper.map(tasks, TaskDTO.class);
    }

    /**
     * map {@link Tasks} to {@link TaskPresentationDTO}*/
    @Override
    public TaskPresentationDTO mapToPreDTO(Tasks tasks) {

        return mapper.map(tasks, TaskPresentationDTO.class);
    }
}
