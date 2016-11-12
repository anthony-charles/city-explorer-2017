package fr.hei.devweb.cityexplorer.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import fr.hei.devweb.cityexplorer.services.CityService;

@WebServlet("/detail")
public class CityDetailServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = 8559083626521311046L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TemplateEngine templateEngine = this.createTemplateEngine(req);

		WebContext context = new WebContext(req, resp, getServletContext());
		
		Integer idCity = Integer.parseInt(req.getParameter("id"));
		context.setVariable("city", CityService.getInstance().getCity(idCity));
		
		templateEngine.process("citydetail", context, resp.getWriter());
	}

}
