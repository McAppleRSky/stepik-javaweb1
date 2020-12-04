package stepik.javaweb1.lesson21.servlets;

import com.google.gson.Gson;
import org.eclipse.jetty.servlet.Source;
import stepik.javaweb1.lesson21.accounts.AccountService;
import stepik.javaweb1.lesson21.accounts.UserProfile;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SigninServlet extends HttpServlet implements servletable {
    private final AccountService accountService;
    public SigninServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        if (login == null || pass == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if ( profile == null || !profile.getPass().equals(pass) ) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.addSession(request.getSession().getId() ,profile);
        /*Gson gson = new Gson();
        String json = gson.toJson(profile);*/
        response.setContentType(COMMON_CONTENT_TYPE);
        response.getWriter().println("Authorized: "+profile.getLogin()
                                        //json
                                        );
        //response.getWriter().println(CALL_BACK_HTEXPR);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
