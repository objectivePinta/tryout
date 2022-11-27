package com.tryout;


import com.tryout.entities.Appointment;
import com.tryout.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TryoutApplication implements CommandLineRunner {

  public static void main (String[] args) {

    SpringApplication.run(TryoutApplication.class, args);
  }


  @Autowired
  AppointmentRepository appointmentRepository;


  @Override
  public void run (String... args) throws Exception {

    appointmentRepository.save(Appointment.builder().name("first").build());
    appointmentRepository.findAll().forEach(System.out::println);
  }
}
