package messagePack;

public class MessageType {
	private String[] types = {"login", "text", "logout", "Undefined"};
	
	public String login() {
		return types[0];
	}
	
	public String text() {
		return types[1];
	}
	
	public String logout() {
		return types[2];
	}
	
	public String undefined() {
		return types[3];
	}
}
