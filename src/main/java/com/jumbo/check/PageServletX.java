package com.jumbo.check;

import com.alibaba.dubbo.common.URL;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

    private final static String SERVICE = "service";
    private final static String WEB = "web";
    private final static String DEAMO = "deamo";

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

        String[] arr = query.split("=");
        String host = url.getParameter(arr[0]);
        ResourceBundle bundle;
        try{
            bundle = ResourceBundle.getBundle("hostConfig");
        }catch (Exception e){
            bundle = ResourceBundle.getBundle("conf/hostConfig");
        }

        String hostKey = query.replace("=", "");
        String host0 = bundle.getString(hostKey);

        if(SERVICE.equals(arr[0])){
            List<URL> providers = RegistryContainer.getInstance().getProvidersByHost(host0);
            if(providers == null || providers.size() < 1){
                throw  new HttpException(HttpStatus.ORDINAL_404_Not_Found);
            }
        }else if(WEB.equals(arr[0])){
            // HTTPCLIENT
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpgets = new HttpGet(host0);
            try{
                HttpResponse response = httpclient.execute(httpgets);
                StatusLine statusLine = response.getStatusLine();
                if(HttpStatus.ORDINAL_200_OK != statusLine.getStatusCode()){
                    throw  new HttpException(HttpStatus.ORDINAL_404_Not_Found);
                }
            }catch (Exception e){
                throw  new HttpException(HttpStatus.ORDINAL_404_Not_Found);
            }

        }else if(DEAMO.equals(arr[0])){
            // HTTPCLIENT   TODO deamo加页面

        }else{
            throw new IllegalArgumentException();
        }


        PrintWriter writer = resp.getWriter();
        writer.print(host0 + ": have " + arr[0]);
        writer.flush();
    }
}
