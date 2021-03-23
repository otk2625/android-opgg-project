package com.cos.javagg.champ;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ChampionList {

    // 챔피언 데이터
    public String getChampName(long champId) {

        String champName = "";

        if (champId == 266) {
            champName = "Aatrox";
        } else if (champId == 103) {
            champName = "Ahri";
        } else if (champId == 84) {
            champName = "Akali";
        } else if (champId == 12) {
            champName = "Alistar";
        } else if (champId == 32) {
            champName = "Amumu";
        } else if (champId == 34) {
            champName = "Anivia";
        } else if (champId == 1) {
            champName = "Annie";
        } else if (champId == 523) {
            champName = "Aphelios";
        } else if (champId == 22) {
            champName = "Ashe";
        } else if (champId == 136) {
            champName = "AurelionSol";
        } else if (champId == 268) {
            champName = "Azir";
        } else if (champId == 432) {
            champName = "Bard";
        } else if (champId == 53) {
            champName = "Blitzcrank";
        } else if (champId == 63) {
            champName = "Brand";
        } else if (champId == 201) {
            champName = "Braum";
        } else if (champId == 51) {
            champName = "Caitlyn";
        } else if (champId == 164) {
            champName = "Camille";
        } else if (champId == 69) {
            champName = "Cassiopeia";
        } else if (champId == 31) {
            champName = "Chogath";
        } else if (champId == 42) {
            champName = "Corki";
        } else if (champId == 122) {
            champName = "Darius";
        } else if (champId == 131) {
            champName = "Diana";
        } else if (champId == 119) {
            champName = "Draven";
        } else if (champId == 36) {
            champName = "DrMundo";
        } else if (champId == 245) {
            champName = "Ekko";
        } else if (champId == 60) {
            champName = "Elise";
        } else if (champId == 28) {
            champName = "Evelynn";
        } else if (champId == 81) {
            champName = "Ezreal";
        } else if (champId == 9) {
            champName = "Fiddlesticks";
        } else if (champId == 114) {
            champName = "Fiora";
        } else if (champId == 105) {
            champName = "Fizz";
        } else if (champId == 3) {
            champName = "Galio";
        } else if (champId == 41) {
            champName = "Gangplank";
        } else if (champId == 86) {
            champName = "Garen";
        } else if (champId == 150) {
            champName = "Gnar";
        } else if (champId == 79) {
            champName = "Gragas";
        } else if (champId == 104) {
            champName = "Graves";
        } else if (champId == 120) {
            champName = "Hecarim";
        } else if (champId == 74) {
            champName = "Heimerdinger";
        } else if (champId == 420) {
            champName = "Illaoi";
        } else if (champId == 39) {
            champName = "Irelia";
        } else if (champId == 427) {
            champName = "Ivern";
        } else if (champId == 40) {
            champName = "Janna";
        } else if (champId == 59) {
            champName = "JarvanIV";
        } else if (champId == 24) {
            champName = "Jax";
        } else if (champId == 126) {
            champName = "Jayce";
        } else if (champId == 202) {
            champName = "Jhin";
        } else if (champId == 222) {
            champName = "Jinx";
        } else if (champId == 145) {
            champName = "Kaisa";
        } else if (champId == 429) {
            champName = "Kalista";
        } else if (champId == 43) {
            champName = "Karma";
        } else if (champId == 30) {
            champName = "Karthus";
        } else if (champId == 38) {
            champName = "Kassadin";
        } else if (champId == 55) {
            champName = "Katarina";
        } else if (champId == 10) {
            champName = "Kayle";
        } else if (champId == 141) {
            champName = "Kayn";
        } else if (champId == 85) {
            champName = "Kennen";
        } else if (champId == 121) {
            champName = "Khazix";
        } else if (champId == 203) {
            champName = "Kindred";
        } else if (champId == 240) {
            champName = "Kled";
        } else if (champId == 96) {
            champName = "KogMaw";
        } else if (champId == 7) {
            champName = "Leblanc";
        } else if (champId == 64) {
            champName = "LeeSin";
        } else if (champId == 89) {
            champName = "Leona";
        } else if (champId == 127) {
            champName = "Lissandra";
        } else if (champId == 236) {
            champName = "Lucian";
        } else if (champId == 117) {
            champName = "Lulu";
        } else if (champId == 99) {
            champName = "Lux";
        } else if (champId == 54) {
            champName = "Malphite";
        } else if (champId == 90) {
            champName = "Malzahar";
        } else if (champId == 57) {
            champName = "Maokai";
        } else if (champId == 11) {
            champName = "MasterYi";
        } else if (champId == 21) {
            champName = "MissFortune";
        } else if (champId == 62) {
            champName = "MonkeyKing";
        } else if (champId == 82) {
            champName = "Mordekaiser";
        } else if (champId == 25) {
            champName = "Morgana";
        } else if (champId == 267) {
            champName = "Nami";
        } else if (champId == 75) {
            champName = "Nasus";
        } else if (champId == 111) {
            champName = "Nautilus";
        } else if (champId == 518) {
            champName = "Neeko";
        } else if (champId == 76) {
            champName = "Nidalee";
        } else if (champId == 56) {
            champName = "Nocturne";
        } else if (champId == 20) {
            champName = "Nunu";
        } else if (champId == 2) {
            champName = "Olaf";
        } else if (champId == 61) {
            champName = "Orianna";
        } else if (champId == 516) {
            champName = "Ornn";
        } else if (champId == 80) {
            champName = "Pantheon";
        } else if (champId == 78) {
            champName = "Poppy";
        } else if (champId == 555) {
            champName = "Pyke";
        } else if (champId == 246) {
            champName = "Qiyana";
        } else if (champId == 133) {
            champName = "Quinn";
        } else if (champId == 497) {
            champName = "Rakan";
        } else if (champId == 33) {
            champName = "Rammus";
        } else if (champId == 421) {
            champName = "RekSai";
        } else if (champId == 58) {
            champName = "Renekton";
        } else if (champId == 107) {
            champName = "Rengar";
        } else if (champId == 92) {
            champName = "Riven";
        } else if (champId == 68) {
            champName = "Rumble";
        } else if (champId == 13) {
            champName = "Ryze";
        } else if (champId == 113) {
            champName = "Sejuani";
        } else if (champId == 235) {
            champName = "Senna";
        } else if (champId == 875) {
            champName = "Sett";
        } else if (champId == 35) {
            champName = "Shaco";
        } else if (champId == 98) {
            champName = "Shen";
        } else if (champId == 102) {
            champName = "Shyvana";
        } else if (champId == 27) {
            champName = "Singed";
        } else if (champId == 14) {
            champName = "Sion";
        } else if (champId == 15) {
            champName = "Sivir";
        } else if (champId == 72) {
            champName = "Skarner";
        } else if (champId == 37) {
            champName = "Sona";
        } else if (champId == 16) {
            champName = "Soraka";
        } else if (champId == 50) {
            champName = "Swain";
        } else if (champId == 517) {
            champName = "Sylas";
        } else if (champId == 134) {
            champName = "Syndra";
        } else if (champId == 223) {
            champName = "TahmKench";
        } else if (champId == 163) {
            champName = "Taliyah";
        } else if (champId == 91) {
            champName = "Talon";
        } else if (champId == 44) {
            champName = "Taric";
        } else if (champId == 17) {
            champName = "Teemo";
        } else if (champId == 412) {
            champName = "Thresh";
        } else if (champId == 18) {
            champName = "Tristana";
        } else if (champId == 48) {
            champName = "Trundle";
        } else if (champId == 23) {
            champName = "Tryndamere";
        } else if (champId == 4) {
            champName = "TwistedFate";
        } else if (champId == 29) {
            champName = "Twitch";
        } else if (champId == 77) {
            champName = "Udyr";
        } else if (champId == 6) {
            champName = "Urgot";
        } else if (champId == 110) {
            champName = "Varus";
        } else if (champId == 67) {
            champName = "Vayne";
        } else if (champId == 45) {
            champName = "Veigar";
        } else if (champId == 161) {
            champName = "Velkoz";
        } else if (champId == 254) {
            champName = "Vi";
        } else if (champId == 112) {
            champName = "Viktor";
        } else if (champId == 8) {
            champName = "Vladimir";
        } else if (champId == 106) {
            champName = "Volibear";
        } else if (champId == 19) {
            champName = "Warwick";
        } else if (champId == 498) {
            champName = "Xayah";
        } else if (champId == 101) {
            champName = "Xerath";
        } else if (champId == 5) {
            champName = "XinZhao";
        } else if (champId == 157) {
            champName = "Yasuo";
        } else if (champId == 83) {
            champName = "Yorick";
        } else if (champId == 350) {
            champName = "Yuumi";
        } else if (champId == 154) {
            champName = "Zac";
        } else if (champId == 238) {
            champName = "Zed";
        } else if (champId == 115) {
            champName = "Ziggs";
        } else if (champId == 26) {
            champName = "Zilean";
        } else if (champId == 142) {
            champName = "Zoe";
        } else if (champId == 143) {
            champName = "Zyra";
        } else if (champId == 777) {
            champName = "Yone";
        } else if (champId == 145) {
            champName = "Kaisa";
        } else if (champId == 777) {
            champName = "Yone";
        } else{
            champName = "Garen";
        }

        return champName;
    }

    //스펠 데이터
    public String getSpell(String spellId) {

        if(spellId == null || spellId.equals("") || spellId.equals("null")){
            return "";
        }

        String spellName = null;

        if (spellId.equals("21")){
            spellName = "SummonerBarrier";
        } else if (spellId.equals("1")){
            spellName = "SummonerBoost";
        } else if (spellId.equals("14")){
            spellName = "SummonerDot";
        } else if (spellId.equals("3")){
            spellName = "SummonerExhaust";
        } else if (spellId.equals("4")){
            spellName = "SummonerFlash";
        } else if (spellId.equals("6")){
            spellName = "SummonerHaste";
        } else if (spellId.equals("7")){
            spellName = "SummonerHeal";
        } else if (spellId.equals("13")){
            spellName = "SummonerMana";
        } else if (spellId.equals("30")){
            spellName = "SummonerPoroRecall";
        } else if (spellId.equals("31")){
            spellName = "SummonerPoroThrow";
        } else if (spellId.equals("11")){
            spellName = "SummonerSmite";
        } else if (spellId.equals("39")){
            spellName = "SummonerSnowURFSnowball_Mark";
        } else if (spellId.equals("32")){
            spellName = "SummonerSnowball";
        } else if (spellId.equals("12")){
            spellName = "SummonerTeleport";
        }

        return spellName;
    }



}
