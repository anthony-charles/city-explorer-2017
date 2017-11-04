package fr.hei.devweb.cityexplorer.servlets;

import fr.hei.devweb.cityexplorer.services.CityService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class CityDeletionServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer cityId = Integer.parseInt(req.getParameter("id"));
        String confirm = req.getParameter("confirm");
        if((confirm != null && Boolean.parseBoolean(confirm))) {
            CityService.getInstance().deleteCity(cityId);
            resp.sendRedirect("home");
            return;
        }

        TemplateEngine templateEngine = this.createTemplateEngine(req);
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("city", CityService.getInstance().getCity(cityId));

        templateEngine.process("citydeletion", context, resp.getWriter());
    }
}
