package com.sixt.rental.demo.integration.kafka.serde;

import com.google.common.base.Charsets;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sixt.rental.demo.events.RawBookEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class RawBookEventSerializer implements Serializer<RawBookEvent> {
    @Override
    public byte[] serialize(String topic, RawBookEvent data) {
        try {
            return JsonFormat.printer().print(data).getBytes(Charsets.UTF_8);
        } catch (InvalidProtocolBufferException e) {
            log.error("could not write JSON", e);
            return null;
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }
}
