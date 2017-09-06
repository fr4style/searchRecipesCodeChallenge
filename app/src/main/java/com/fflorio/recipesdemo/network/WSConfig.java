package com.fflorio.recipesdemo.network;

/**
 * Created by francesco on 2017-09-06.
 */

class WSConfig {

    final int connectionTimeoutInSeconds;
    final int readTimeoutInSeconds;
    final String baseUrl;

    private WSConfig(Builder builder) {
        connectionTimeoutInSeconds = builder.connectionTimeoutInSeconds;
        readTimeoutInSeconds = builder.readTimeoutInSeconds;
        baseUrl = builder.baseUrl;
    }


    public static final class Builder {
        private int connectionTimeoutInSeconds = 30;
        private int readTimeoutInSeconds = 30;
        private String baseUrl = "http://www.recipepuppy.com/";

        public Builder() {
        }

        public Builder connectionTimeoutInSeconds(int val) {
            connectionTimeoutInSeconds = val;
            return this;
        }

        public Builder readTimeoutInSeconds(int val) {
            readTimeoutInSeconds = val;
            return this;
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public WSConfig build() {
            return new WSConfig(this);
        }
    }
}
