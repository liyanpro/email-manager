package cloud.liyan.emailmanager.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyan
 * @description
 * @date 2019-09-07 14:14
 */
public class StringUtil {
    private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    private static Pattern PATTERN_NUM = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
    /**
     * 对传入的参数进行过滤
     *
     * @param request
     * @param key
     * @return
     */
    public static String convertRequestString(HttpServletRequest request, String key) {
        String value = null;
        try {
            value = request.getParameter(key);
        } catch (Exception e) {
            logger.error("参数过滤异常", e);
        }
        return value;
    }

    public static int parseInt(String str) {
        int rtn = 0;
        try {
            if (StringUtils.isNotBlank(str)) {
                Matcher isNum = PATTERN_NUM.matcher(str);
                if (isNum.matches()) {
                    rtn = Integer.valueOf(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtn;
    }

    /**
     * 判断是否是邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^\\w+([-.]\\w+)*@\\w+([-]\\w+)*\\.(\\w+([-]\\w+)*\\.)*[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
