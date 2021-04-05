package com.cos.javagg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankingDto {
    private int rank;
    private String summonerId;
    private String summonerName;
    private long leaguePoints;
    private long profileIconId;
}
