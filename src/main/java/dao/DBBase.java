package dao;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuminchen
 * @version V1.0
 * @date 2017/6/7
 */
public class DBBase {

    private  MongoCollection<Document> collection;

    /**
     * 链接数据库
     *
     * @param databaseName 数据库名称
     * @param collectionName 集合名称
     * @param hostName 主机名
     * @param port 端口号
     */
    public  void connect(String databaseName, String collectionName,
                         String hostName, int port) {
        MongoClient client = new MongoClient(hostName, port);
        MongoDatabase db = client.getDatabase(databaseName);
        collection = db.getCollection(collectionName);
    }

    /**
     *
     * @param document 文档
     */
    public  void insert(Document document) {
        collection.insertOne(document);
    }

    /**
     *
     * @return 所有文档集合
     */
    public  List<Document> findAll() {
        List<Document> results = new ArrayList<Document>();
        FindIterable<Document> iterables = collection.find();
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }

        return results;
    }

    /**
     *
     * @param filter
     * @return 返回集合列表
     */
    public  List<Document> findBy(Bson filter) {
        List<Document> results = new ArrayList<Document>();
        FindIterable<Document> iterables = collection.find(filter);
        MongoCursor<Document> cursor = iterables.iterator();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }

        return results;
    }

    /**
     * 更新查询到的第一个
     *
     * @param filter
     * @param update
     * @return 更新结果
     */
    public  UpdateResult updateOne(Bson filter, Bson update) {
        UpdateResult result = collection.updateOne(filter, update);

        return result;
    }

    /**
     * 更新查询到的所有的文档
     *
     * @param filter
     * @param update
     * @return 更新结果
     */
    public  UpdateResult updateMany(Bson filter, Bson update) {
        UpdateResult result = collection.updateMany(filter, update);

        return result;
    }

    /**
     * 更新一个文档, 结果是replacement是新文档，老文档完全被替换
     *
     * @param filter
     * @param replacement
     */
    public  void replace(Bson filter, Document replacement) {
        collection.replaceOne(filter, replacement);
    }

    /**
     * 根据条件删除一个文档
     *
     * @param filter
     */
    public  void deleteOne(Bson filter) {
        collection.deleteOne(filter);
    }

    /**
     * 根据条件删除多个文档
     *
     * @param filter
     */
    public  void deleteMany(Bson filter) {
        collection.deleteMany(filter);
    }
}