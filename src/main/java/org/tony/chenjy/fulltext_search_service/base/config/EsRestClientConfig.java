package org.tony.chenjy.fulltext_search_service.base.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.tony.chenjy.fulltext_search_service.base.property.EsProperty;

import javax.annotation.Resource;

@Configuration
public class EsRestClientConfig implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(EsRestClientConfig.class);

    @Resource
    private EsProperty esProperty;
    private RestHighLevelClient client;

    @Override
    public RestHighLevelClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        buildClient();
    }

    private void buildClient() {
        HttpHost[] httpHosts = new HttpHost[esProperty.getUris().length];
        for (int i = 0; i < esProperty.getUris().length; i++) {
            httpHosts[i] = HttpHost.create(esProperty.getUris()[i]);
        }
        client = new RestHighLevelClient(RestClient.builder(httpHosts));
    }

    @Override
    public void destroy() throws Exception {
        try {
            logger.info("Closing elasticsearch rest client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing elasticsearch rest client: ", e);
        }
    }
}
