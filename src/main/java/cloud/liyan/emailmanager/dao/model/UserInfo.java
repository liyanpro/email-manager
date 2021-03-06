package cloud.liyan.emailmanager.dao.model;

import java.util.Date;

public class UserInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.user_id
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.head_image
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private String headImage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.name
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.last_login_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private Date lastLoginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.password
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.create_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.user_id
     *
     * @return the value of user_info.user_id
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.user_id
     *
     * @param userId the value for user_info.user_id
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.head_image
     *
     * @return the value of user_info.head_image
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.head_image
     *
     * @param headImage the value for user_info.head_image
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.name
     *
     * @return the value of user_info.name
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.name
     *
     * @param name the value for user_info.name
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.last_login_time
     *
     * @return the value of user_info.last_login_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.last_login_time
     *
     * @param lastLoginTime the value for user_info.last_login_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.password
     *
     * @return the value of user_info.password
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.password
     *
     * @param password the value for user_info.password
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.create_time
     *
     * @return the value of user_info.create_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.create_time
     *
     * @param createTime the value for user_info.create_time
     *
     * @mbggenerated Tue Sep 10 15:40:59 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}