import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import models.Point;


@WebServlet("/areaCheck")
public class AreaCheckServlet extends HttpServlet {
    private static Logger logger;

    HttpServletResponse response;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        logger = ServerLogger.getInstance();
        long start = System.nanoTime();

        this.response = response;

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String X = request.getParameter("X");
        String Y = request.getParameter("Y");
        String R = request.getParameter("R");

        Point p = createPoint(X, Y, R);

        if (p != null) {
            logger.info(String.format("Point: X = %s, Y = %s, R = %s", p.getX(), p.getY(), p.getR()));

            ServletContext servletContext = request.getServletContext();
            
            long nano = (System.nanoTime() - start) / 1_000;
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
            p.setResult(checkPoint(p));
            p.setDate(date);
            p.setNano(nano);

            logger.info(String.format("%s", p.getResult()));

            List<Point> pointArr = savePointInContext(p, servletContext);
            request.setAttribute("pointArr", pointArr);
            servletContext.getRequestDispatcher("/table.jsp").forward(request,response);
        }
    }

    private List<Point> savePointInContext(Point p, ServletContext sc) {
        @SuppressWarnings("unchecked")
        List<Point> pointList = (List<Point>) sc.getAttribute("points");

        if (pointList == null) {
            pointList = Collections.synchronizedList(new ArrayList<>());
            sc.setAttribute("points", pointList);
        }
        pointList.add(p);
        return pointList;
    }

    private Point createPoint(String xStr, String yStr, String rStr) {
        if (rStr == null || xStr == null || yStr == null) {
            try {
                this.response.sendError(400, "R, X and Y must be provided as x-www-form-urlencoded params.");
            } catch (Exception e) {
                return null;
            }
        }

        BigDecimal r, x, y;
        r = toBigDecimal("R", rStr, "1", "3");
        x = toBigDecimal("X", xStr, "-5", "3");
        y = toBigDecimal("Y", yStr, "-5", "3");

        if (r == null || x == null || y == null) return null;

        BigDecimal multiplied = r.multiply(BigDecimal.valueOf(2));
        BigDecimal rounded = multiplied.setScale(0, RoundingMode.HALF_UP);

        r = rounded.divide(BigDecimal.valueOf(2), 1, RoundingMode.UNNECESSARY);
        y = y.setScale(0, RoundingMode.HALF_UP);

        return new Point(x, y.intValue(), r.floatValue());
    }

    private BigDecimal toBigDecimal(String name, String value, String MIN, String MAX) {
        try {
            BigDecimal x = new BigDecimal(value);
            BigDecimal min = new BigDecimal(MIN);
            BigDecimal max = new BigDecimal(MAX);
            if (x.compareTo(min) < 0 || x.compareTo(max) > 0) {
                try {
                    this.response.setStatus(400);
                    this.response.sendError(400, String.format("%s must be in [%s, %s]", name, min, max));
                } catch (Exception e) {
                    return null;
                }
                return null;
            }
            return x;
        } catch (NumberFormatException e) {
            try {
                this.response.sendError(400, String.format("%s must be a number", name));
            } catch (Exception z) {
                return null;
            }
        }
        return null;
    }

    private boolean checkPoint(Point p) {
        BigDecimal x = p.getX();
        BigDecimal y = new BigDecimal(p.getY());
        BigDecimal r = new BigDecimal(p.getR());

        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal two = new BigDecimal("2");
        BigDecimal four = new BigDecimal("4");

        // logger.info(String.format("\n%s < -%s : %s\n%s > %s/2 : %s\n%s < -%s : %s\n%s > %s/2 : %s", x, r, x.compareTo(r.negate()) < 0, x, r, x.compareTo(r.divide(two, RoundingMode.HALF_UP)) > 0, y, r, y.compareTo(r.negate()) < 0, y, r, y.compareTo(r.divide(two, RoundingMode.HALF_UP)) > 0));

        if (x.compareTo(r.divide(two, RoundingMode.HALF_UP)) > 0 ||
                x.compareTo(r.negate()) < 0 ||
                y.compareTo(r.negate()) < 0 ||
                y.compareTo(r.divide(two, RoundingMode.HALF_UP)) > 0) {
            return false;
        }

        BigDecimal xSquared = x.multiply(x);
        BigDecimal ySquared = y.multiply(y);
        BigDecimal sumSquares = xSquared.add(ySquared);
        BigDecimal rSquared = r.multiply(r);
        BigDecimal quarterCircle = rSquared.divide(four, 10, RoundingMode.HALF_UP);

        if (x.compareTo(zero) >= 0 && y.compareTo(zero) <= 0 && sumSquares.compareTo(quarterCircle) > 0) {
            return false; // 4 четверть — вне круга
        }

        if (x.compareTo(zero) <= 0 && y.compareTo(zero) >= 0) {
            BigDecimal lineValue = x.subtract(r.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP));
            if (y.compareTo(lineValue) > 0) {
                return false; // 2 четверть -  точка ниже линии — вне области
            }
        }

        if (x.compareTo(zero) > 0 && y.compareTo(zero) > 0) {
            return false; // 1 четверть — всегда вне области
        }

        return true;
    }
}
