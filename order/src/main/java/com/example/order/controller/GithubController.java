package com.example.order.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.order.client.GithubClient;
import com.example.order.vo.ActionsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GithubController {
    private final GithubClient githubClient;

    @GetMapping("/teams")
    public JSONObject getTeams(){
        List<String> list = new ArrayList<>();
        JSONArray jsonArray = githubClient.getTeams();
        jsonArray.forEach(o -> {
            list.add((String) ((Map<String, Object>) o).get("name"));
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("teams", list);
        return jsonObject;
    }

    @GetMapping("/memberships")
    public JSONObject getMemberships(@RequestParam String networkid){
        JSONObject jsonObject = githubClient.getMemberships(networkid).getJSONObject("user");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", jsonObject.get("login"));
        return jsonObject2;
    }

    @PostMapping("/actions")
    public void triggerActions(@RequestBody ActionsVo actionsVo){
        githubClient.triggerActions(actionsVo);
    }
}
