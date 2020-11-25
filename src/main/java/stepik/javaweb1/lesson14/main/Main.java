package stepik.javaweb1.lesson14.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import stepik.javaweb1.lesson14.servlets.AllRequestsServlet;
import stepik.javaweb1.lesson14.servlets.MirrorRequestsServlet;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Main {

    private static final String startMsg = "Server started";

    //private static final Logger LOG = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        //AllRequestsServlet allRequestsServlet = new AllRequestsServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder( new MirrorRequestsServlet() ), "/mirror");
        context.addServlet(new ServletHolder( new AllRequestsServlet() ), "/*");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
        //LOG.info(startMsg);
        System.out.println(startMsg);

        server.join();
    }
}
