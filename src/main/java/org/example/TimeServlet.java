package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timeZone = request.getParameter("timezone");

        if (timeZone == null) timeZone = "UTC";
        timeZone = timeZone.replace(" ", "+");

        String currentTime = ZonedDateTime
                .now(ZoneId.of(timeZone))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ")) + timeZone;

        response.getWriter().write(currentTime);
        response.getWriter().close();
    }
}
