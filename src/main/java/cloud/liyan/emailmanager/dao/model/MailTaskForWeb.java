package cloud.liyan.emailmanager.dao.model;

import io.swagger.annotations.ApiModelProperty;

public class MailTaskForWeb {

	@ApiModelProperty("邮件任务")
	private EmailTask mailTask;
	@ApiModelProperty("未发送数量")
	private Integer nCount;
	@ApiModelProperty("已发送数量")
	private Integer yCount;

	public EmailTask getMailTask() {
		return mailTask;
	}

	public void setMailTask(EmailTask mailTask) {
		this.mailTask = mailTask;
	}

	public Integer getnCount() {
		return nCount;
	}

	public void setnCount(Integer nCount) {
		this.nCount = nCount;
	}

	public Integer getyCount() {
		return yCount;
	}

	public void setyCount(Integer yCount) {
		this.yCount = yCount;
	}
}
