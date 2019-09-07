package cloud.liyan.emailmanager.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liyan
 * @description
 * @date 2019-09-07 14:14
 */
public class LoggerUtil {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /****** info ******/
    protected void log_info(String log) {
        logger.info(log);
    }

    protected void log_info(String log, Object arg) {
        logger.info(log, arg);
    }

    protected void log_info(String log,Object arg1, Object arg2){
        logger.info(log, arg1, arg2);
    }

    protected void log_info(String log, Object[] argArray) {
        logger.info(log, argArray);
    }

    /****** debug ******/
    protected void log_debug(String log, Object[] argArray) {
        logger.debug(log, argArray);
    }

    /****** error ******/
    protected void log_error(String log, Exception e) {
        logger.error("ERROR_CLASS_" + this.getClass().getSimpleName() + " -- " + log, e);
    }
}
