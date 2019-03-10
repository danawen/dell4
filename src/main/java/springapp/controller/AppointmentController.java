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

import springapp.command.ClientCommand;
import springapp.command.PetCommand;
import springapp.domain.Appointment;
import springapp.domain.Client;
import springapp.domain.Pet;
import springapp.service.AppointmentService;
import springapp.service.ClientService;
import springapp.service.PetService;

/**
 * Controller that handles the appointment pages
 *
 * Notice the @PreAuthorized annotations on the methods.
 *
 * Each method either returned the name of the view template that will geneder
 * the page, or specifies a redirect to another page/path
 *
 * Each method, will also populate a model object if needed. The model is merged
 * in with the view template
 */
@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	private Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	// Inject in a AppointmentService claass
	@Autowired
	AppointmentService appointmentService;

	/**
	 * The appointment page does not change based on the model and is a "static"
	 * page
	 * 
	 * @return appointment page view template
	 */
	//@PreAuthorize("hasAuthority('APPOINTMENTS')")
	@GetMapping
	public String appointments() {
		return "appointments/appointments";
	}

	/**
	 * Returns the name of the view template that should be used along witht the
	 * model to draw the list of clients
	 *
	 * Note that no addiontal path is specified, and that this method only handles a
	 * GET
	 * 
	 * @param model the model to populate for merging with the view
	 * @return the client list page template
	 */
//	 @PreAuthorize("hasAuthority('LIST_CLIENTS')")
//	 @GetMapping
//	 public String listAppointments(Model model) {
//   //    List<Appointment> clients = appointmentService.getClients();
//	//	model.addAttribute("clients", clients);
//		return "appointments/appointments";
//    }

	/**
	 * Generates the model for rendering the specific client page
	 * 
	 * @param id    the id of the client we want to render, if the value is 'new'
	 *              that is for creating a new client
	 * @param model the model to populate for merging with the view
	 * @return the client edit page template
	 */
	@PreAuthorize("hasAuthority('GET_CLIENT')")
	@GetMapping("/{id}")
	public String getAppointment(@PathVariable("id") String id, Model model, boolean saved) {

		model.addAttribute("saved", saved);

		// we could have used a different path for handling the create page but this
		// approach uses the same
		// template for both creating a new client and editing and existing client
		if (id.equals("new")) {
			// create an empty command object to merge with the view template
			model.addAttribute("command", new ClientCommand(null));
		} else {
			// since we have a valid id, get the client object from the service
			Client client = appointmentService.getClient(id);
			// we create a client command that can be used when the browser sends the save
			// object
			model.addAttribute("command", new ClientCommand(client));

			// we get the list of pets, and send those as is since we dont need a command to
			// carry changes to the pets
			// from this page
			model.addAttribute("pets", appointmentService.getPets(client.getId()));
		}
		return "clients/editClient";
	}

	/**
	 * Saves the updates to a client based on the command that was sent from the
	 * client side
	 * 
	 * @param command            the command corresponding with how the client
	 *                           object should be updated/created
	 * @param redirectAttributes holds the attribtues that we may want to pass to
	 *                           the get page after a save
	 * @return the edit client view template
	 */
	@PreAuthorize("hasAuthority('SAVE_CLIENT')")
	@PostMapping
	public String saveAppointment(ClientCommand command, RedirectAttributes redirectAttributes) {

		// NOTE: if we want to capture errors correctly, we would wrap the following
		// code in a try/catch
		// and the catch would add a nice error message to the mode
		// then the view template would render a nice error message

		// we pass the command to the service, and it nows how update/create a client
		// the service returns the new client object back to us after the save
		Client client = appointmentService.saveClient(command);

		// we add in a "saved" attribute so we can print a nice message indicating a
		// save was successfull
		redirectAttributes.addAttribute("saved", true);

		return "redirect:/clients/" + client.getId();

	}

	/**
	 * Deletes a client and redirects to list clients page
	 * 
	 * @param id                 the id of the client to delete
	 * @param redirectAttributes we use this instead of a Model object, because we
	 *                           want to pass some attributes to the list page
	 * @return redirect path to the list clients page
	 */
	@PreAuthorize("hasAuthority('DELETE_CLIENT')")
	@GetMapping("/{id}/delete")
	public String deleteAppointment(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		// NOTE to handle exceptions, we would wrap the following code in a try/catch
		// and in the catch forward to a different page

		// send the id passed in to the client service
		appointmentService.deleteClient(id);

		// add an attribute to the list page, so a nice message can be shown
		redirectAttributes.addFlashAttribute("deleted", true);

		// redirect to list clients path/page
		return "redirect:/clients";
	}

}
