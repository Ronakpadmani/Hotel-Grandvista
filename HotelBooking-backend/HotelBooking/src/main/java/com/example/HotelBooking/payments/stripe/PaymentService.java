package com.example.HotelBooking.payments.stripe;

import com.example.HotelBooking.dtos.NotificationDTO;
import com.example.HotelBooking.entities.Booking;
import com.example.HotelBooking.entities.PaymentEntity;
import com.example.HotelBooking.enums.NotificationType;
import com.example.HotelBooking.enums.PaymentGateway;
import com.example.HotelBooking.enums.PaymentStatus;
import com.example.HotelBooking.exceptions.NotFoundException;
import com.example.HotelBooking.payments.stripe.dto.PaymentRequest;
import com.example.HotelBooking.repositories.BookingRepository;
import com.example.HotelBooking.repositories.PaymentRepository;
import com.example.HotelBooking.services.NotificationService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final NotificationService notificationService;

    @Value("${stripe.api.secret.key}")
    private String secretKey;

    public String createPaymentIntent(PaymentRequest paymentRequest) {

        log.info("Inside create payment intent");
        Stripe.apiKey=secretKey;
        String bookingReference = paymentRequest.getBookingReference();

        Booking booking = bookingRepository.findByBookingreference(bookingReference)
                .orElseThrow(() -> new NotFoundException("Booking Not found"));

        if(booking.getPaymentStatus() == PaymentStatus.COMPLETED){
            throw new NotFoundException("Payment already completed for this booking");
        }

        try{
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("usd")
                    .putMetadata("bookingReference", bookingReference)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getClientSecret();

        }catch(Exception e){
            throw new RuntimeException("Error in creating payment intent");
        }
    }

    public void updatePaymentBooking(PaymentRequest paymentRequest){

        log.info("Inside update payment booking");
        String bookingReference = paymentRequest.getBookingReference();

        Booking booking = bookingRepository.findByBookingreference(bookingReference)
                .orElseThrow(() -> new NotFoundException("Booking Not found"));

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentGateway(PaymentGateway.STRIPE);
        payment.setAmount(paymentRequest.getAmount());
        payment.setTransactionId(paymentRequest.getTransactionId());
        payment.setPaymentStatus(paymentRequest.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBookingReference(bookingReference);
        payment.setUser(booking.getUser());

        if(!paymentRequest.isSuccess()){
            payment.setFailureReason(paymentRequest.getFailureReason());
        }

        paymentRepository.save(payment); //save payment to DB

        //create and send Notification

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(booking.getUser().getEmail())
                .type(NotificationType.EMAIL)
                .bookingReference(bookingReference)
                .build();

        log.info("About to send notification inside updatePaymentBooking by sms");

        if(paymentRequest.isSuccess()){
            booking.setPaymentStatus(PaymentStatus.COMPLETED);
            log.info("Updating booking {}: setting paymentStatus to {}", booking.getId(), booking.getPaymentStatus());
            bookingRepository.save(booking);
            notificationDTO.setSubject("Payment Successful");
            notificationDTO.setBody("Congratulations!! Your payment for booking with reference " + bookingReference + " is successfully processed. Thank you for your booking.");
            notificationService.sendEmail(notificationDTO);
        }
        else{
            booking.setPaymentStatus(PaymentStatus.FAILED);
            log.info("Updating booking {}: setting paymentStatus to {}", booking.getId(), booking.getPaymentStatus());
            bookingRepository.save(booking);

            notificationDTO.setSubject("Payment Failed");
            notificationDTO.setBody("Your payment for booking with reference " + bookingReference + " failed with reason" + paymentRequest.getFailureReason());
            notificationService.sendEmail(notificationDTO);
        }




    }

}
