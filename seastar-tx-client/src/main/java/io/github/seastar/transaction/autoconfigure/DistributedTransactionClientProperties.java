package io.github.seastar.transaction.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;


@ConfigurationProperties(prefix = "seastar.transaction.client")
public class DistributedTransactionClientProperties {

    @Getter
    private String url;

    @Setter
    @Getter
    private String localEndpoint;

    @Setter
    @Getter
    private Discovery discovery;


    public void setUrl(String url) {

        Assert.notNull(url, "URL 不能为空");

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        this.url = url;
    }

    public static class Discovery {
        public static final String DEFAULT_TX_SERVER = "seastar-tx-server";

        /**
         * Flag to indicate that config server discovery is enabled (config server URL
         * will be looked up via discovery).
         */
        private boolean enabled;
        /**
         * Service id to locate config server.
         */
        private String serviceId = DEFAULT_TX_SERVER;

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getServiceId() {
            return this.serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

    }
}
