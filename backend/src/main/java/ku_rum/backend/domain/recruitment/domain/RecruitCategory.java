package ku_rum.backend.domain.recruitment.domain;

import lombok.Getter;

@Getter
public enum RecruitCategory {
    SARAMIN("https://www.saramin.co.kr/zf_user/jobs/hot100",
            "#content > div.recruit_hot_wrap > div.list_recruiting.list_hot_type > div.list_body > div > div",
            "div.box_item > div.col.notification_info > div.job_tit > a.str_tit > span",
            "div.box_item > div.col.notification_info > div.job_tit > a.str_tit",
            "div.box_item > div.col.recruit_info > ul > li:nth-child(1) > p",
            "div.box_item > div.col.recruit_info > ul > li:nth-child(2) > p",
            "div.box_item > div.col.company_nm > .str_tit"),
    WANTED("https://www.wanted.co.kr/wdlist/all?country=kr&job_sort=job.popularity_order&years=-1&locations=all",
            "#__next > div.JobList_JobList__Qj_5c > div.JobList_JobList__contentWrapper__3wwft > ul > li",
            "div > a > div:nth-child(2) > span.JobCard_JobCard__body__position__CyaGY.wds-1mh3acf",
            "div > a",
            "div > a > div:nth-child(2) > span.CompanyNameWithLocationPeriod_CompanyNameWithLocationPeriod__location__FHNmN.wds-1m3gvmz",
            "div > a > div:nth-child(2) > span.CompanyNameWithLocationPeriod_CompanyNameWithLocationPeriod__location__FHNmN.wds-1m3gvmz",
            "div > a > div:nth-child(2) > span.CompanyNameWithLocationPeriod_CompanyNameWithLocationPeriod__company__j_pad.wds-1m3gvmz");

    private final String url;
    private final String selector;
    private final String titleSelector;
    private final String urlSelector;
    private final String locationSelector;
    private final String careerSelector;
    private final String companySelector;

    RecruitCategory(String url, String selector, String titleSelector, String urlSelector, String locationSelector, String careerSelector,  String companySelector) {
        this.url = url;
        this.selector = selector;
        this.titleSelector = titleSelector;
        this.urlSelector = urlSelector;
        this.locationSelector = locationSelector;
        this.careerSelector = careerSelector;
        this.companySelector = companySelector;
    }

    public boolean isEqual(RecruitCategory recruitCategory) {
        return this.name().equals(recruitCategory.name());
    }
}
