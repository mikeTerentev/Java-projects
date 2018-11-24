package markup;


import java.util.List;

public class Strong extends AbstractEmphasis {
    public Strong(List<ChangeStruct> components) {
        super("__", "<strong>", "</strong>", components);
    }
}