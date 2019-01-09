package hu.dayroom.model.domain;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class LogPair {

	private final LocalTime enter;
	private final LocalTime exit;
	
	public LogPair(LocalTime enter) {
		this(enter, null);
	}
	
	public LogPair(LocalTime enter, LocalTime exit) {
		this.enter = enter;
		this.exit = exit;
	}

	public long getDuration() {
		return exit == null 
				? ChronoUnit.MINUTES.between(enter, LocalTime.of(15, 0)) 
				: ChronoUnit.MINUTES.between(enter, exit);
	}
	
	public boolean isStay() {
		return exit == null;
	}

	@Override
	public String toString() {
		return exit == null ? enter + "-" : enter + "-" + exit;
	}
	
	
}
