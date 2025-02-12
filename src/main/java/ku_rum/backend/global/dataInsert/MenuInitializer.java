package ku_rum.backend.global.dataInsert;

import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.menu.domain.Menu;

import java.util.ArrayList;

public class MenuInitializer {
  public static ArrayList<Menu> initializer(ArrayList<Category> categories) {
    ArrayList<Menu> menus = new ArrayList<>();;
    Category studentCafeteriaCategory = categories.get(4);
    String defaultDescription = "NONE";

    //학생식당 메뉴
    menus.add(Menu.of("마라탕", 4000L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("쌀국수", 4500L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("순대", 5000L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("소금삼겹", 4500L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("삼겹 양념구이", 4500L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("참치마요", 3000L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("황소라면", 3000L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("건대 떡볶이", 4300L, defaultDescription, studentCafeteriaCategory));
    menus.add(Menu.of("카레라이스", 4600L, defaultDescription, studentCafeteriaCategory));

    return menus;
  }
}