package ticketing_system.app.Business.implementation.ReportService;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ticketing_system.app.percistance.Entities.Message.Message;
import ticketing_system.app.percistance.Entities.userEntities.Department;
import ticketing_system.app.percistance.Entities.userEntities.Positions;
import ticketing_system.app.percistance.Entities.userEntities.Users;
import ticketing_system.app.percistance.repositories.MessageRepository.MessageRepository;
import ticketing_system.app.percistance.repositories.userRepositories.PositionRepository;
import ticketing_system.app.percistance.repositories.userRepositories.UserRepository;
import ticketing_system.app.preesentation.data.ReportDTOs.InboxMessageReportDTO;
import ticketing_system.app.preesentation.data.ReportDTOs.MessageReportDTOs;
import ticketing_system.app.preesentation.data.ReportDTOs.PositionReportDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageReportService {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private MessageRepository messageRepository;

        public MessageReportDTOs convertToReportDTO(Message message){
            Long senderId = message.getSenderId();
            Long receiverId = message.getReceiverId();
             String senderName = "";
            String receiverName = "";
            if (senderId!=0){
                Users sender = userRepository.findById(senderId).get();
                senderName = sender.getFirstname();
            }
            if (receiverId!=0){
                Users receiver = userRepository.findById(receiverId).get();
                receiverName = receiver.getFirstname();
            }

            return  new MessageReportDTOs(
                    message.getId(),
                    message.getContent(),
                    senderName,
                    receiverName,
                    message.getTimestamp()
            );
        }
        public List<MessageReportDTOs> convertToListReportDTOs(List<Message> messageList) {
            return messageList.stream()
                    .map(this::convertToReportDTO)
                    .collect(Collectors.toList());
        }


    public InboxMessageReportDTO convertToInboxReportDTO(Message message){
        Long senderId = message.getSenderId();
        String senderName = "";
        if (senderId!=0){
            Users sender = userRepository.findById(senderId).get();
            senderName = sender.getFirstname();
        }

        return  new InboxMessageReportDTO(
                message.getId(),
                message.getContent(),
                senderName,
                message.getTimestamp()
        );
    }
    public List<InboxMessageReportDTO> convertToListInboxReportDTOs(List<Message> messageList) {
        return messageList.stream()
                .map(this::convertToInboxReportDTO)
                .collect(Collectors.toList());
    }
        public ResponseEntity<?> exportReport() throws FileNotFoundException, JRException {
            List<Message> messages = messageRepository.findAll();
            List<MessageReportDTOs> messageReportDTOs = convertToListReportDTOs(messages);
            System.out.println(messages);
            System.out.println(messageReportDTOs);

            //load file and compile it
            File file = ResourceUtils.getFile("classpath:messageReportTemplate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(messageReportDTOs);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "KeMaTCo");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] reportBytes = null;
            //String fileName = "tickets.pdf";
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=messages.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            //return reportBytes;
            // Return the PDF content as a response with appropriate headers
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        }

    public ResponseEntity<?> exportReportByUser(Long userId) throws FileNotFoundException, JRException {
        List<Message> messages =  messageRepository.findAll().stream()
                .filter(message -> message.getReceiverId().equals(userId))
                .collect(Collectors.toList());
        List<MessageReportDTOs> messageReportDTOs = convertToListReportDTOs(messages);
        System.out.println(messages);
        System.out.println(messageReportDTOs);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:inboxMessageReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(messageReportDTOs);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=messages.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }

    public ResponseEntity<?> exportReportByKeyword(String keyword) throws FileNotFoundException, JRException {
        List<Message> messages =  messageRepository.findAll().stream()
                .filter(message -> message.getContent().contains(keyword))
                .collect(Collectors.toList());
        List<MessageReportDTOs> messageReportDTOs = convertToListReportDTOs(messages);
        System.out.println(messages);
        System.out.println(messageReportDTOs);

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:messageReportTemplate.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(messageReportDTOs);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "KeMaTCo");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte[] reportBytes = null;
        //String fileName = "tickets.pdf";
        reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=messages.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        //return reportBytes;
        // Return the PDF content as a response with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);

    }


        public ResponseEntity<?> exportMessageReport(Long messageId) throws FileNotFoundException, JRException {
            Optional<Message> message = messageRepository.findById(messageId);
            MessageReportDTOs messageReportDTOs = convertToReportDTO(message.get());
            List<MessageReportDTOs> singleMessageList = Arrays.asList(messageReportDTOs);


            //load file and compile it
            File file = ResourceUtils.getFile("classpath:messageReportTemplate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(singleMessageList);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "KeMaTCo");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] reportBytes = null;
            //String fileName = "tickets.pdf";
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);


            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=message.pdf");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            //return reportBytes;
            // Return the PDF content as a response with appropriate headers
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(reportBytes);

        }
    }

