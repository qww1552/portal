package kr.ac.jejunu;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequiredArgsConstructor
public class UserController implements Controller {
    private final UserDao userDao;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userDao.findById(Integer.valueOf(request.getParameter("id")));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping("/user")
    public User getUser(@RequestParam("id") Integer id) {
        return userDao.findById(id);
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("e", e);
        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload() {
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws IOException {
        String path = request.getServletContext().getRealPath("/")
                + "/WEB-INF/static/" + file.getOriginalFilename();
        File saveFile = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "/images/" + file.getOriginalFilename());
        return modelAndView;
    }
}
