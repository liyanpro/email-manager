package cloud.liyan.emailmanager.util;

public class InitUtil {
	public static String HOST;
	/**
	 * 线程数量
	 */
	public static final int THREAD_NUM = 6;
	/**
	 * 任务队列中的key，value是任务ID
	 */
	public static final String TASK_KEY = "task";
	/**
	 * 任务ID的key
	 */
	public static final String TASK_ID = "id";
	/**
	 * 任务域名的key
	 */
	public static final String TASK_DOMAIN = "domain";
	/**
	 * 任务主机的key
	 */
	public static final String TASK_HOST = "host";
	/**
	 * 任务内容的key
	 */
	public static final String TASK_CONTENT = "content";
	/**
	 * 任务状态的key
	 */
	public static final String TASK_STATUS = "status";
	/**
	 * 任务标题的key
	 */
	public static final String TASK_TITLE = "title";
	/**
	 * 暂停任务的key
	 */
	public static final String TASK_SUSPEND = "suspend";
	/**
	 * 状态-未开始
	 */
	public static final String STR_STATUS_WKS = "0";
	/**
	 * 状态-进行中
	 */
	public static final String STR_STATUS_JXZ = "1";
	/**
	 * 状态-暂停
	 */
	public static final String STR_STATUS_ZT = "2";
	/**
	 * 状态-结束
	 */
	public static final String STR_STATUS_JS = "3";
	/**
	 * 状态-未开始
	 */
	public static final int STATUS_WKS = 0;
	/**
	 * 状态-进行中
	 */
	public static final int STATUS_JXZ = 1;
	/**
	 * 状态-暂停
	 */
	public static final int STATUS_ZT = 2;
	/**
	 * 状态-结束
	 */
	public static final int STATUS_JS = 3;


	/**
	 * 初始化host
	 */
	public static void mailInit() {
		HOST = ConfigUtils.getConf().getString("host");
	}
}
