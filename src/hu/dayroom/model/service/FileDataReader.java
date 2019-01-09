package hu.dayroom.model.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hu.dayroom.model.domain.LogEntry;

public class FileDataReader {

	private int counter;
	
	public List<LogEntry> getData(String fileName) {
		return parse(fileRead(fileName));
	}
	
	private List<LogEntry> parse(List<String> lines) {
		return lines.stream().map(this::createLogEntry).collect(Collectors.toList());
	}
	
	private LogEntry createLogEntry(String line) {
		String[] items = line.split(" ");
		LocalTime time = LocalTime.of(Integer.parseInt(items[0]), Integer.parseInt(items[1]));
		int id = Integer.parseInt(items[2]);
		boolean enter = "be".equals(items[3]);
		if (enter) {
			counter++;
		} else {
			counter--;
		}
		return new LogEntry(time, id, enter, counter);
	}
	
	private List<String> fileRead(String fileName) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
