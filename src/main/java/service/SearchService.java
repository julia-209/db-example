package service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import entity.Block;
import entity.Page;
import util.ElasticUtil;

import java.io.IOException;
import java.util.List;
public class SearchService {
    private final ElasticsearchClient client = ElasticUtil.getClient();

//    public void indexPage(Page page, List<Block> blocks) {
//
//        StringBuilder content = new StringBuilder();
//
//        if (blocks != null) {
//            for (Block b : blocks) {
//                if (b.getContentJson() != null) {
//                    content.append(b.getContentJson()).append(" ");
//                }
//            }
//        }
//        if (blocks != null) {
//            for (Block b : blocks) {
//                if (blocks != null) {
//                        if (b.getContentJson() != null) {
//                            content.append(b.getContentJson()).append(" ");
//                        }
//                    }
//                }
//                content.append(b.getContentJson()).append(" ");
//            }

//        try {
//            client.index(i -> i
//                    .index("pages")
//                    .id(page.getId().toString())
//                    .document(new search.PageDocument() {{
//                        id = page.getId();
//                        title = page.getTitle();
//                        content = content.toString();
//                    }})
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void indexPage(Page page, List<Block> blocks) {

        StringBuilder content = new StringBuilder();

        if (blocks != null) {
            for (Block b : blocks) {
                if (b.getContentJson() != null) {
                    content.append(b.getContentJson()).append(" ");
                }
            }
        }

        try {
            // 👉 СОЗДАЁМ ДОКУМЕНТ ЗДЕСЬ
            search.PageDocument doc = new search.PageDocument();
            doc.id = page.getId();
            doc.title = page.getTitle();
            doc.content = content.toString();

            // 👉 ИНДЕКСАЦИЯ
            client.index(i -> i
                    .index("pages")
                    .id(page.getId().toString())
                    .document(doc)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
