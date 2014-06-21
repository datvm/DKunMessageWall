package io.kunnie.models;

/**
 * @author DatVM
 * @since 2014
 */
public class PhoneNumber {

	private String number;
	private int messageCounter;
	
	public boolean matchNumber(String pNumber) {
		if (pNumber.equals(this.number)) {
			return true;
		}
		
		if (pNumber.startsWith("+") && this.number.startsWith("0")) {
			return pNumber.endsWith(number.substring(1));
		}
		
		return false;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getMessageCounter() {
		return messageCounter;
	}
	public void setMessageCounter(int messageCounter) {
		this.messageCounter = messageCounter;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%d)", this.number, this.messageCounter);
	}
	
}
