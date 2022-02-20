/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graficaprofesional2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class DaoConnection {
    
    String url_base = "http://localhost/WEbservice/";
    URL url;
    HttpURLConnection conn;
    Reader responsePost;
    Reader responseGet;

    public DaoConnection(String endPoint) {
        
        try {
            this.url  = new URL(url_base+endPoint);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DaoConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void setPost(Map<String, Object> params) throws UnsupportedEncodingException, IOException{
         
        StringBuilder postData = new StringBuilder();
        conn = (HttpURLConnection) this.url.openConnection();
      
              
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0)
                postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                    "UTF-8"));
        }
      
        
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        this.responsePost = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        
    }
    
    
    public void setGet(Map<String, Object> params) throws IOException {
        
        
           conn = (HttpURLConnection) this.url.openConnection();
           System.out.println("conn "+conn);
           conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
             conn.setDoOutput(true);
            this.responseGet =  new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            
            
    }
    
    public void destroyConn(){
        this.conn.disconnect();
        this.conn = null;
    }
    
    public Reader getResponsePost(){
        return this.responsePost;
        
    }
    
    public Reader getResponseGet(){
        return this.responseGet;
        
    }
    public void closeResponse() throws IOException{
        if(this.responseGet!=null){
            this.responseGet.close();
        }
        if(this.responsePost!=null){
             this.responsePost.close();
        }
      
         
    }

    
    
    
}
