package com.cos.javagg.model.api;

import java.util.List;

import com.cos.javagg.model.detail.Entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiRanking {

    public String tier;
    public String leagueId;
    public String queue;
    public String name;
    public List<Entry> entries;

}