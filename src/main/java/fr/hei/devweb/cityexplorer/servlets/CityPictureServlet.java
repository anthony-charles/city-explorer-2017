package fr.hei.devweb.cityexplorer.servlets;

import fr.hei.devweb.cityexplorer.services.CityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/picture")
public class CityPictureServlet extends AbstractGenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer cityId = Integer.parseInt(req.getParameter("id"));
        Path picturePath = CityService.getInstance().getCityPicturePath(cityId);

        resp.setContentType("image/jpeg");
        Files.copy(picturePath, resp.getOutputStream());


    }
}
