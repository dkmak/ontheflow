
public class User {
	private final String displayname;
	private String token;
	private static final String clientId=" ";
	
	
    public User(String username, String token) {
        this.displayname = username;
        this.token= token;
        
    }

    public String getUsername() {
        return this.displayname;
    }
    
    public String getToken() {
    	return this.token;
    }
	
}
