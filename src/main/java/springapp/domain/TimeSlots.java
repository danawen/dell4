package springapp.domain;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeSlots {
	public static List <Time> slots = new ArrayList<Time>();
	static {
		slots.add(Time.valueOf("08:00:00"));
		slots.add(Time.valueOf("08:30:00"));
		slots.add(Time.valueOf("09:00:00"));
		slots.add(Time.valueOf("09:30:00"));
		slots.add(Time.valueOf("10:00:00"));
		slots.add(Time.valueOf("10:30:00"));
		slots.add(Time.valueOf("11:00:00"));
		slots.add(Time.valueOf("11:30:00"));
		slots.add(Time.valueOf("12:00:00"));
		slots.add(Time.valueOf("12:30:00"));
		slots.add(Time.valueOf("13:00:00"));
		slots.add(Time.valueOf("13:30:00"));
		slots.add(Time.valueOf("14:00:00"));
		slots.add(Time.valueOf("14:30:00"));
		slots.add(Time.valueOf("15:00:00"));
		slots.add(Time.valueOf("15:30:00"));
		slots.add(Time.valueOf("16:00:00"));
		slots.add(Time.valueOf("16:30:00"));
		slots.add(Time.valueOf("17:00:00"));
		slots.add(Time.valueOf("17:30:00"));
	}
}
