package net;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zhsk.bbktool.BBK_Tool_Byte;
import com.zhsk.bbktool.d;

public class BBKNetUDP {

	public static class GPS {
		// -----------------------------------------
		public boolean K;// 是否定位
		public boolean R;// 是否合理移动
		// -----------------------------------------
		public Date t;// GPS时间
		// -----------------------------------------
		public double w;// 纬度
		public double j;// 经度
		public double h;// 海拔
		public double v;// 速度
		public double f;// 方向
		// -----------------------------------------
		public int r;// 定位精度
		public int s;// 卫星数目
		public int u;// 参与解算卫星数目
		public int snr;// 卫星平均信号值
		public int usr;// 参与解算卫星数目
		// -----------------------------------------
		public double lw;// 上一次纬度
		public double lj;// 上一次经度
		// -----------------------------------------
		public double l;// 里程
		public double va;// 平均速度
		public double vm;// 最大速度
		// -----------------------------------------
		public Date ts;// 启动时间
		public double tl;// 持续时间
		public String tls;// 持续时间字符形式
		// -----------------------------------------
		public String a;// 标题
		public String i;// 信息
		public String vs;// 速度
		// -----------------------------------------
	}

	private static long tp = 0;//0gps,1MDU6050,

	public static byte[] toBytes(long id, GPS g) {
		// -----------------------------------------
		byte[] data = new byte[64];// 20+8*5+4=64
		// -----------------------------------------
		BBK_Tool_Byte.putLong4(data, id, 0);//long4//唯一标识
		BBK_Tool_Byte.putLong4(data, tp, 4);//long4//类型
		// -----------------------------------------
		BBK_Tool_Byte.putBoolean(data, g.K, 8);//boolean1//是否定位
		BBK_Tool_Byte.putLong8(data, g.t.getTime(), 12);//long4//GPS时间
		// -----------------------------------------
		BBK_Tool_Byte.putDouble(data, g.w, 20 + 0);//double8 纬度
		BBK_Tool_Byte.putDouble(data, g.j, 20 + 8);//double8 经度	
		BBK_Tool_Byte.putDouble(data, g.h, 20 + 16);//double8 高度	
		BBK_Tool_Byte.putDouble(data, g.v, 20 + 24);//double8 速度
		BBK_Tool_Byte.putDouble(data, g.f, 20 + 32);//double8 方向
		// -----------------------------------------
		BBK_Tool_Byte.putInt(data, g.r, 60 + 0); //int4定位精度
		// -----------------------------------------
		return data;
	}

	// -----------------------------------------
	public static GPS g = new GPS();
	public static long tm;

	public static void bufExp(byte[] b) {
		//d.s(b.toString());
		// -----------------------------------------
		long id = BBK_Tool_Byte.getLong4(b, 0);
		long tp = BBK_Tool_Byte.getLong4(b, 4);
		g.K = BBK_Tool_Byte.getBoolean(b, 8);
		tm = BBK_Tool_Byte.getLong8(b, 12);
		// -----------------------------------------		
		g.w = BBK_Tool_Byte.getDouble(b, 20 + 0);
		g.j = BBK_Tool_Byte.getDouble(b, 20 + 8);
		g.h = BBK_Tool_Byte.getDouble(b, 20 + 16);
		g.v = BBK_Tool_Byte.getDouble(b, 20 + 24);
		g.f = BBK_Tool_Byte.getDouble(b, 20 + 32);
		// -----------------------------------------	
		g.r = BBK_Tool_Byte.getInt(b, 60 + 0);
		// -----------------------------------------	
		d.s(id + "," + tp + "," + g.K + "," + tm);
		d.s(g.w + "," + g.j + "," + g.h + "," + g.v + "," + g.f);
		// -----------------------------------------
		adsfasdf();
	}

	//http://www.bbkgps.com/v/gs.php?n=boboking1&p=123456&t=1473321840394&g=39.984557,116.342592,56.0,0.5,0.0,8.0,1473321839999;
	public static final String mHostHead = "http://www.bbkgps.com/v/g.php?n=boboking1&p=123456";
	public static String mUrl = "";

	public static void adsfasdf() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			mUrl = mHostHead + "&g=" + g.w + "," + g.j + "," + g.h + "," + g.v + "," + g.f + "," + tm;
			d.s(mUrl);
			HttpGet httpget = new HttpGet(mUrl); // 创建httpget.  	   
			CloseableHttpResponse response = httpclient.execute(httpget);// 执行get请求. 
			try {
				// 获取响应实体    
				HttpEntity entity = response.getEntity();
				d.s("------------------------------------");
				System.out.println(response.getStatusLine()); // 打印响应状态    
				if (entity != null) {
					d.s("Response content length: " + entity.getContentLength()); // 打印响应内容长度 
					d.s("Response content: ");
					d.s(EntityUtils.toString(entity)); // 打印响应内容
				}
				d.s("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
		} catch (ParseException e) {
		} catch (IOException e) {
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
