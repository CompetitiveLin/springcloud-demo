package com.example.order.vo;


import lombok.Data;

@Data
public class ActionsVo {
    private String ref = "master";
    private Inputs inputs;

    @Data
    public class Inputs {
        private String network_id;
        private String team;
    }


}
