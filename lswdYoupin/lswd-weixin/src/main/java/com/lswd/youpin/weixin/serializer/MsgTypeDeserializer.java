package com.lswd.youpin.weixin.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lswd.youpin.weixin.model.data.msg.MsgType;

import java.io.IOException;

/**
 *hanxiaoyu
 * Date: 20/11/15
 */
public class MsgTypeDeserializer extends JsonDeserializer<MsgType> {

    @Override
    public MsgType deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return MsgType.from(parser.getIntValue());
    }
}
