package ticketing_system.app.Business.servises.TicketServices.utilities.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ticketing_system.app.Business.servises.TicketServices.utilities.TicketMapper;
import ticketing_system.app.percistance.Entities.TicketEntities.Tickets;
import ticketing_system.app.percistance.Enums.Tags;
import ticketing_system.app.percistance.Enums.TicketPriority;
import ticketing_system.app.preesentation.data.TicketData.TicketAgentDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketDTO;
import ticketing_system.app.preesentation.data.TicketData.TicketNormalDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.yaml.snakeyaml.tokens.Token.ID.Tag;

/**
 * implementation of the ticket mapper.
 * @author kamar baraka.*/

@Service
@RequiredArgsConstructor
public class TicketMapperImpl implements TicketMapper {

    /*inject the mapper*/
    private final ModelMapper mapper;

    /**
     * map {@link TicketDTO} to {@link Tickets}*/
    @Override
    public Tickets mapToTicket(TicketDTO ticketDTO) {

        Tickets map = mapper.map(ticketDTO, Tickets.class);
        map.setTicketName(ticketDTO.ticketName());
        map.setDescription(ticketDTO.description());
        map.setTag(Tags.valueOf(ticketDTO.tag()));
        //map.setPriority(TicketPriority.valueOf(ticketDTO.priority()));
        //map.setDeadline(LocalDate.parse(ticketDTO.deadline(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return map;
    }

    /**
     * map {@link Tickets} to {@link TicketDTO}*/
    @Override
    public TicketDTO mapToDTO(Tickets tickets) {

        return new TicketDTO(tickets.getTicketName(),tickets.getDescription(),tickets.getTag().toString());
    }

    /**
     * map {@link Tickets} to {@link TicketNormalDTO}*/
    @Override
    public TicketNormalDTO mapToNormalDTO(Tickets tickets) {

        return new TicketNormalDTO(tickets.getTicketName(),tickets.getDescription(),tickets.getTag().toString(),tickets.getStatus().toString(),tickets.getPriority().toString(), tickets.getCreatedOn().toString());
    }

    /**
     * map {@link Tickets} to {@link TicketAgentDTO}*/
    @Override
    public TicketAgentDTO mapToAgentDTO(Tickets tickets) {

        return new TicketAgentDTO(tickets.getTicketName(),tickets.getDescription(),tickets.getTag().toString(),tickets.getDeadline().toString(),tickets.getStatus().toString(),tickets.getPriority().toString(),tickets.getUpdatedOn().toString());
    }
}
