package springapp.service;


import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteException;

import springapp.command.AppointmentCommand;
import springapp.command.ClientCommand;
import springapp.dao.AppointmentDao;
import springapp.dao.ClientDao;
import springapp.dao.PetDao;
import springapp.domain.Client;
import springapp.domain.Pet;
import springapp.domain.Appointment;

@Service
public class AppointmentService {

	@Autowired 
	AppointmentDao appointmentDao;

	@Autowired 
	ClientDao clientDao;

	@Autowired 
	PetDao petDao;
	
	public List<Appointment> getAppointment(){
		return appointmentDao.list();
		
	}

	public Appointment getAppointment(String id) {
		return appointmentDao.get(Integer.parseInt(id));
	}
	
	public Appointment getAppointment(Integer id) {
		return appointmentDao.get(id);
	}

	public void deleteAppointment(String id) {
		appointmentDao.delete(Integer.parseInt(id));
	}

	
	public Appointment saveAppointment(AppointmentCommand toSave) throws SQLException, SQLiteException {
		Appointment appointment = new Appointment(toSave.getId(), toSave.getDate(), toSave.getTime(), toSave.getClient(), toSave.getPet());

		return appointmentDao.save(appointment);
	}
	

	
}
