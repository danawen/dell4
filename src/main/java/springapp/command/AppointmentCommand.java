package springapp.command;
import springapp.domain.Appointment;
import springapp.domain.Client;
import springapp.domain.Pet;

import java.sql.Date;
import java.sql.Time;

public class AppointmentCommand {
	
	
	private Integer client;
	private Integer pet;
	private Date date;
	private Time time;
	
	
	public AppointmentCommand(Appointment appointment, Client client, Pet pet) {
		if(appointment != null) {
			this.client = client.getId();
			this.pet = pet.getId();
			this.date = appointment.getDate();
			this.time = appointment.getTime();
		}
	}


	public Integer getClient() {
		return client;
	}


	public void setClient(Integer client) {
		this.client = client;
	}


	public Integer getPet() {
		return pet;
	}


	public void setPet(Integer pet) {
		this.pet = pet;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Time getTime() {
		return time;
	}


	public void setTime(Time time) {
		this.time = time;
	}
	
	

}
