package dao;

import entity.Movie;
import org.bson.Document;

import java.util.List;


/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/6/7
 */
public class MovieDAO extends DBBase {


    public MovieDAO() {
        connect(DBUtility.databaseName,DBUtility.movie,DBUtility.hostName,DBUtility.port);
    }

    public void insertMovie(Movie movie) {
        Document movieDoc = new Document();
        movieDoc.append("chiName", movie.chiName)
                .append("engName", movie.engName)
                .append("intro", movie.introduction)
                .append("year", movie.year)
                .append("score", movie.score)
                .append("num", movie.num);

        List<String> names = movie.otherNames;
        for ( int i = 0; i< names.size(); i++){
            Document name = new Document();
            name.append("name", names.get(i));
            movieDoc.append(String.valueOf(i + 1), name);
        }
        insert(movieDoc);
    }
}
