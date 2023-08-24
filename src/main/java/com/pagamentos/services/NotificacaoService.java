package com.pagamentos.services;

import com.pagamentos.domain.user.User;
import com.pagamentos.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacaoService {


    private RestTemplate restTemplate;

    public  void sendNotificacao(User user, String mensagem) throws Exception{
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, mensagem);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            System.out.println("erro ao enviar email");
            throw new Exception("Serviço de notificação esta fora do ar!");

        }
    }
}
