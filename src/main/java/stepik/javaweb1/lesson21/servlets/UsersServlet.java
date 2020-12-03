package stepik.javaweb1.lesson21.servlets;

import com.google.gson.Gson;
import stepik.javaweb1.lesson21.accounts.AccountService;
import stepik.javaweb1.lesson21.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersServlet extends HttpServlet implements servletable{
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
    private final AccountService accountService;

    public UsersServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get public user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            if( "admin".equals( profile.getLogin() ) || "test".equals( profile.getLogin() ) ){
                UserProfile queredProfile = accountService.getUserByLogin( request.getParameter("login") );
                Gson gson = new Gson();
                String json = gson.toJson(queredProfile);
                response.setContentType(COMMON_CONTENT_TYPE);
                response.getWriter().println(json);
                response.getWriter().println(CALL_BACK_HTEXPR);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setContentType(COMMON_CONTENT_TYPE);
                response.getWriter().println( profile.getLogin() + " can't see user : " + request.getParameter("login") );
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }

    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");
        if(accountService.getUserByLogin(login)!=null || login == null || pass == null){
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } else {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("added");
            response.getWriter().println(CALL_BACK_HTEXPR);
            response.setStatus(HttpServletResponse.SC_OK);
            accountService.addNewUser( new UserProfile(login, pass, email) );
        }
    }

    //change profile
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        String pass = request.getParameter("pass"),
                email = request.getParameter("email");
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.addNewUser( new UserProfile(
                    profile.getLogin(),
                    pass==null?profile.getPass():pass,
                    email==null?profile.getEmail():email
                )
            );
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println("change.");
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println();
        }
    }

    //unregister
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        //todo: module 2 home work
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            if( "admin".equals( profile.getLogin() ) ){
                accountService.deleteUserUnregister(request.getParameter("login"));
                response.setContentType(COMMON_CONTENT_TYPE);
                response.getWriter().println("unregister");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setContentType(COMMON_CONTENT_TYPE);
                response.getWriter().println( profile.getLogin() + " can't delete user");
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            }
        }
    }

}
