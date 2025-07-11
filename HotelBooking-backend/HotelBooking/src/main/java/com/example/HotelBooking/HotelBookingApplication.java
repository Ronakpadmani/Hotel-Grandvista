package com.example.HotelBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HotelBookingApplication {

//	@Autowired
//	private NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(HotelBookingApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		NotificationDTO notificationDTO = NotificationDTO.builder()
//				.type(NotificationType.EMAIL)
//				.recipient("ronakpadmani2903@gmail.com")
//				.body("I am testing from a command line")
//				.subject("Testing Email Sending")
//				.build();
//
//		notificationService.sendEmail(notificationDTO);
//
//	}
}
