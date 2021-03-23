package com.cos.javagg.dto;

import java.util.List;

import com.cos.javagg.model.api.ApiEntry;
import com.cos.javagg.model.api.ApiMatch;
import com.cos.javagg.model.api.ApiMatchEntry;
import com.cos.javagg.model.api.ApiSummoner;
import com.cos.javagg.model.detail.Match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoLDto {
	private ApiSummoner apiSummoner;
	private ApiMatchEntry apiMatchEntry;
	private List<ApiMatch> apiMatch;
	private List<ApiEntry> apiEntries;
}
