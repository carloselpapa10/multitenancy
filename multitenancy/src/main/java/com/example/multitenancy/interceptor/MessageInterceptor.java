package com.example.multitenancy.interceptor;

import com.example.multitenancy.constant.MultiTenantConstants;
import com.example.multitenancy.context.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.DirectWithAttributesChannel;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalChannelInterceptor
public class MessageInterceptor implements ChannelInterceptor {

    private final Logger log = LoggerFactory.getLogger(MessageInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> msg, MessageChannel mc) {
        String tenantId = (String) msg.getHeaders().get(MultiTenantConstants.X_TENANT_ID);
        System.out.println("Channel topic::" + ((DirectWithAttributesChannel) mc).getBeanName() + " || Search for X-TenantID  :: " + tenantId);

        if (tenantId == null) {
            log.error("Entry message does not contain a corresponding tenant in the header");
            return null;
        }

        TenantContext.setCurrentTenant(tenantId);
        return msg;
    }

    @Override
    public void postSend(Message<?> msg, MessageChannel mc, boolean bln) {
        TenantContext.clear();
    }

    @Override
    public void afterSendCompletion(Message<?> msg, MessageChannel mc, boolean bln, Exception excptn) {
        log.info("Message in afterSendCompletion");
    }

    @Override
    public boolean preReceive(MessageChannel mc) {
        log.info("Message in preReceive");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> msg, MessageChannel mc) {
        log.info("Message in postReceive");
        return msg;
    }

    @Override
    public void afterReceiveCompletion(Message<?> msg, MessageChannel mc, Exception excptn) {
        log.info("Message in afterReceiveCompletion");
    }
}
