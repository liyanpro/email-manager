package cloud.liyan.emailmanager.dao.model.constant;

import cloud.liyan.emailmanager.dao.model.constant.HttpConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回结果
 * @title
 * @author liyan
 * @date 2018年4月17日 下午4:40:40
 */
public class ServiceResult {
	private String resultCode;
	private String errorCode;
	private String msg;

	public ServiceResult(String resultCode, String errorCode, boolean errorMsg) {
		this.resultCode = resultCode;
		if (StringUtils.isNotBlank(errorCode)) {
			this.errorCode = errorCode;
		}
		if (errorMsg) {
			this.msg = HttpConstants.MSG_MAP.get(errorCode);
		}
	}

	public ServiceResult(String resultCode, String msg) {
		this.resultCode = resultCode;
		if (StringUtils.isNotBlank(msg)) {
			this.msg = msg;
		}
	}
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
