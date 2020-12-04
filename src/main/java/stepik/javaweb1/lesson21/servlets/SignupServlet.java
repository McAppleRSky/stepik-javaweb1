package stepik.javaweb1.lesson21.servlets;

import org.eclipse.jetty.servlet.Source;
import stepik.javaweb1.lesson21.accounts.AccountService;
import stepik.javaweb1.lesson21.accounts.UserProfile;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet implements servletable {

    private final AccountService accountService;
    public SignupServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        //String email = request.getParameter("email");
        if(accountService.getUserByLogin(login)!=null || login == null || pass == null){
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            //response.setContentType(COMMON_CONTENT_TYPE);
            //response.getWriter().println("added");
            //response.getWriter().println(CALL_BACK_HTEXPR);
            response.setStatus(HttpServletResponse.SC_OK);
            accountService.addNewUser( new UserProfile(login, pass, "email") );
        }
    }

}
