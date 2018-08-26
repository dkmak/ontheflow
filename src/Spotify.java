import java.io.IOException;
import java.net.URI;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

// imports for requests related to artists
import com.wrapper.spotify.model_objects.specification.Artist;
import com.wrapper.spotify.requests.data.artists.GetArtistRequest;
/*
 * Class to handle anything Spotify related(getting URI, getting tokens)
 * */
public class Spotify {
	private static final String CLIENT_ID = "***REMOVED***";
	private static final String CLIENT_SECRET = "***REMOVED***";
	//redirect link currently goes to your local computer
	private static final URI REDIRECT_URI = SpotifyHttpManager.makeUri("http://localhost:8080/app/index.html");
	private static String code="";
	private static AuthorizationCodeRequest authorizationCodeRequest;
	private static AuthorizationCodeCredentials authorizationCodeCredentials;
	private static String accessToken;
	private static String refreshToken;
	
	
	//this is the API object. Once we give it all the proper credentials, we can make requests using this object.
	public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setClientId(CLIENT_ID)
		          .setClientSecret(CLIENT_SECRET)
		          .setRedirectUri(REDIRECT_URI)
		          .build();
	

	public static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email, playlist-read-collaborative, playlist-modify-public, playlist-read-private, playlist-modify-private")
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
	
	public static String returnCode() {
		return code;
	}
	/*check if tokens are still valid. If not, reset the access tokens for API use*/
	private static void checkTokens(){
		
	}
	
	/*
	 * Set accessTokens that can be used to make requests to the Web API
	 * */
	public static void makeTokenRequests() {
	    try {
	      authorizationCodeCredentials= authorizationCodeRequest.execute();

	      // Set access and refresh token for further "spotifyApi" object usage
	      accessToken = authorizationCodeCredentials.getAccessToken();
	      refreshToken = authorizationCodeCredentials.getRefreshToken();
	      
	      spotifyApi.setAccessToken(accessToken);
	      spotifyApi.setRefreshToken(refreshToken);
	      
	      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
	    } catch (IOException | SpotifyWebApiException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
	
	/*get Artist info given an id*/
	public static void getArtist(String id) {
		System.out.println(id);
		 GetArtistRequest getArtistRequest = spotifyApi.getArtist(id).build();
		     try {
		      final Artist artist = getArtistRequest.execute();
		      System.out.println("Name: " + artist.getName());
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    } 
	}
}
