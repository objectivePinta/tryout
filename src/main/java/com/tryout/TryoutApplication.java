package com.tryout;


import com.tryout.entities.Appointment;
import com.tryout.entities.Business;
import com.tryout.entities.User;
import com.tryout.enums.UserRoles;
import com.tryout.enums.UserType;
import com.tryout.repository.AppointmentRepository;
import com.tryout.repository.BusinessRepository;
import com.tryout.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class TryoutApplication {

    public static void main(String[] args) {

        SpringApplication.run(TryoutApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    UserRepository userRepository;


//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        User user = User.builder()
//                .name("yam")
//                .email("a@a.com")
//                .username("ciucii")
//                .password("123")
//                .userType(UserType.BUSINESS)
//                .roles(List.of(UserRoles.ADMIN, UserRoles.CUSTOMER))
//                .build();
//        user = userRepository.save(user);
//        Business business = Business.builder()
//                .name("Sc srl")
//                .address("yaya")
//                .user(user)
//                .build();
//        businessRepository.save(business);
//        appointmentRepository.save(Appointment.builder().name("first")
//                .customer(user)
//                .duration(30)
//                .startingTime(LocalDateTime.now())
//                .build());
//        appointmentRepository.save(Appointment.builder().name("second")
//                .customer(user)
//                .duration(30)
//                .startingTime(LocalDateTime.now())
//                .build());
//        appointmentRepository.findAll().forEach(System.out::println);
//    }
}
