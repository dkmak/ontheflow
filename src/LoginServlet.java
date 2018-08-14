

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 private static final String clientId = "client";
		private static final String clientSecret = "secret";
		private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/app/index.html");

		private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setClientId(clientId)
		          .setClientSecret(clientSecret)
		          .setRedirectUri(redirectUri)
		          .build();
		private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
		          .state("x4xkmn9pu3j6ukrs8n")
		          .scope("user-read-birthdate,user-read-email")
		          .show_dialog(true)
		          .build();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    final URI uri = authorizationCodeUriRequest.execute();

	    System.out.println("URI: " + uri.toString());
	    response.setContentType("application/json");
	    
	    PrintWriter out = response.getWriter();
	    
	    JsonObject jsonObject = new JsonObject();
	    jsonObject.addProperty("link", uri.toString());
	    jsonObject.addProperty("status", "success");
	    
	    // write JSON string to output
	    System.out.println(jsonObject.toString());
        out.write(jsonObject.toString());
        // set response status to 200 (OK)
        response.setStatus(200);
        
        out.close();

	}

}
