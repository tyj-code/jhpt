/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.server.command.device;

import com.tyj.jhpt.server.message.CommandEnum;
import com.tyj.jhpt.server.handler.DeviceManagerServerHandler;
import com.tyj.jhpt.server.message.MessageBean;
import com.tyj.jhpt.server.util.DeviceMsgUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;

import static com.tyj.jhpt.server.command.device.FourCommand.DataEnum.TIME;
import static com.tyj.jhpt.server.command.device.FourCommand.DataEnum.TRACE_NO;

/**
 * 车辆登出
 *
 * @author: CK
 * @date: 2017/12/8
 */
@Component
public class FourCommand extends DeviceAbstractCommand {
    public FourCommand() {
        super(CommandEnum.CAR_LOGOUT.getType());
    }

    public void deal(DeviceManagerServerHandler handler, MessageBean mb) {
        // 登出时间
        byte[] content = mb.getContent();
        Date time = DeviceMsgUtils.resolveTime(content, TIME.length);
        int offset = TIME.length;

        // 登出流水号
        byte [] bytes = new byte[TRACE_NO.length];
        System.arraycopy(content, offset, bytes, 0, TRACE_NO.length);
        offset += TRACE_NO.length;
        BigInteger bigInteger = new BigInteger(bytes);
        int traceNo = bigInteger.intValue();

    }

    public static enum DataEnum {
        TIME(6, "登出时间"),
        TRACE_NO(4, "登出流水号"),
        ;
        private int length;
        private String desc;
        DataEnum(int length, String desc) {
            this.length = length;
            this.desc = desc;
        }
    }
}
