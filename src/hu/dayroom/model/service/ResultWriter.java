package hu.dayroom.model.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultWriter {

	private final String fileName;

	public ResultWriter(String fileName) {
		this.fileName = fileName;
	}

	public void print(String text) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
			pw.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
