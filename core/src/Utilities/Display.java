package Utilities;

public enum Display {
	DISPLAY_1("Play"),
	DISPLAY_2("How to play"),
	DISPLAY_3("Awards"),
	DISPLAY_4("Credits"),
	DISPLAY_5("Settings");
	
	public final String name;
	
	Display(String label) {
		this.name = label;
	}
}
