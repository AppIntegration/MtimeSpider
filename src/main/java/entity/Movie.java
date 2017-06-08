package entity;

import java.util.List;

/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/6/7
 */
public class Movie {

    public String chiName;
    public String engName;

    public String introduction;

    public int year;
    public double score;
    public int num;

    public List<String> otherNames;

    public Movie() {
    }

    public Movie(String chiName, String engName, String introduction, int year, double score, int num, List<String> otherNames) {
        this.chiName = chiName;
        this.engName = engName;
        this.introduction = introduction;
        this.year = year;
        this.score = score;
        this.num = num;
        this.otherNames = otherNames;
    }
}
