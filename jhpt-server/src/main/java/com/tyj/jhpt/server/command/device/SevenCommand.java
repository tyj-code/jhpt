/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.server.command.device;

import com.github.fartherp.framework.common.util.ISOUtil;
import com.tyj.jhpt.server.message.CommandEnum;
import com.tyj.jhpt.server.message.type.CarAlarmMessage;
import com.tyj.jhpt.server.handler.DeviceManagerServerHandler;
import com.tyj.jhpt.server.message.MessageBean;
import com.tyj.jhpt.server.util.DeviceMsgUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;

import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.CURRENT_CAR_DRIVER_SPEED;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.DRIVER_ID;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.LATITUDE;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.LONGITUDE;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.LOCATION_STATUS;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.MESSAGE_BODY;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.MESSAGE_TYPE;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.TIME;
import static com.tyj.jhpt.server.command.device.SevenCommand.DataEnum.ALARM_ID;

/**
 * 车辆告警信息上报
 *
 * @author: CK
 * @date: 2017/12/8
 */
@Component
public class SevenCommand extends DeviceAbstractCommand {
    public SevenCommand() {
        super(CommandEnum.CAR_ALARM_MESSAGE_UPLOAD.getType());
    }

    public void deal(DeviceManagerServerHandler handler, MessageBean mb) {
        byte[] content = mb.getContent();
        // 数据采集时间
        Date time = DeviceMsgUtils.resolveTime(content, TIME.length);
        int offset = TIME.length;

        // 告警消息ID
        byte[] bytes = new byte[ALARM_ID.length];
        System.arraycopy(content, offset, bytes, 0, ALARM_ID.length);
        offset += ALARM_ID.length;
        BigInteger bigInteger = new BigInteger(bytes);
        int alarmId = bigInteger.intValue();

        // 驾驶员ID
        bytes = new byte[DRIVER_ID.length];
        System.arraycopy(content, offset, bytes, 0, DRIVER_ID.length);
        offset += DRIVER_ID.length;
        String driverId = ISOUtil.byte2hex(bytes);

        // 定位状态
        bytes = new byte[]{content[offset + LOCATION_STATUS.length]};
        offset += LOCATION_STATUS.length;
        byte locationStatus = bytes[0];

        // 经度
        double longitude = DeviceMsgUtils.readLatLongInfo(content, offset);
        offset += LONGITUDE.length;

        // 纬度
        double latitude = DeviceMsgUtils.readLatLongInfo(content, offset);
        offset += LATITUDE.length;

        // 当前车辆行驶速度
        bytes = new byte[CURRENT_CAR_DRIVER_SPEED.length];
        System.arraycopy(content, offset, bytes, 0, CURRENT_CAR_DRIVER_SPEED.length);
        offset += CURRENT_CAR_DRIVER_SPEED.length;
        bigInteger = new BigInteger(bytes);
        int speed = bigInteger.intValue();

        // 信息类型标志
        bytes = new byte[]{content[offset + MESSAGE_TYPE.length]};
        offset += MESSAGE_TYPE.length;
        byte alarmType = bytes[0];

        // 信息体
        if (CarAlarmMessage.LIMIT_SPEED_ALARM.getCode() == alarmType
                || CarAlarmMessage.SPEED_UP.getCode() == alarmType
                || CarAlarmMessage.SPEED_DOWN.getCode() == alarmType
                || CarAlarmMessage.LEFT_UP.getCode() == alarmType
                || CarAlarmMessage.RIGHT_UP.getCode() == alarmType) {
            bytes = new byte[]{content[offset + MESSAGE_BODY.length]};
            offset += MESSAGE_BODY.length;
            byte alarmBody = bytes[0];
        }
    }

    public static enum DataEnum {
        TIME(6, "数据采集时间"),
        ALARM_ID(4, "告警消息ID"),
        DRIVER_ID(3, "驾驶员ID"),
        LOCATION_STATUS(1, "定位状态"),
        LONGITUDE(4, "经度"),
        LATITUDE(4, "纬度"),
        CURRENT_CAR_DRIVER_SPEED(4, "当前车辆行驶速度"),
        MESSAGE_TYPE(1, "信息类型标志"),
        MESSAGE_BODY(1, "信息体"),
        ;
        private int length;
        private String desc;
        DataEnum(int length, String desc) {
            this.length = length;
            this.desc = desc;
        }
    }
}
