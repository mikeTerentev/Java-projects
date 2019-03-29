package markup;

import java.util.List;

public class Paragraph implements ChangeStruct {
    private List<ChangeStruct> components;

    public Paragraph(List<ChangeStruct> components) {
        this.components = components;
    }

    public void toMarkdown(StringBuilder sb) {
        components.forEach(el -> el.toMarkdown(sb));
    }

    public void toHtml(StringBuilder sb) {
        components.forEach(el -> el.toHtml(sb));
    }
}
