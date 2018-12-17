package com.lswd.youpin.weixin.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lswd.youpin.weixin.model.data.article.ArticleSource;

import java.io.IOException;

/**
 *hanxiaoyu
 * Date: 20/11/15
 */
public class ArticleSourceDeserializer extends JsonDeserializer<ArticleSource> {

    @Override
    public ArticleSource deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return ArticleSource.from(parser.getIntValue());
    }
}
