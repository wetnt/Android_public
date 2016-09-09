package net;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class bbkokhttp {

	public static void main(String[] args) {
		
		get();

//		try {
//			//1. 向指定URL发送GET方法的请求 
//			String urlName = "http://www.baidu.com";//url + "?" + param;  
//			URL realUrl = new URL(urlName);
//			//打开和URL之间的连接 
//			URLConnection conn = realUrl.openConnection();
//			//设置通用的请求属性 
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
//			//建立实际的连接 
//			conn.connect();
//			
//			
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//			//2. 向指定URL发送POST方法的请求
		//			URL realUrl = new URL(url);  
		//			//打开和URL之间的连接 
		//			URLConnection conn = realUrl.openConnection();   
		//			//设置通用的请求属性
		//			conn.setRequestProperty("accept", "*/*"); 
		//			conn.setRequestProperty("connection", "Keep-Alive");  
		//			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
		//			//发送POST请求必须设置如下两行
		//			conn.setDoOutput(true);  
		//			conn.setDoInput(true);  
		//			//获取URLConnection对象对应的输出流
		//			out = new PrintWriter(conn.getOutputStream());  
		//			//发送请求参数 
		//			out.print(param);
	}
	
    public static void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet("http://www.baidu.com/");  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

}
