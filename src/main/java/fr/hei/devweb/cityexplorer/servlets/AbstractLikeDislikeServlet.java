package fr.hei.devweb.cityexplorer.servlets;

import fr.hei.devweb.cityexplorer.services.CityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Consumer;

public class AbstractLikeDislikeServlet extends AbstractGenericServlet {

    protected void handleLikeDislike(HttpServletRequest req, HttpServletResponse resp, Consumer<Integer> methodToCall) throws IOException {
        String cityId = req.getParameter("id");
        String returnPage = req.getParameter("returnpage");

        methodToCall.accept(Integer.parseInt(cityId));

        if ("home".equals(returnPage)) {
            resp.sendRedirect("home");
        } else {
            resp.sendRedirect(String.format("detail?id=%s", cityId));
        }
    }
}
