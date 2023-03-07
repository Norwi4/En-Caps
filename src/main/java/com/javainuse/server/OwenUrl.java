package com.javainuse.server;

public enum OwenUrl {
    auth("https://api.owencloud.ru/v1/auth/open"), // авторизация в системе
    device_list("https://api.owencloud.ru/v1/device/index"), // получение списка ПЛК компании
    device_in_plc("https://api.owencloud.ru/v1/device/"), // получение списка приборов на ПЛК
    last_data("https://api.owencloud.ru/v1/parameters/last-data"); // получение последних показателей датчиков

    private String url;
    OwenUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }
}
