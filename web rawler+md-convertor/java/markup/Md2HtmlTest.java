//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package markup;

import base.MainFilesChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Md2HtmlTest {
    protected static Map<String, String> TAGS = new HashMap();
    protected final Md2HtmlChecker checker = new Md2HtmlChecker("md2html.Md2Html");

    public Md2HtmlTest() {
    }

    protected void test() {
        this.test("# Заголовок первого уровня\n\n", "<h1>Заголовок первого уровня</h1>");
        this.test("## Второго\n\n", "<h2>Второго</h2>");
        this.test("### Третьего ## уровня\n\n", "<h3>Третьего ## уровня</h3>");
        this.test("#### Четвертого\n# Все еще четвертого\n\n", "<h4>Четвертого\n# Все еще четвертого</h4>");
        this.test("Этот абзац текста,\nсодержит две строки.", "<p>Этот абзац текста,\nсодержит две строки.</p>");
        this.test("    # Может показаться, что это заголовок.\nНо нет, это абзац начинающийся с `#`.\n\n", "<p>    # Может показаться, что это заголовок.\nНо нет, это абзац начинающийся с <code>#</code>.</p>");
        this.test("#И это не заголовок.\n\n", "<p>#И это не заголовок.</p>");
        this.test("###### Заголовки могут быть многострочными\n(и с пропуском заголовков предыдущих уровней)\n\n", "<h6>Заголовки могут быть многострочными\n(и с пропуском заголовков предыдущих уровней)</h6>");
        this.test("Мы все любим *выделять* текст _разными_ способами.\n**Сильное выделение**, используется гораздо реже,\nно __почему бы и нет__?\nНемного --зачеркивания-- еще ни кому не вредило.\nКод представляется элементом `code`.\n\n", "<p>Мы все любим <em>выделять</em> текст <em>разными</em> способами.\n<strong>Сильное выделение</strong>, используется гораздо реже,\nно <strong>почему бы и нет</strong>?\nНемного <s>зачеркивания</s> еще ни кому не вредило.\nКод представляется элементом <code>code</code>.</p>");
        this.test("Обратите внимание, как экранируются специальные\nHTML-символы, такие как `<`, `>` и `&`.\n\n", "<p>Обратите внимание, как экранируются специальные\nHTML-символы, такие как <code>&lt;</code>, <code>&gt;</code> и <code>&amp;</code>.</p>");
        this.test("Знаете ли вы, что в Markdown, одиночные * и _\nне означают выделение?\nОни так же могут быть заэкранированы\nпри помощи обратного слэша: \\*.", "<p>Знаете ли вы, что в Markdown, одиночные * и _\nне означают выделение?\nОни так же могут быть заэкранированы\nпри помощи обратного слэша: *.</p>");
        this.test("\n\n\nЛишние пустые строки должны игнорироваться.\n\n\n\n", "<p>Лишние пустые строки должны игнорироваться.</p>");
        this.test("Любите ли вы *вложеные __выделения__* так,\nкак __--люблю--__ их я?", "<p>Любите ли вы <em>вложеные <strong>выделения</strong></em> так,\nкак <strong><s>люблю</s></strong> их я?</p>");
        this.test("# Заголовок первого уровня\n\n## Второго\n\n### Третьего ## уровня\n\n#### Четвертого\n# Все еще четвертого\n\nЭтот абзац текста,\nсодержит две строки.\n\n    # Может показаться, что это заголовок.\nНо нет, это абзац начинающийся с `#`.\n\n#И это не заголовок.\n\n###### Заголовки могут быть многострочными\n(и с пропуском заголовков предыдущих уровней)\n\nМы все любим *выделять* текст _разными_ способами.\n**Сильное выделение**, используется гораздо реже,\nно __почему бы и нет__?\nНемного --зачеркивания-- еще ни кому не вредило.\nКод представляется элементом `code`.\n\nОбратите внимание, как экранируются специальные\nHTML-символы, такие как `<`, `>` и `&`.\n\nЗнаете ли вы, что в Markdown, одиночные * и _\nне означают выделение?\nОни так же могут быть заэкранированы\nпри помощи обратного слэша: \\*.\n\n\n\nЛишние пустые строки должны игнорироваться.\n\nЛюбите ли вы *вложеные __выделения__* так,\nкак __--люблю--__ их я?", "<h1>Заголовок первого уровня</h1>\n<h2>Второго</h2>\n<h3>Третьего ## уровня</h3>\n<h4>Четвертого\n# Все еще четвертого</h4>\n<p>Этот абзац текста,\nсодержит две строки.</p>\n<p>    # Может показаться, что это заголовок.\nНо нет, это абзац начинающийся с <code>#</code>.</p>\n<p>#И это не заголовок.</p>\n<h6>Заголовки могут быть многострочными\n(и с пропуском заголовков предыдущих уровней)</h6>\n<p>Мы все любим <em>выделять</em> текст <em>разными</em> способами.\n<strong>Сильное выделение</strong>, используется гораздо реже,\nно <strong>почему бы и нет</strong>?\nНемного <s>зачеркивания</s> еще ни кому не вредило.\nКод представляется элементом <code>code</code>.</p>\n<p>Обратите внимание, как экранируются специальные\nHTML-символы, такие как <code>&lt;</code>, <code>&gt;</code> и <code>&amp;</code>.</p>\n<p>Знаете ли вы, что в Markdown, одиночные * и _\nне означают выделение?\nОни так же могут быть заэкранированы\nпри помощи обратного слэша: *.</p>\n<p>Лишние пустые строки должны игнорироваться.</p>\n<p>Любите ли вы <em>вложеные <strong>выделения</strong></em> так,\nкак <strong><s>люблю</s></strong> их я?</p>\n");
        this.test("# Без перевода строки в конце", "<h1>Без перевода строки в конце</h1>");
        this.test("# Один перевод строки в конце\n", "<h1>Один перевод строки в конце</h1>");
        this.test("# Два перевода строки в конце\n\n", "<h1>Два перевода строки в конце</h1>");
        this.test("Выделение может *начинаться на одной строке,\n а заканчиваться* на другой", "<p>Выделение может <em>начинаться на одной строке,\n а заканчиваться</em> на другой</p>");
        this.test("# *Выделение* и `код` в заголовках", "<h1><em>Выделение</em> и <code>код</code> в заголовках</h1>");
        Iterator var1 = TAGS.keySet().iterator();

        while (var1.hasNext()) {
            String var2 = (String) var1.next();
            this.randomTest(3, 10, var2);
        }

        this.randomTest(100, 1000, "_", "**", "`", "--");
        this.randomTest(100, 1000, "*", "__", "`", "--");
    }

    protected void test(String var1, String var2) {
        this.checker.test(var1, var2);
    }

    protected void randomTest(int var1, int var2, String... var3) {
        StringBuilder var4 = new StringBuilder();
        StringBuilder var5 = new StringBuilder();
        this.emptyLines(var4);
        ArrayList var6 = new ArrayList(Arrays.asList(var3));

        for (int var7 = 0; var7 < var1; ++var7) {
            StringBuilder var8 = new StringBuilder();
            this.paragraph(var2, var8, var5, var6);
            var4.append(var8);
            this.emptyLines(var4);
        }

        this.test(var4.toString(), var5.toString());
    }

    private void paragraph(int var1, StringBuilder var2, StringBuilder var3, List<String> var4) {
        int var5 = this.checker.randomInt(0, 6);
        String var6 = var5 == 0 ? "p" : "h" + var5;
        if (var5 > 0) {
            var2.append((new String(new char[var5])).replace('\u0000', '#')).append(" ");
        }

        this.open(var3, var6);

        while (var2.length() < var1) {
            this.generate(var4, var2, var3);
            String var7 = this.checker.randomString("abcdefghijklmnopqrstuvwxyz");
            var2.append(var7).append("\n");
            var3.append(var7).append("\n");
        }

        var3.setLength(var3.length() - 1);
        this.close(var3, var6);
        var3.append("\n");
        var2.append("\n");
    }

    private void randomSpace(StringBuilder var1, StringBuilder var2) {
        if (this.checker.random.nextBoolean()) {
            String var3 = this.checker.random.nextBoolean() ? " " : "\n";
            var1.append(var3);
            var2.append(var3);
        }

    }

    private void generate(List<String> var1, StringBuilder var2, StringBuilder var3) {
        this.word(var2, var3);
        if (!var1.isEmpty()) {
            String var4 = (String) this.checker.randomItem(var1);
            String var5 = (String) TAGS.get(var4);
            var1.remove(var4);
            this.randomSpace(var2, var3);
            var2.append(var4);
            this.open(var3, var5);
            this.word(var2, var3);
            this.generate(var1, var2, var3);
            this.word(var2, var3);
            var2.append(var4);
            this.close(var3, var5);
            this.randomSpace(var2, var3);
            var1.add(var4);
        }
    }

    private void word(StringBuilder var1, StringBuilder var2) {
        String var3 = this.checker.randomString("abcdefghijklmnopqrstuvwxyz");
        var1.append(var3);
        var2.append(var3);
    }

    private void open(StringBuilder var1, String var2) {
        var1.append("<").append(var2).append(">");
    }

    private void close(StringBuilder var1, String var2) {
        var1.append("</").append(var2).append(">");
    }

    private void emptyLines(StringBuilder var1) {
        while (this.checker.random.nextBoolean()) {
            var1.append('\n');
        }

    }

    protected void run() {
        this.test();
        this.checker.printStatus();
    }

    public static void main(String... var0) {
        (new Md2HtmlTest()).run();
    }

    static {
        TAGS.put("*", "em");
        TAGS.put("**", "strong");
        TAGS.put("_", "em");
        TAGS.put("__", "strong");
        TAGS.put("--", "s");
        TAGS.put("`", "code");
    }

    static class Md2HtmlChecker extends MainFilesChecker {
        public Md2HtmlChecker(String var1) {
            super(var1);
        }

        private void test(String var1, String var2) {
            List var3 = this.runFiles(Arrays.asList(var1.split("\n")));
            this.checkEquals(Arrays.asList(var2.split("\n")), var3);
        }
    }
}
