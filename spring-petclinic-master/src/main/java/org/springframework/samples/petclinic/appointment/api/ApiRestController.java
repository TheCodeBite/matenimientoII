/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.samples.petclinic.appointment.api;

import java.util.Collection;
import org.springframework.samples.petclinic.appointment.Appointment;
import org.springframework.samples.petclinic.appointment.AppointmentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MoisesDario
 */
@RestController
public class ApiRestController {
    
   private final AppointmentRepository appointmentRepository;
    
    public ApiRestController(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }
    //Obtiene las citas del usuario
    @GetMapping("/api/listado")
    public Collection<Appointment> All(){
        return this.appointmentRepository.getAppointmentsByOwner(2);
    }
}
