import java.io.IOException;
import java.net.URI;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
/*
 * Class to handle anything Spotify related(getting URI, getting tokens)
 * */
public class Spotify {
	private static final String clientId = "***REMOVED***";
	private static final String clientSecret = "***REMOVED***";
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

	
	public static URI getUriRequest() {
		final URI uri = authorizationCodeUriRequest.execute();
		return uri;
	}
	
	/*
	 * Set the code returned by the Spotify API and build the authorizationCodeRequest with the code
	 * */
	public static void setCode(String c) {
		System.out.println("Setting  Spotify Code");
		code= c;
		authorizationCodeRequest =  spotifyApi.authorizationCode(code).build();
	}
	
	/*
	 * Set accessTokens that can be used to make requests to the Web API
	 * */
	public static void makeTokenRequests() {
	    try {
	      authorizationCodeCredentials= authorizationCodeRequest.execute();

	      // Set access and refresh token for further "spotifyApi" object usage
	      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
	      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
	      
	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
}
