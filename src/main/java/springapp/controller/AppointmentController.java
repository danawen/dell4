package springapp.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springapp.command.PetCommand;
import springapp.domain.Client;
import springapp.domain.Pet;
import springapp.service.ClientService;
import springapp.service.PetService;
import springapp.domain.Appointment;
import springapp.command.AppointmentCommand;
import springapp.service.AppointmentService;

/**
 * Controller that handles the pets pages
 *
 * Notice the @PreAuthorized annotations on the methods.
 *
 * Each method either returned the name of the view template that will geneder the page,
 * or specifies a redirect to another page/path
 *
 * Each mothod, will also populate a model object if needed. The model is merged in with the view template
 */
@Controller
@RequestMapping("/appointments")

public class AppointmentController {
	private Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	// injecting in a PetService instance
    @Autowired
	AppointmentService appointmentService;
	
	// injecting in a PetService instance
    @Autowired
	PetService petService;

    // injecting in a ClientService instance
	@Autowired
	ClientService clientService;

    /**
     * Handle listing all the pets
     * @param model the model to populate, and merge in with the view template
     * @return the view template for listing pets
     */
	@PreAuthorize("hasAuthority('LIST_PETS')")
	@GetMapping
	 public String listAppointment(Model model) {
	    // get the list of pets from the service
        List<Appointment> appointments = appointmentService.getAppointment();

        // we add the pets to the model
        // Note we are not adding the PetCommand instances, but Pet instances
		model.addAttribute("appointments", appointments);
        return "appointment/listAppointments";
    }

	


    /**
     * Get pet information and draw an edit page for single pet.
     * Also used to draw a new pet page
     * @param id the id of the pet we are editing, or new if this is for a new pet
     * @param clientId if this is for a new pet, then the client id is required
     * @param model the model that will be used when merging with the view template
     * @return the edit pet view template
     */
	/*	@PreAuthorize("hasAuthority('GET_APPOINTMENT')")
	@GetMapping("/{id}")
	public String getAppointment(@PathVariable("id") String id,
						 Model model,
						 @RequestParam(name="clientId", required=false) Integer clientId,
//****************!!!!!!!!!!!!!MIGHT NEED TO CHANGE CLIENT ID TO PET ID DEPENDING ON HOW WE MAP APPOINTMENTS******!!!!!!!!!!!!!!!!!!!! 
						 @RequestParam(name="saved", required = false) boolean saved) {


	    // we used this as flag so we can tell on the view template how we got here?
        // if a client id wass passed in, then we got to this page from the client edit page.
        // other wise we got here from the list pets page
        // this information can be used to figure out what page we should exit to
		model.addAttribute("fromClientPage", clientId != null);
//****************!!!!!!!!!!!!!MIGHT NEED TO CHANGE CLIENT ID TO PET ID DEPENDING ON HOW WE MAP APPOINTMENTS******!!!!!!!!!!!!!!!!!!!!		
        model.addAttribute("saved", saved);


        // if the id passed in is 'new' and no clientId is passed in, then we have a problem ....
		if(id.equals("new") && clientId == null) {
			throw new IllegalArgumentException("Cannot add a new appointment without a clientid");
		}

		AppointmentCommand AppointmentCommand;

		if(id.equals("new")) {
            // if the id is 'new' then we create a appt command that only has the client id filled in
            AppointmentCommand = new AppointmentCommand();
			
		} else {
            // else we should get the pet command that is a copy of the pet
            // so we get the pet from the service
            Pet pet = petService.getPet(id);
            // and we generate our command from the pet instance the service returns
			AppointmentCommand = new AppointmentCommand(appointment);
		}

		// the pet command should always have the clientid (unless the Pet instance from the service is missing an id)
        // which we should probably handle....

        // we get the client based on the client id in the command
		Client client = clientService.getClient(AppointmentCommand.getClientId());

		// we set the client instance in the pet command,
        // when we got the command earlier, we only had the clientid, but now we should have the full client object.
        // we do this because we want to display the client info (name) not just the id.
		AppointmentCommand.setClient(client);			

		// we add the command pet command instance to the mode (which has the client instance as well as the pet info)
		model.addAttribute("command", AppointmentCommand);
		return "appointment/editAppointment";
	}


    *//**
     * Save the pet info, or create a new pet based on the command
     * @param command the command to get the pet info from
     * @param redirectAttributes used to pass attributes to the get page after saving a pet
     * @param fromClientPage a flag so we know if this originated from the client page, or the pet list page
     * @return the view template to use once the save is successful
     *//*
	@PreAuthorize("hasAuthority('SAVE_Appointment')")
	@PostMapping
	 public String saveAppointmnet(AppointmentCommand command, RedirectAttributes redirectAttributes, boolean fromClientPage) {

        // we pass in the pet command to the service to update or create a new pet
        Appointment appointment = AppointmentService.saveAppointment(command);


        redirectAttributes.addAttribute("saved", true);
        if(fromClientPage) {
            redirectAttributes.addAttribute("clientId", appointment.getClientId());
        }
        return "redirect:/appointments/"+appointment.getId();

    }

    *//**
     * Delete a pet and redirect to either the pet listing page, or the client edit page
     * @param id the if of the pet
     * @param clientId an optional client id of the pet
     * @param redirectAttributes additional attributes to pass along to the page we redirect to
     * @return the path of the page we redirect to once the delete is done
     *//*
	@PreAuthorize("hasAuthority('DELETE_PET')")
	@GetMapping("/{id}/delete")
	public String deleteAppointment(@PathVariable("id") String id,
							@RequestParam(name="clientId", required=false) Integer clientId,
							RedirectAttributes redirectAttributes) {

	    // we pass the pet id to the service so it can delete the pet
		appointmentService.deleteAppointment(id);

		// a flag so the page we redirect to can tell that the delete was successful
		redirectAttributes.addFlashAttribute("deleted", true);

		if(clientId != null){
            // if a client id was passed in, then we redirect to the client edit page
			return "redirect:/clients/"+clientId;
		}
		
		//DIDN'T CHANGE THIS ABOVE NOT SURE WHAT THIS DOES********************************

		// otherwise we redirect to the petslisting page
		return "redirect:/appointment";

	}

*/
}