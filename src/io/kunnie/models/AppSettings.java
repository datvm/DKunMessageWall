package io.kunnie.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

/**
 * @author DatVM
 * @since 2014
 */
public class AppSettings {

	private static final String SETTINGS_FILE_PATH = "settings.dat";

	private static AppSettings instance;
	private ArrayList<PhoneNumber> numbers;

	private AppSettings() {
		this.numbers = new ArrayList<PhoneNumber>();
	}

	public static AppSettings getSettings(Context pContext) {
		if (instance == null) {
			instance = new AppSettings();

			FileInputStream stream = null;
			try {
				stream = pContext.openFileInput(SETTINGS_FILE_PATH);

				instance.load(stream);
			} catch (FileNotFoundException e) {
				// No file yet, do nothing
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
					}
				}
			}
		}

		return instance;
	}

	public void save(Context pContext) {
		FileOutputStream fos = null;
		try {
			fos = pContext.openFileOutput(SETTINGS_FILE_PATH,
					Context.MODE_PRIVATE);
			this.save(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void save(FileOutputStream pStream) throws IOException {
		StringBuilder builder = new StringBuilder();

		for (PhoneNumber number : this.numbers) {
			builder.append(number.getNumber());
			builder.append('\n');
			builder.append(number.getMessageCounter());
			builder.append("\n");
		}

		pStream.write(builder.toString().getBytes());
	}

	public void load(FileInputStream pStream) throws IOException {
		StringBuffer fileContent = new StringBuffer();

		byte[] buffer = new byte[1024];
		int n;
		while ((n = pStream.read(buffer)) != -1) {
			fileContent.append(new String(buffer, 0, n));
		}

		String[] parts = fileContent.toString().split("\n");
		this.numbers = new ArrayList<PhoneNumber>();

		for (int i = 0; i < parts.length; i += 2) {
			PhoneNumber number = new PhoneNumber();

			number.setNumber(parts[i]);
			number.setMessageCounter(Integer.parseInt(parts[i + 1]));

			this.numbers.add(number);
		}
	}

	public ArrayList<PhoneNumber> getNumbers() {
		return numbers;
	}

}
