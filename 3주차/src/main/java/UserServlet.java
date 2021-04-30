import kr.ac.jejunu.User;
import kr.ac.jejunu.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UserServlet extends GenericServlet {
    UserDao userDao;
    @Override
    public void destroy() {
        System.out.println("****************** destroy *****************");
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("kr.ac.jejunu");
        userDao = applicationContext.getBean("userDao", UserDao.class);
        System.out.println("****************** init *****************");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("****************** service *****************");
        Integer id = Integer.parseInt(req.getParameter("id"));
        User user = userDao.findById(id);
        res.setContentType("text/html; charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html>");
        stringBuffer.append("<body");
        stringBuffer.append("<h1>");
        stringBuffer.append(String.format("hello %s",user.getName()));
        stringBuffer.append("</h1>");
        stringBuffer.append("</body");
        stringBuffer.append("</html>");
        res.getWriter().println(stringBuffer.toString());
    }
}
