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
public class SessionsServlet extends HttpServlet {
    private final AccountService accountService;
    private final String SESSION_MEMBER_ATTR = "userId" ,
            COMMON_CONTENT_TYPE = "text/html;charset=utf-8",
            LOGOUT_FORM = "<form align='right' name='form1' method='get' action=''><label><input name='submit2' type='submit' id='submit2' value='log out'></label></form>"
            //,LOGOUT_FORM2 = "<form align='right' name='form1' method='DELETE' action=''><label><input name='submit2' type='submit' id='submit2' value='log out'></label></form>"
            //LOGOUT_FORM3 = "<button data-method='DELETE'>doDelete</button>"
                    ;


    public SessionsServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    //get logged user profile
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String
        //long
        //LongId<UserProfile>
                sessionId = //new LongId<>(
                                            //Long.parseLong(
                                                    session.getId()
                                            //)
//                                                    )
                                                        ;

        UserProfile profile = accountService.getUserBySessionId(sessionId);
        if (profile == null) {
            response.setContentType(COMMON_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(profile);
            response.setContentType(COMMON_CONTENT_TYPE);
            response.getWriter().println(LOGOUT_FORM);
            response.getWriter().println(json);
            response.setStatus(HttpServletResponse.SC_OK);
            if (request.getParameter("submit2") != null)
                if(request.getParameter("submit2").equals("log out")){
                    accountService.deleteSession(sessionId);
                    response.getWriter().println("Goodbye!");
                }
        }
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        //String sessionString = request.getSession().toString();
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

        //accountService.addSession(request.getSession().getId(), profile);
        //Long userId = (Long) request.getSession().getAttribute("userId");
        //HttpSession session = request.getSession();
        //LongId <UserProfile> userId = new LongId( (Long)session.getAttribute(SESSION_MEMBER_ATTR) );


/*
        UserIdGenerator userIdGenerator = new UserIdGenerator();
        if ( userId.getId()==null ) {
            userId = userIdGenerator.getAndIncrement();
            session.setAttribute(SESSION_MEMBER_ATTR, userId.getId());
        }
*/
        accountService.addSession(request.getSession().getId()
                                    //(LongId<UserProfile>) session.getAttribute(SESSION_MEMBER_ATTR)
                                    ,profile);

        Gson gson = new Gson();
        String json = gson.toJson(profile);
        response.setContentType(COMMON_CONTENT_TYPE);
        response.getWriter().println(LOGOUT_FORM);
        response.getWriter().println(json);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    //sign out
    public void doDelete(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        //XLongId<User> sessionId = request.getSession().getId();
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
