package com.cos.javagg.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/id값?api_key=


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiEntry {

    private String leagueId;
    private String queueType;
    private String tier;
    private String rank;
    private String summonerId;
    private String summonerName;
    private long leaguePoints;
    private long wins;
    private long losses;
    private boolean veteran;
    private boolean inactive;
    private boolean freshBlood;
    private boolean hotStreak;

}