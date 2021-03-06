package springapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.sql.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteException;

import springapp.controller.AppointmentController;
import springapp.domain.Appointment;
import springapp.domain.Client;
import springapp.domain.Pet;

/**
 * This is the appointments dao that is responsible for managing the appointments info in the databsae.
 * The dao acts as the 'gatekeeper' between the rest of the code and the database
 */
@Repository
@Scope("singleton")
public class AppointmentDao {
	
	private Logger logger = LoggerFactory.getLogger(AppointmentDao.class);

	RowMapper<Appointment> simpleMapper = new RowMapper<Appointment>() {

		@Override
		public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Appointment(rs.getInt("id"), Date.valueOf(rs.getString("date")),Time.valueOf(rs.getString("time")), rs.getInt("client"),rs.getInt("pet"));
			//return new Appointment(rs.getInt("id"), Date.valueOf("2019-04-05"),Time.valueOf("12:00:00"), rs.getInt("client"),rs.getInt("pet"));
		}
	};
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    	
	public List<Appointment> list(){
		List<Appointment> queryResult = jdbcTemplate.query("SELECT id, time, date, client, pet FROM appointments",
				simpleMapper);
		
		return queryResult;
	}
	
	public List<Appointment> list(Date date) {
		// TODO Auto-generated method stub
		List<Appointment> queryResult = jdbcTemplate.query("SELECT id, time, date, client, pet FROM appointments where date = ? order by time",
				new Object[] {date.toString()},
				simpleMapper);
		
		return queryResult;
	}
	
	public List<Appointment> list(List<Pet> pets) {
		// TODO Auto-generated method stub
		String statement= "SELECT id, time, date, client, pet FROM appointments WHERE pet in ( ";
		Integer [] petIDs= new Integer[pets.size()];
		for (int i=0;i<pets.size(); i++) {
			if (i == (pets.size()-1)) statement += "? ";
			else statement += "? , ";
			petIDs[i]=new Integer(pets.get(i).getId());
		}
		statement += ") order by date";		
		
		List<Appointment> queryResult = jdbcTemplate.query(statement,
				petIDs,
				simpleMapper);
		
		return queryResult;
	}

	
	public Appointment get(int id) {
		List<Appointment> queryResult = jdbcTemplate.query("SELECT id, time, date, client,pet FROM appointments WHERE id = ? LIMIT 1", 
				new Object[] {id},
				simpleMapper);
		
		if(queryResult.isEmpty()) {
			return null;
		}
		
		return queryResult.get(0);
		
		
	}
	
	public Appointment save(Appointment appointment) throws SQLException, DataAccessException, SQLiteException {
		logger.info(appointment.toString());
		Integer id = appointment.getId();
		if(id == null) {
			
			KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement("INSERT INTO appointments (time, date, client, pet) VALUES (?, ?, ?, ?)");
					statement.setString(1, appointment.getTime().toString());
					statement.setString(2, appointment.getDate().toString());
					statement.setInt(3, appointment.getClient());
					statement.setInt(4, appointment.getPet());
					
					
					return statement;
		}
			}, holder);
			
			id = holder.getKey().intValue();
			
		} else {
			jdbcTemplate.update("UPDATE appointments SET time = ?, date = ? , client = ?, pet = ? WHERE id = ?",
					new Object[] {appointment.getTime().toString(), appointment.getDate().toString(), appointment.getClient(), appointment.getPet(), id});
		}
		
		return get(id);
	}
	
	
	public void delete(int id) {
		
		jdbcTemplate.update("DELETE FROM appointments WHERE id = ?",
				new Object[] {id});
	

	}


	
}
