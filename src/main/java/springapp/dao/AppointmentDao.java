package springapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import springapp.domain.Appointment;

/**
 * This is the appointments dao that is responsible for managing the appointments info in the databsae.
 * The dao acts as the 'gatekeeper' between the rest of the code and the database
 */
@Repository
@Scope("singleton")
public class AppointmentDao {

	RowMapper<Appointment> simpleMapper = new RowMapper<Appointment>() {

		@Override
		public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Appointment(rs.getTime("time"), rs.getDate("date"), rs.getInt("client"), rs.getInt("pet"));
		}
	};
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    	
	public List<Appointment> list(){
		List<Appointment> queryResult = jdbcTemplate.query("SELECT time, date, client, pet FROM appointments",
				simpleMapper);
		
		return queryResult;
	}
	
	public Appointment get(int id) {
		List<Appointment> queryResult = jdbcTemplate.query("SELECT time, date, client,pet FROM appointments WHERE id = ? LIMIT 1", 
				new Object[] {id},
				simpleMapper);
		
		if(queryResult.isEmpty()) {
			return null;
		}
		
		return queryResult.get(0);
		
		
	}
	
	public Appointment save(Appointment appointment) {
		Integer id = appointment.getId();
		if(id == null) {
			
			KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement("INSERT INTO clients(name, phone_number, address) VALUES (?, ?, ?)");
					statement.setString(1, client.getName());
					statement.setString(2, client.getPhoneNumber());
					statement.setString(3, client.getAddress());
					return statement;

				}
			}, holder);
			
			id = holder.getKey().intValue();
			
		} else {
			jdbcTemplate.update("UPDATE clients SET name = ?, phone_number = ? , address = ? WHERE id = ?",
					new Object[] {client.getName(), client.getPhoneNumber(), client.getAddress(), id});
		}
		
		return get(id);
	}
	
	
	public void delete(int id) {
		
		jdbcTemplate.update("DELETE FROM pets WHERE client_id = ?",
				new Object[] {id});
		
		
		jdbcTemplate.update("DELETE FROM clients WHERE id = ?",
				new Object[] {id});
		
	}
}
