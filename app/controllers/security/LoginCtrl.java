package controllers.security;

import play.mvc.*;
import play.data.*;

// Import required classes
import javax.inject.Inject;


import models.users.*;
import views.html.*;

public class LoginCtrl extends Controller {

    /** http://stackoverflow.com/questions/15600186/play-framework-dependency-injection **/
    private FormFactory formFactory;

    /** http://stackoverflow.com/a/10159220/6322856 **/
    @Inject
    public LoginCtrl(FormFactory f) {
        this.formFactory = f;
    }


    public Result login() {
	   // Pass a login form to the login view and render
	   return ok(login.render(formFactory.form(Login.class), User.getLoggedIn(session().get("email"))));
    }

    // Process the user login form
    public Result loginSubmit() {
        // Bind form instance to the values submitted from the form  
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        // Check for errors
        // Uses the validate method defined in the Login class
        if (loginForm.hasErrors()) {
            // If errors, show the form again
            return badRequest(login.render(loginForm, User.getLoggedIn(session().get("email"))));
        } 
        else {
            // User Logged in successfully
            // Clear the existing session
            session().clear();
            // Store the logged in email in the session
            session("email", loginForm.get().email);
            
            // Check user type
            User u = User.getLoggedIn(loginForm.get().email);
            // If admin - go to admin section
            if (u != null && "admin".equals(u.role)) {
                return redirect(controllers.routes.AdminProductCtrl.index());
            }
            
            // Return to home page
            return redirect(controllers.routes.ProductCtrl.index());
        }
    }	

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.LoginCtrl.login()
        );
    }
}
