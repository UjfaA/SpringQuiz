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
		
		switch (shortName) {
			case "user":
				return USER;
			case "cntr":
				return CONTRIBUTOR;
			case "mod":
				return MODERATOR;
			case "admn": 
				return ADMINISTRATOR;
			default:
				throw new IllegalArgumentException("Short[" + shortName + "] not supported.");
		}
	}
}