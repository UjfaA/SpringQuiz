package ujfaA.springQuiz.model;


public enum Role {
	
	USER("Basic", "user"),
	CONTRIBUTOR("Contributor", "cntr"),
	MODERATOR("Moderator", "mod"),
	ADMINISTRATOR("Administrator", "admn");
	
	public final String displayValue;
	public final String shortName; // arbitrary max lenght = 4 
	
	Role(String display, String shortName) {
		this.displayValue = display;
		this.shortName = shortName;
	}

	public static Role fromShortName(String shortName ) {		
		return switch (shortName) {
			case "user"	-> USER;
			case "cntr"	-> CONTRIBUTOR;
			case "mod"	-> MODERATOR;
			case "admn"	-> ADMINISTRATOR;
			default -> throw new IllegalArgumentException("Short[" + shortName + "] not supported.");
		};
	}
}