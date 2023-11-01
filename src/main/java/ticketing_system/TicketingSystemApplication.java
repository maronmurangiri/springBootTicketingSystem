package ticketing_system;

import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ticketing_system.app.Business.implementation.EmailSenderService.EmailSenderService;

@SpringBootApplication
@EnableTransactionManagement
public class TicketingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemApplication.class, args);
    }

    @Bean
    //defining model mapper bean
    public ModelMapper modelMapper(){
    return new ModelMapper();
    }

    @Autowired
    private EmailSenderService senderService;





}

