package org.example;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String timeZone = request.getParameter("timezone");

        if (timeZone == null) {
            timeZone = "UTC";
        } else {
            timeZone = timeZone.replace(" ", "+");
        }

        if (isValidTimeZone(timeZone)) {
            chain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timeZone");
        }
    }

    private boolean isValidTimeZone(String timeZone) {
        if (timeZone.equals("UTC")) {
            return true;
        } else if ((timeZone.startsWith("UTC+") || timeZone.startsWith("UTC-")) && timeZone.length() > 4) {
            String offset = timeZone.substring(4);
            try {
                int hours = Integer.parseInt(offset);
                return hours >= -23 && hours <= 23;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
