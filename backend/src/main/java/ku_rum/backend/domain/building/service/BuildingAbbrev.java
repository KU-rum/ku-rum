package ku_rum.backend.domain.building.service;

public enum BuildingAbbrev {

  경영("경영관"),
  상허관("상허연구관"),
  사("교육과학관"),
  예("예술문화관"),
  언어원("언어교육관"),
  종강("법학관"),
  의("의생명과학연구관"),
  생("생명과학관"),
  동("동물생명과학관"),
  산학("산학협동관"),
  수("수의학관"),
  새("새천년관"),
  건("건축관"),
  부("해봉부동산학관"),
  문("인문학관"),
  공("공학관"),
  신공("신공학관"),
  이("과학관"),
  창("창의관");
  
  final private String originalName;
  public String getOriginalName(){
    return originalName;
  }
  private BuildingAbbrev(String originalName){
    this.originalName = originalName;
  }
}
