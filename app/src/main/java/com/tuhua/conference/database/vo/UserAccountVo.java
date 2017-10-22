package com.tuhua.conference.database.vo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户表
 * Created by yangtufa on 2017/10/21.
 */
@Entity(indexes = {
        @Index(value = "uId Asc", unique = true)
})
public class UserAccountVo {

    @Id(autoincrement = true)
    private Long uId;

    @Property
    private String username;

    @Property
    private String password;

    @Property
    private Long lastLoginTime;

    @Generated(hash = 514831447)
    public UserAccountVo(Long uId, String username, String password,
            Long lastLoginTime) {
        this.uId = uId;
        this.username = username;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
    }

    @Generated(hash = 386846576)
    public UserAccountVo() {
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getUId() {
        return this.uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }
}
