package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dr_user_address {

    private Integer addressId;
    private String address;
    private String receiver;
    private String phone;
    private Integer isDefault;

}
