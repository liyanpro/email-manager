package cloud.liyan.emailmanager.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liyan
 * @description
 * @date 2019-09-07 12:32
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3000, TimeUnit.SECONDS)
			.readTimeout(5000, TimeUnit.SECONDS).build();

	public static int doResultGet(String url) {
		Request request = new Request.Builder().url(url).build();
		Response response = null;
		int code;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			logger.error("邮件发送失败");
		}
		code=response.code();
		response.close();
		return code;
	}
}
