//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package md2html;

public class Md2HtmlTest extends markup.Md2HtmlTest {
    public Md2HtmlTest() {
    }

    protected void test() {
        this.test("++подчеркивание++", "<p><u>подчеркивание</u></p>");
        super.test();
        //this.randomTest(100, 1000, new String[]{"_", "**", "`", "--", "++"});
        //this.randomTest(100, 1000, new String[]{"*", "__", "`", "--", "++"});
        //this.randomTest(100, 100000, new String[]{"*", "__", "`", "--", "++"});
    }

    public static void main(String... var0) {
        (new Md2HtmlTest()).run();
    }

    static {
        TAGS.put("++", "u");
    }
}
