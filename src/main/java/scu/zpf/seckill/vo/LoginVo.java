package scu.zpf.seckill.vo;

import org.hibernate.validator.constraints.Length;
import scu.zpf.seckill.validator.IsPhone;

import javax.validation.constraints.NotNull;

public class LoginVo {

    @IsPhone
    private String phone;

    @NotNull
    @Length(min = 32)
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo [phone=" + phone + ", password=" + password + "]";
    }
}
