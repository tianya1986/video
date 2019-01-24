package com.wolf.uc.entity;

import java.io.Serializable;

/**
 * <p>Title: 帐号       </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2019     </p>
 * <p>Company: ND Co., Ltd.       </p>
 * <p>Create Time: 2019年1月24日           </p>
 * @author Administrator
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 1614352032836403628L;

    /**
     * 帐号名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
