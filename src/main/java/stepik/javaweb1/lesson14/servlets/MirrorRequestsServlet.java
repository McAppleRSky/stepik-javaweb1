package stepik.javaweb1.lesson14.servlets;

import stepik.javaweb1.lesson14.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class MirrorRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String key = null;
        Map<String, Object> pageVariables = new HashMap<>()
                //createPageVariablesMap(request)
                ;
        key = request.getParameter("key");
        //pageVariables.put("key", key==null?"":key);

        //response.getWriter().println(PageGenerator.instance().getPage("greet.html", pageVariables));

        response.getWriter().println(key);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
    }

}
