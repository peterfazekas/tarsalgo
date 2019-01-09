package hu.dayroom.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import hu.dayroom.model.domain.LogEntry;
import hu.dayroom.model.domain.LogPair;

public class DayRoom {

	private final List<LogEntry> logEntries;

	public DayRoom(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}

	public String getFirstAndLastGuestInfo() {
		return String.format("%nAz első belépő: %d%nAz utolsó kilépő: %d", findFirstEnterId(), findLastExitId());
	}
	
	private int findFirstEnterId() {
		return logEntries.stream().filter(LogEntry::isEnter).findFirst().map(LogEntry::getId).get();
	}
	
	private int findLastExitId() {
		List<LogEntry> exitList = logEntries.stream().filter(LogEntry::isExit).collect(Collectors.toList());
		return exitList.get(exitList.size() - 1).getId();
	}

	public String getGateWayDetails() {
		return createMap().entrySet().stream()
				.map(i -> i.getKey() + " " + i.getValue())
				.collect(Collectors.joining("\r\n"));
	}
	
	private Map<Integer, Long> createMap() {
		return logEntries.stream()
				.collect(Collectors.groupingBy(LogEntry::getId, TreeMap::new, Collectors.counting()));
	}
	
	public String getDayRoomActualVisitorIds() {
		return createMap().entrySet().stream()
				.filter(i -> i.getValue() % 2 == 1)
				.map(i -> i.getKey())
				.map(i -> i.toString())
				.collect(Collectors.joining(" "));
	}
	
	public String getMostCroudedTime() {
		return String.format("Például %s-kor voltak a legtöbben a társalgóban.", getMostCroudedLocalTime());
	}
	
	private LocalTime getMostCroudedLocalTime() {
		return logEntries.stream()
				.sorted((j, i) -> i.getCounter().compareTo(j.getCounter()))
				.findFirst()
				.map(LogEntry::getTime)
				.get();
	}
	
	public String getTimeEntriesForVisitorById(int id) {
		return createPairs(id).stream()
				.map(LogPair::toString)
				.collect(Collectors.joining("\r\n"));
	}
	
	private List<LogPair> createPairs(int id) {
		List<LogEntry> entries = getEntriesbyId(id);
		List<LogPair> pairs = new ArrayList<>();
		int limit = entries.size() % 2 == 1 ? entries.size() - 1 : entries.size(); 
		for (int i = 0; i < limit; i += 2) {
			pairs.add(new LogPair(entries.get(i).getTime(), entries.get(i + 1).getTime()));
		}
		if (entries.size() % 2 == 1) {
			pairs.add(new LogPair(entries.get(entries.size() - 1).getTime()));
		}
		return pairs;
	}
	
	private List<LogEntry> getEntriesbyId(int id) {
		return logEntries.stream().filter(i -> i.getId() == id).collect(Collectors.toList());
	}
	
	public String getTotalRestTimeDetail(int id) {
		return String.format("A(z) %d. személy összesen %d percet volt bent, a megfigyelés végén %s", 
				id, calculateTotalRestTime(id), getConclusion(id));
	}
	
	private String getConclusion(int id) {
		List<LogPair> createPairs = createPairs(id);
		return createPairs.get(createPairs.size() - 1).isStay() 
				? "a társalgóban volt."
				: "nem volt a társalgóban.";
	}
	
	private long calculateTotalRestTime(int id) {
		return createPairs(id).stream().mapToLong(LogPair::getDuration).sum();
	}
}
