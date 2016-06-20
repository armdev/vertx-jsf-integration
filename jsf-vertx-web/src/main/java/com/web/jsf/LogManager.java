package com.web.jsf;

import com.web.jsf.rest.RESTClientBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/logmanager"})
public class LogManager extends HttpServlet {

    private String message;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doActivation(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private void doActivation(HttpServletRequest request, HttpServletResponse response) throws IOException {

        FacesContext context = FacesUtil.getFacesContext(request, response);

        MessageBean appManager = context.getApplication().evaluateExpressionGet(
                context, "#{messageBean}", MessageBean.class);

        PrintWriter out = response.getWriter();
        message = (String) context.getExternalContext().getRequestParameterMap().get("message");

        //System.out.println("Got a new message " + message);
        appManager.getLogList().add(0, message);

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
