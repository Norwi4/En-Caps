package com.javainuse.server.model.lastData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Модель для формирования списка id датчиков для запроса параметров
 */
public class DeviceIds {
    @JsonProperty("ids")
    List<DevId> ids;

    public List<DevId> getIds() {
        return ids;
    }

    public void setIds(List<DevId> ids) {
        this.ids = ids;
    }
}
