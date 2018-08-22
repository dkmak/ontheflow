import java.io.IOException;
import java.net.URI;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
/*
 * Class to handle anything Spotify related
 * */
public class Spotify {
	private static final String clientId = "337b12eb2cb0479cadf15cd5108525f9";
	private static final String clientSecret = "277bbf2275294abcbe350356a10311f0";
	private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/app/index.html");
	private static String code="";
	private static AuthorizationCodeRequest authorizationCodeRequest;
	private static AuthorizationCodeCredentials authorizationCodeCredentials;
	
	public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setClientId(clientId)
		          .setClientSecret(clientSecret)
		          .setRedirectUri(redirectUri)
		          .build();
	

	public static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email")
		          .show_dialog(true)
		          .build();

	
	public void setCode(String c) {
		System.out.println("Setting Code");
		code= c;
		spotifyApi.authorizationCode(code).build();
	}
	
	//attempts to create accessTokens based on the out ids, and the code provided at login
	public static void makeTokenReqests() {
	    try {
	      authorizationCodeRequest.execute();

	      // Set access and refresh token for further "spotifyApi" object usage
	      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
	      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
	      
	      
	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
}
