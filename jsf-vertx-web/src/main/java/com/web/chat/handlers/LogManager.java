package com.web.chat.handlers;

import com.web.chat.beans.MessageBean;
import com.web.chat.utils.FacesUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@WebServlet(urlPatterns = {"/logmanager"})
public class LogManager extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private String message;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        traceBusLogs(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void traceBusLogs(HttpServletRequest request, HttpServletResponse response) throws IOException {

        FacesContext context = FacesUtil.getFacesContext(request, response);

        MessageBean appManager = context.getApplication().evaluateExpressionGet(
                context, "#{messageBean}", MessageBean.class);

        PrintWriter out = response.getWriter();
        message = context.getExternalContext().getRequestParameterMap().get("message");

        System.out.println("Servlet: Got a new message " + message);
        appManager.getLogList().add(0, message);

    }

}
