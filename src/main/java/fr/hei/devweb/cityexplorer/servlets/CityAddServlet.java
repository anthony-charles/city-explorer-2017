package fr.hei.devweb.cityexplorer.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import fr.hei.devweb.cityexplorer.pojos.Country;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import fr.hei.devweb.cityexplorer.pojos.City;
import fr.hei.devweb.cityexplorer.services.CityService;

@WebServlet("/addcity")
@MultipartConfig
public class CityAddServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = -3497793006266174453L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TemplateEngine templateEngine = this.createTemplateEngine(req);
		
		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("countries", Country.values());
		if(req.getSession().getAttribute("cityCreationError") != null) {
			context.setVariable("errorMessage", req.getSession().getAttribute("cityCreationError"));
			context.setVariable("city", (City) req.getSession().getAttribute("cityCreationData"));

			req.getSession().removeAttribute("cityCreationError");
			req.getSession().removeAttribute("cityCreationData");
		} else {
			context.setVariable("city", new City());
		}
		
		templateEngine.process("cityadd", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String summary = req.getParameter("summary");
		String country = req.getParameter("country");

		Part picture = req.getPart("picture");
		City newCity = new City(null, name, summary, Country.valueOf(country), 0, 0);
		
		
		try {
			CityService.getInstance().addCity(newCity, picture);
			resp.sendRedirect("home");
		} catch (IllegalArgumentException e) {
			req.getSession().setAttribute("cityCreationError", e.getMessage());
			req.getSession().setAttribute("cityCreationData", newCity);
			resp.sendRedirect("addcity");
		}

	}

	
}
