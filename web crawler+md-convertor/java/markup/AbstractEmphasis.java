package markup;

import java.util.List;

public abstract class AbstractEmphasis implements ChangeStruct {
    private List<ChangeStruct> components;
    private final String surrounding;
    private final String htmlOpeningTag;
    private final String htmlClosingTag;

    public AbstractEmphasis(String surrounding, String htmlOpeningTag, String htmlClosingTag, List<ChangeStruct> components) {
        this.surrounding = surrounding;
        this.htmlOpeningTag = htmlOpeningTag;
        this.htmlClosingTag = htmlClosingTag;
        this.components = components;
    }

    public void toHtml(StringBuilder sb) {
        sb.append(htmlOpeningTag);
        components.forEach(el -> el.toHtml(sb));
        sb.append(htmlClosingTag);
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(surrounding);
        components.forEach(el -> el.toMarkdown(sb));
        sb.append(surrounding);
    }
}