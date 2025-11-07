import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Point;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");

        String X = request.getParameter("X");
        String Y = request.getParameter("Y");
        String R = request.getParameter("R");

        if (X != null && Y != null && R != null) {
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            request.getServletContext().getRequestDispatcher("/areaCheck").forward(request, response);
        } else {
            response.sendError(400, "Request hasn't data of X, Y and R.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        ServletContext servletContext = request.getServletContext();

        @SuppressWarnings("unchecked")
        List<Point> pointArr = (List<Point>) servletContext.getAttribute("points");

        if (pointArr == null) {
            pointArr = Collections.synchronizedList(new ArrayList<>());
            servletContext.setAttribute("points", pointArr);
        }

        request.setAttribute("pointArr", pointArr);     
        servletContext.getRequestDispatcher("/table.jsp").forward(request,response);
    }
}
