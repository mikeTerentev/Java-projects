package markup;

import java.util.List;

public class Emphasis extends AbstractEmphasis {
    public Emphasis(List<ChangeStruct> components) {
        super("*", "<em>", "</em>", components);
    }
}
