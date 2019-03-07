package com.douguo.ndc.config.esconfig;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: lcyanxi
 * Date: 2018/8/17
 */

@Configuration public class EsCliectFacotry {
    public EsCliectFacotry() {
    }

    @Value("${es.clusterName}") private String clusterName;

    @Value("${es.serverIp}") private String serverIp;

    @Value("${es.serverPort}") private Integer serverPort;

    @Value("${caipu.es.clusterName}") private String caipuClusterName;

    @Value("${caipu.es.serverIp}") private String caipuServerIp;




    @Bean(name = "esClient") public TransportClient getESClient() {
        TransportClient client = null;
        try {
            Settings settings =
                Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true).build();
            client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(serverIp), serverPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean(name = "caipuEsClient") public TransportClient getCaipuEsClient() {
        TransportClient client = null;
        try {
            Settings settings =
                Settings.builder().put("cluster.name", caipuClusterName).build();
            client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(caipuServerIp), serverPort));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
