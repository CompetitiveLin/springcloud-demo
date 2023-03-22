package com.example.order.client;

import com.example.activity.api.ProviderApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "activity")
public interface ConsumerClient extends ProviderApi {
}
