package com.example.gatewayservice.model;

import com.example.commonapi.parameter.enumable.EPermission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyPermission {
    private EPermission verifyRole;
}
