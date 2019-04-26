/**
 * 
 */
package cn.project.albumss.test;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;









import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author 陈超
 *
 */
public class testUrl {

	@Test
	public void test() {
		CloseableHttpClient client=HttpClients.createDefault();
		CloseableHttpResponse response =null;
		try {
			URI uri=new URI("https://www.iesdouyin.com/share/video/6602413056922225923/?region=CN&mid=6602413207996861191&u_code=c65k8if0&titleType=title&timestamp=1537331589&utm_campaign=client_share&app=aweme&utm_medium=ios&tt_from=copy&utm_source=copy&iid=43610100462");
			HttpGet get=new HttpGet(uri);
			response = client.execute(get);
//			HttpEntity entity = response.getEntity();
//			String res=EntityUtils.toString(entity,"UTF-8");
			System.out.println(response.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    public static void main(String[] args) throws Exception    {
        URL url = new URL("https://www.iesdouyin.com/share/video/6602413056922225923/?region=CN&mid=6602413207996861191&u_code=c65k8if0&titleType=title&timestamp=1537331589&utm_campaign=client_share&app=aweme&utm_medium=ios&tt_from=copy&utm_source=copy&iid=43610100462"); 
        String urlsource = getURLSource(url);
        System.out.println(urlsource);
    }
    
    /** *//**
     * 通过网站域名URL获取该网站的源码
     * @param url
     * @return String
     * @throws Exception
     */
    public static String getURLSource(URL url) throws Exception    {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream =  conn.getInputStream();  //通过输入流获取html二进制数据
        byte[] data = readInputStream(inStream);        //把二进制数据转化为byte字节数据
        String htmlSource = new String(data);
        return htmlSource;
    }
    
    /** *//**
     * 把二进制流转化为byte字节数组
     * @param instream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[]  buffer = new byte[1204];
        int len = 0;
        while ((len = instream.read(buffer)) != -1){
            outStream.write(buffer,0,len);
        }
        instream.close();
        return outStream.toByteArray();         
    }
    @Test
    public void pachong() throws IOException {
    	String preUrl="http://v.douyin.com/dxCJV1";//初始URL
    	URL url=new URL(preUrl);
    	HttpURLConnection conn=(HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("GET");
    	conn.setInstanceFollowRedirects(false);
    	conn.connect();
    	String location=conn.getHeaderField("Location");
    	//System.out.println(location);
    	Document doc = Jsoup.connect(location).get();
    	//System.out.println(doc.getAllElements());
    	String data=doc.getAllElements().toString();
    	//System.out.println(data);
    	String goal=data.substring(data.indexOf("playAddr:")+11,data.indexOf("&line=0")+7);
    	System.out.println(goal);
	}
}
