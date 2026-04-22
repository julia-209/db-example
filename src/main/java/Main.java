import entity.User;
import service.PageService;

public class Main {
    public static void main(String[] args) {

        PageService service = new PageService();

        User user = new User();
        user.setId(1L); // должен быть в БД

        var page = service.createPage("Test", user);
        System.out.println("Page: " + page.getId());

        var v = service.updatePage(page.getId(), "Updated", null, user);
        System.out.println("Version: " + v.getVersionNumber());
        System.exit(0);
    }
}