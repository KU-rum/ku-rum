package ku_rum.backend.domain.recruitment.domain;

import lombok.Getter;

@Getter
public enum RecruitCategory {
    SARAMIN("https://www.saramin.co.kr/zf_user/jobs/hot100",
            "#content > div.recruit_hot_wrap > div.list_recruiting.list_hot_type > div.list_body > div > div"),
    WANTED("https://www.wanted.co.kr/wdlist/all?country=kr&job_sort=job.popularity_order&years=-1&locations=all",
            "#__next > div.JobList_JobList__Qj_5c > div.JobList_JobList__contentWrapper__3wwft > ul > li");

    private final String url;
    private final String selector;

    RecruitCategory(String url, String selector) {
        this.url = url;
        this.selector = selector;
    }
}
