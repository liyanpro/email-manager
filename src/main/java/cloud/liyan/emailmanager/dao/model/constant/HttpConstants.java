package cloud.liyan.emailmanager.dao.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @title http返回结果
 * @author liyan
 * @date 2018年4月18日 上午10:45:08
 */
public class HttpConstants {
	/**
	 * 成功
	 */
	public static final String RESUTL_OK = "SUCCESS";
	/**
	 * 失败
	 */
	public static final String RESUTL_FAIL = "FAIL";

	/**
	 * 未知
	 */
	public static final String CODE_UNKNOWN = "unknown";
	/**
	 * 异常
	 */
	public static final String CODE_EXCEPTION = "exception";
	/**
	 * 参数缺失
	 */
	public static final String CODE_PARAMETER_MISS = "parameter_miss";
	/**
	 * 已经存在
	 */
	public static final String CODE_EXISTS_MAIL = "exists_mail";
	/**
	 * cookie名称
	 */
	public static final String COOLKIE_NAME = "emailtask";

	public static final String COOKIE_VALUE = "liyansuperEDM";

	public static Map<String, String> MSG_MAP = new HashMap<String, String>();

	static {
		MSG_MAP.put(CODE_PARAMETER_MISS, "缺少参数");
		MSG_MAP.put(CODE_UNKNOWN, "未知错误，请重新发起请求");
		MSG_MAP.put(CODE_EXCEPTION, "程序异常");
		MSG_MAP.put(CODE_EXISTS_MAIL, "已存在的邮件");
	}
}
