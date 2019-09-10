package cloud.liyan.emailmanager.api;

import cloud.liyan.emailmanager.dao.model.EmailTask;
import cloud.liyan.emailmanager.dao.model.MailTaskForWeb;
import cloud.liyan.emailmanager.dao.model.constant.ServiceResult;
import cloud.liyan.emailmanager.dao.model.constant.HttpConstants;
import cloud.liyan.emailmanager.service.Impl.MailServiceImpl;
import cloud.liyan.emailmanager.service.MailService;
import cloud.liyan.emailmanager.util.InitUtil;
import cloud.liyan.emailmanager.util.LoggerUtil;
import cloud.liyan.emailmanager.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Api(value = "EmailTaskApi", description = "邮件服务数据接口")
@RestController
@RequestMapping(value = "/api")
public class EmailTaskApi extends LoggerUtil {

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/create/EmailTask", method = RequestMethod.POST)
	@ApiOperation(value = "创建邮件任务", notes = "通过此接口创建要发送的邮件任务")
	@ApiImplicitParams({ // 请求参数描述
			@ApiImplicitParam(name = "json", value = "{'id':'邮件ID','domain':'域名','title':'邮件标题','content':'邮件内容'", required = true, dataType = "String") })
	public ServiceResult createUpdateEmailTask(@RequestBody JSONObject json, HttpServletRequest request) {
		ServiceResult serviceResult = null;
		String id = null;
		String title = null;
		String content = null;
		String domain = null;
		if (json != null) {
			try {
				id = json.getString("id").trim();
				domain = json.getString("domain").trim();
				title = URLDecoder.decode(json.getString("title"), "utf-8");
				content = URLDecoder.decode(json.getString("content"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				this.logger.error("解码失败", e);
			}
		}
		EmailTask mailTask = new EmailTask();
		mailTask.setCreateTime(new Date());
		mailTask.setDomain(domain);
		mailTask.setId(id);
		mailTask.setTitle(title);
		mailTask.setCount(0);
		mailTask.setStatus(InitUtil.STATUS_WKS);
		int result = mailService.createUpdateEmailTask(mailTask, content);
		if (result == 1) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "邮件任务创建成功");
		} else if (result == 2) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "邮件id:" + id + "的任务更新成功");
		} else {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "数据异常，操作失败");
		}
		return serviceResult;
	}

	@RequestMapping(value = "/test/EmailTask", method = RequestMethod.POST)
	@ApiOperation(value = "测试邮件发送", notes = "通过此接口给目标人发送测试邮件，验证邮件内容是否完整，多个地址以分号分割")
	@ApiImplicitParams({ // 请求参数描述
			@ApiImplicitParam(name = "id", value = "邮件ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "address", value = "邮件地址", required = true, dataType = "String") })
	public ServiceResult testEmailTask(HttpServletRequest request) {
		ServiceResult serviceResult = null;
		String id = StringUtil.convertRequestString(request, "id");
		String address = StringUtil.convertRequestString(request, "address");
		int result = mailService.testEmailTask(id, address);
		if (result > 0) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "测试邮件发送成功");
		} else if (result == 0) {
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "发送失败，邮件ID不存在");
		} else {
			serviceResult = new ServiceResult(HttpConstants.CODE_EXCEPTION, "发送失败，邮件发送出现异常");
		}
		return serviceResult;
	}

	@RequestMapping(value = "/upload/EmailAdress", method = RequestMethod.POST)
	@ApiOperation(value = "上传邮件地址", notes = "通过此接口上传当前邮件ID下要发送的邮件地址，文件为txt格式，一行一个邮件地址存储形式")
	@ApiImplicitParams({ // 请求参数描述
			@ApiImplicitParam(name = "id", value = "邮件ID", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "addressfile", value = "邮件地址文件", required = true, dataType = "MultipartFile") })
	public ServiceResult uploadEmailAdress(@RequestParam(value = "file", required = true) MultipartFile addressfile,
			HttpServletRequest request) {
		ServiceResult serviceResult = null;
		String id = StringUtil.convertRequestString(request, "id");
		try {
			String name = addressfile.getOriginalFilename();
			InputStream streamFile = null;
			byte[] byteFile = null;
			boolean isXlsx = true;
			if (name.endsWith(".xlsx") || name.endsWith(".xls")) {
				streamFile = addressfile.getInputStream();
				if (name.endsWith(".xls")) {
					isXlsx = false;
				}
			} else {
				byteFile = addressfile.getBytes();
			}
			if (byteFile == null && streamFile == null) {
				return new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_PARAMETER_MISS, true);
			}
			int result = mailService.uploadEmailAdress(id, byteFile, streamFile, isXlsx);
			if (result > 0) {
				serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "上传成功");
			} else if (result == 0) {
				serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "上传失败，邮件ID不存在", false);
			} else {
				serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
			}
		} catch (IOException e) {
			this.logger.error("获取文件二进制异常", e);
		}
		return serviceResult;
	}

	@RequestMapping(value = "/get/EmailAdress", method = RequestMethod.GET)
	@ApiOperation(value = "获取邮件任务列表", notes = "通过此接口获取全部邮件任务列表，任务状态：{0：未开始，1：进行中，2：暂停，3：结束}")
	@ApiImplicitParams({})
	public List<MailTaskForWeb> getEmailAdress(HttpServletRequest request) {
		List<MailTaskForWeb> taskList = mailService.getEmailTask();
		return taskList;
	}

	@RequestMapping(value = "/handle/EmailTask", method = RequestMethod.GET)
	@ApiOperation(value = "处理邮件任务", notes = "通过此接口处理相关的邮件，包括开始、暂停、停止、删除")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "邮件ID", required = true, dataType = "String"),
			@ApiImplicitParam(name = "status", value = "任务状态", required = true, dataType = "int"),
			@ApiImplicitParam(name = "source", value = "操作来源:1暂停任务  2结束任务", required = true, dataType = "int") })
	public ServiceResult handleEmailTask(HttpServletRequest request) {
		ServiceResult serviceResult = null;
		String id = StringUtil.convertRequestString(request, "id");
		Integer status = StringUtil.parseInt(StringUtil.convertRequestString(request, "status"));
		Integer source = StringUtil.parseInt(StringUtil.convertRequestString(request, "source"));
		int result = 0;
		try {
			// 未开始状态，执行邮件发送任务
			if (status == 0) {
				result = mailService.startSendEmail(id);
			}

			else if (status == 1 && source == 1) {// 暂停任务
				result = mailService.suspendSendEmail(id);
			}

			else if (status == 2) {// 重新开始任务
				result = mailService.restartSendEmail(id);
			}

			else if (status == 1 && source == 2) { // 结束任务
				result = mailService.stopSendEmail(id);
			} else if (status == 3 && source == 10) { // 删除任务
				result = mailService.deleteEmail(id);
			}
			if (result >= 0) {
				serviceResult = new ServiceResult(HttpConstants.RESUTL_OK, "操作成功");
			} else {
				serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, "操作失败");
			}
		} catch (Exception e) {
			logger.error("操作出现异常,邮件ID=" + id, e);
			serviceResult = new ServiceResult(HttpConstants.RESUTL_FAIL, HttpConstants.CODE_EXCEPTION, true);
		}
		return serviceResult;
	}
}
