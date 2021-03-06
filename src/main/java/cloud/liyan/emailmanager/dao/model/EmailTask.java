package cloud.liyan.emailmanager.dao.model;

import java.util.Date;

public class EmailTask {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.id
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.title
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.count
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private Integer count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.status
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.domain
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private String domain;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.create_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column email_task.update_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.id
     *
     * @return the value of email_task.id
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.id
     *
     * @param id the value for email_task.id
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.title
     *
     * @return the value of email_task.title
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.title
     *
     * @param title the value for email_task.title
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.count
     *
     * @return the value of email_task.count
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.count
     *
     * @param count the value for email_task.count
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.status
     *
     * @return the value of email_task.status
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.status
     *
     * @param status the value for email_task.status
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.domain
     *
     * @return the value of email_task.domain
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public String getDomain() {
        return domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.domain
     *
     * @param domain the value for email_task.domain
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.create_time
     *
     * @return the value of email_task.create_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.create_time
     *
     * @param createTime the value for email_task.create_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column email_task.update_time
     *
     * @return the value of email_task.update_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column email_task.update_time
     *
     * @param updateTime the value for email_task.update_time
     *
     * @mbggenerated Sat Sep 07 14:03:29 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}