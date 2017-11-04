package fr.hei.devweb.cityexplorer.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.hei.devweb.cityexplorer.pojos.City;
import fr.hei.devweb.cityexplorer.pojos.Comment;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import fr.hei.devweb.cityexplorer.services.CityService;

@WebServlet("/detail")
public class CityDetailServlet extends AbstractGenericServlet {

	private static final long serialVersionUID = 8559083626521311046L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		Integer idCity = Integer.parseInt(req.getParameter("id"));
		City city = CityService.getInstance().getCity(idCity);
		if (city == null) {
			resp.sendRedirect("home");
			return;
		}

		TemplateEngine templateEngine = this.createTemplateEngine(req);

		WebContext context = new WebContext(req, resp, getServletContext());
		context.setVariable("city", city);
		context.setVariable("comments", CityService.getInstance().listCommentsByCity(idCity));
		
		templateEngine.process("citydetail", context, resp.getWriter());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pseudo = req.getParameter("pseudo");
		String message = req.getParameter("comment");

		Integer idCity = Integer.parseInt(req.getParameter("id"));

		Comment newComment = new Comment(null, pseudo, LocalDateTime.now(), message);

		CityService.getInstance().addComment(newComment, idCity);

		resp.sendRedirect(String.format("detail?id=%d", idCity));
	}
}
