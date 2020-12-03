package stepik.javaweb1.lesson21.servlets;

import com.google.gson.Gson;
import stepik.javaweb1.lesson21.accounts.AccountService;
import stepik.javaweb1.lesson21.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class SessionsServlet extends HttpServlet implements servletable{

    private final AccountService accountService;
    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
//            Gson gson = new Gson();
//            String json = gson.toJson(profile);
            response.setContentType(COMMON_CONTENT_TYPE);
            //response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);
            accountService.deleteSession(sessionId);
            response.getWriter().println("Goodbye!");
            response.getWriter().println(CALL_BACK_HTEXPR);
        }
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        if (login == null || pass == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserProfile profile = accountService.getUserByLogin(login);
        if ( profile == null || !profile.getPass().equals(pass) ) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.addSession(request.getSession().getId() ,profile);
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType(COMMON_CONTENT_TYPE);
        response.getWriter().println(json);
        response.getWriter().println(CALL_BACK_HTEXPR);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.deleteSession(sessionId);
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("Goodbye!");
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

}
