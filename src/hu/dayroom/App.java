package hu.dayroom;

import hu.dayroom.controller.DayRoom;
import hu.dayroom.model.service.Console;
import hu.dayroom.model.service.FileDataReader;
import hu.dayroom.model.service.ResultWriter;

public class App {

	private final DayRoom dayRoom;
	private final ResultWriter writer;
	private final Console console;
	
	public App() {
		console = new Console();
		FileDataReader data = new FileDataReader();
		dayRoom = new DayRoom(data.getData("ajto.txt"));
		writer = new ResultWriter("atjaro.txt");
	}
	
	public static void main(String[] args) {
		new App().run();
	}
	
	private void run() {
		System.out.println("2. feladat:" + dayRoom.getFirstAndLastGuestInfo());
		writer.print(dayRoom.getGateWayDetails());
		System.out.println("4. feladat: A végén a társalgóban voltak: " + dayRoom.getDayRoomActualVisitorIds());
		System.out.println("5. feladat: " + dayRoom.getMostCroudedTime());
		int id = console.readInt("6. feladat: Adja meg a személy azonosítóját: ");
		System.out.println("7. feladat: \r\n" + dayRoom.getTimeEntriesForVisitorById(id));
		System.out.println("8. feladat: " + dayRoom.getTotalRestTimeDetail(id));
	}
	
	
}
