package com.lswd.youpin.lsy;

import com.lswd.youpin.response.LsyResponse;

public interface LsyService {

    LsyResponse getHomeInfo( String url, String machineNo);

    LsyResponse getLawAndRegulationMainInfo(String url, String machineNo);

    LsyResponse getLeaderVisitList(String url, String machineNo);

    LsyResponse getLeaderVisitInfo(String url, String machineNo,Integer id);

    LsyResponse getLawList(String url, String machineNo);

    LsyResponse getLawInfo(String url, String machineNo,Integer id);

    LsyResponse getUploadTypeList(String url);

}
