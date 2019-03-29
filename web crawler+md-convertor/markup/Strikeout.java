package markup;


import java.util.List;

public class Strikeout extends AbstractEmphasis {
    public Strikeout(List<ChangeStruct> components) {
        super("~", "<s>", "</s>", components);
    }
}