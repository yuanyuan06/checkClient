package com.jumbo.check;

import com.alibaba.dubbo.common.URL;
import org.mortbay.jetty.HttpException;
import org.mortbay.jetty.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2016/1/19.
 */
public class PageServletX extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     *
     * 监控service端服务是否正常
     * TODO 监控client端的运行运行状态
     * http://localhost:8080/host?host=2
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String query = req.getQueryString();
        URL url = URL.valueOf(req.getRequestURL().toString() + (query == null || query.length() == 0 ? "" : "?" + query));
        String host = url.getParameter("host");
        ResourceBundle bundle;
        try{
            bundle = ResourceBundle.getBundle("hostConfig");
        }catch (Exception e){
            bundle = ResourceBundle.getBundle("conf/hostConfig");
        }
        String host0 = bundle.getString("host" + host);

        List<URL> providers = RegistryContainer.getInstance().getProvidersByHost(host0);
        if(providers == null || providers.size() < 1){
//            throw new HTTPException(HttpStatus.ORDINAL_404_Not_Found);
            throw  new HttpException(HttpStatus.ORDINAL_404_Not_Found);
        }

        PrintWriter writer = resp.getWriter();
        writer.print(host0 + ": have services");
        writer.flush();
    }
}
