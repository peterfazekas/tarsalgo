package hu.dayroom.model.domain;

import java.time.LocalTime;

public class LogEntry {

	private final LocalTime time;
	private final int id;
	private final boolean enter;
	private final int counter;
	
	public LogEntry(LocalTime time, int id, boolean enter, int counter) {
		this.time = time;
		this.id = id;
		this.enter = enter;
		this.counter = counter;
	}

	public LocalTime getTime() {
		return time;
	}

	public int getId() {
		return id;
	}

	public boolean isEnter() {
		return enter;
	}
	
	public boolean isExit() {
		return !enter;
	}

	public Integer getCounter() {
		return counter;
	}
	
}
