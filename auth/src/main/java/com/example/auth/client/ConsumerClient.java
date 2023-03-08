package com.example.auth.client;


import com.example.activity.api.ProviderApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "authClient", url = "localhost:8022")
public interface ConsumerClient extends ProviderApi {

}
