package service;

import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

import com.google.gson.Gson;
import util.RedisUtil;
public class PageService {

    public Page createPage(String title, User user) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Page page = new Page();
        page.setTitle(title);
        page.setCreatedBy(user);

        session.persist(page);

        PageVersion v = new PageVersion();
        v.setPage(page);
        v.setVersionNumber(1);
        v.setTitleSnapshot(title);
        v.setCreatedBy(user);

        session.persist(v);

        page.setCurrentVersion(v);

        tx.commit();
        session.close();

        return page;
    }

    public PageVersion updatePage(Long pageId, String title, List<Block> blocks, User user) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Page page = session.get(Page.class, pageId);

        PageVersion last = session.createQuery(
                        "FROM PageVersion WHERE page = :p ORDER BY versionNumber DESC",
                        PageVersion.class)
                .setParameter("p", page)
                .setMaxResults(1)
                .uniqueResult();

        PageVersion v = new PageVersion();
        v.setPage(page);
        v.setVersionNumber(last.getVersionNumber() + 1);
        v.setTitleSnapshot(title);
        v.setCreatedBy(user);

        session.persist(v);

        if (blocks != null) {
            for (Block b : blocks) {
                b.setId(null);
                b.setPageVersion(v);
                session.persist(b);
            }
        }
        SearchService searchService = new SearchService();
        searchService.indexPage(page, blocks);

//        RedisUtil.set("page:" + pageId, null);
        RedisUtil.delete("page:" + pageId);
        tx.commit();
        session.close();

        return v;
    }

//        page.setCurrentVersion(v);
//
//        tx.commit();
//        session.close();
//
//        return v;
//    public PageVersion getPage(Long pageId) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//
//        Page page = session.get(Page.class, pageId);
//        PageVersion v = page.getCurrentVersion();
//
//        session.close();
//        return v;
//    }

    private final Gson gson = new Gson();

    public PageVersion getPage(Long pageId) {

        String cacheKey = "page:" + pageId;

        // 1. пробуем взять из Redis
        String cached = RedisUtil.get(cacheKey);
        if (cached != null) {
            System.out.println("FROM CACHE");
            return gson.fromJson(cached, PageVersion.class);
        }

        // 2. если нет — идём в БД
        Session session = HibernateUtil.getSessionFactory().openSession();

        Page page = session.get(Page.class, pageId);
        PageVersion version = page.getCurrentVersion();

        session.close();

        // 3. кладём в кэш
        RedisUtil.set(cacheKey, gson.toJson(version));

        return version;
    }
}