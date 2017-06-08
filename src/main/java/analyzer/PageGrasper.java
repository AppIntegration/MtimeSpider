package analyzer;

import dao.MovieDAO;
import entity.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/6/7
 */
public class PageGrasper {

    private MovieRequest request = new MovieRequest();
    private MovieDAO movieDAO = new MovieDAO();

    public void readHtml(){

        for(int index = 196; index <= 201; index++){
            System.out.println("page " + index + " is processing" );

            Document doc = null;
            doc = Jsoup.parse(request.getJSON(index));
            Elements list = doc.select("ul > li");

            for(int i = 0; i < list.size(); i++){
                Element info = list.get(i).select("div.table > div.t_r > div").get(1);

                String chiName = info.select("h3 > a").get(0).text();
                String url = info.select("h3 > a").get(0).attr("href") + "plots.html";

                String engName = info.select("p > a").get(0).text().trim();
                List<String> otherNames = new ArrayList<>();
                Elements otherEle = info.select("p[class=mt15 c_666]");
                otherEle.forEach( ele ->
                {
                    String[] spl = ele.text().split("/");
                    for(String s : spl){
                        otherNames.add(s);
                    }
                });

                Elements numEle = info.select("p[class=c_666 mt6]");
                int num = 0;
                double score = 0;
                if(numEle.size()!=0){
                    String preNum = numEle.get(0).text();
                    num = Integer.parseInt(preNum.substring(0, preNum.indexOf("人")));
                    String scoreString = info.select("div.clearfix").get(0).text();
                    score = scoreString.equals("")?0:Double.parseDouble(scoreString);
                }


                Movie movie = new Movie(chiName, engName, getIntro(request.sendGet(url)), 2016, score, num, otherNames);

                movieDAO.insertMovie(movie);
            }

        }

    }

    private String getIntro(String html){
        Document plotDoc = Jsoup.parse(html);
        Elements tmp =  plotDoc
                .select("div#paragraphRegion > div.plots_out > div.plots_box");
        if(tmp.size()==0){
            return "";
        }


        return tmp.get(0).text().trim();
    }

    public static void main(String[] args) {
        new PageGrasper().readHtml();
    }
}

