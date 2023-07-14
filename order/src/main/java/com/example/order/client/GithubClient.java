package com.example.order.client;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.order.interceptor.FeignRequestInterceptor;
import com.example.order.vo.ActionsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "githubClient", url = "${github.url}", configuration = FeignRequestInterceptor.class)
public interface GithubClient {
    @GetMapping(value = "/orgs/china-digital-hub/teams?per_page=100")
    JSONArray getTeams();


    @GetMapping(value = "/orgs/china-digital-hub/memberships/{networkId}")
    JSONObject getMemberships(@PathVariable(value = "networkId") String networkId);

    @PostMapping(value = "/repos/china-digital-hub/ActionTest/actions/workflows/main.yaml/dispatches")
    void triggerActions(@RequestBody ActionsVo actionsVo);

}