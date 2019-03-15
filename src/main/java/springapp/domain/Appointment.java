package springapp.domain;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	
//	Created Attributes for appointments
	
	private final Time time;
	private final Date date;
	private final Integer client;
	private final Integer pet;
	

	public Appointment(Integer client, Time time, Date date, Integer pet) {
		this.client = client;
		this.time = time;
		this.date = date;
		this.pet = pet;
	}
	
//	created getters
	public Time getTime() {
		return time;
	}


	public Date getDate() {
		return date;
	}


	public Integer getClient() {
		return client;
	}


	public Integer getPet() {
		return pet;
	}


	
	
}


