/**
 * Handle the data returned by LoginServlet
 * @param resultDataString jsonObject
 */
function handleLoginResult(resultDataJson) {
    //resultDataJson = JSON.parse(resultDataString);

    console.log("handle login response");
    console.log(resultDataJson);
    console.log(resultDataJson["status"]);
    console.log(resultDataJson["link"]);

    // If login success, redirect to index.html page
    if (resultDataJson["status"] === "success") {
        window.location.replace(resultDataJson["link"]);
    }
    // If login fail, display error message on <div> with id "login_error_message"
    else {

        console.log("show error message");
        console.log(resultDataJson["message"]);
        jQuery("#login_error_message").text(resultDataJson["message"]);
    }
}

/**
 * Submit the form content with POST method
 * @param formSubmitEvent
 */
console.log('in login.js');
function submitLoginButton( ) {
    console.log("submit login form");

    // Important: disable the default action of submitting the form
    //   which will cause the page to refresh
    //   see jQuery reference for details: https://api.jquery.com/submit/
    //formSubmitEvent.preventDefault();

    jQuery.post(
        "api/login",
        // Serialize the login form to the data sent by POST request
        jQuery("#login_button").serialize(),
        (resultDataString) => handleLoginResult(resultDataString));

}

// Bind the submit action of the form to a handler function
jQuery("#login_button").click((event) => submitLoginButton(event));

