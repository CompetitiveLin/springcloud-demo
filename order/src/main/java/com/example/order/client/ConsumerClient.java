package com.example.order.client;

import com.example.activity.api.ProviderApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderClient", url = "localhost:8022")
public interface ConsumerClient extends ProviderApi {
}
