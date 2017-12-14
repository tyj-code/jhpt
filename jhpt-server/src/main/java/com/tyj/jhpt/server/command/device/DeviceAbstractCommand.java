/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.server.command.device;

import com.github.fartherp.framework.core.bean.ServiceLocator;
import com.tyj.jhpt.server.body.Body;
import com.tyj.jhpt.server.command.AbstractCommand;
import com.tyj.jhpt.server.handler.DeviceManagerServerHandler;
import com.tyj.jhpt.server.message.MessageBean;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/7/1
 */
public abstract class DeviceAbstractCommand extends AbstractCommand {
    protected byte type;

    protected Collection<Body> bodyList = ServiceLocator.getInstance().getBeansOfType(Body.class).values();

    public DeviceAbstractCommand(byte type) {
        super(type);
    }

    public void deal(DeviceManagerServerHandler handler, MessageBean mb) {

    }

    public MessageBean finish(MessageBean mb) {
        mb.setContent(new byte[]{0x00});
        return mb;
    }
}
