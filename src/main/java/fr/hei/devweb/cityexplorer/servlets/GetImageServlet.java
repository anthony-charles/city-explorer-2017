package fr.hei.devweb.cityexplorer.servlets;

import fr.hei.devweb.cityexplorer.services.CityService;
import org.thymeleaf.context.WebContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/GetImage")
public class GetImageServlet extends AbstractGenericServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String cityId = request.getParameter("id");
        String path = CityService.getInstance().getImagePath(Integer.parseInt(cityId));

        response.setContentType("image/jpeg");
        Files.copy(Paths.get(path),response.getOutputStream());

    }
}
