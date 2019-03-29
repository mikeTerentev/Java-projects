package crawler;

import java.util.*;

class htmlBlock {
    private Tags type;
    public HashMap<String, tagAtr> tagsAtrCache = new HashMap<>();
    private int curPtr = 0;
    private StringBuilder curKey = new StringBuilder(), curValue = new StringBuilder();

    private boolean isPtrCorrect(String attributes) {
        return curPtr < attributes.length();
    }

    private void refresh() {
        curKey = new StringBuilder();
        curValue = new StringBuilder();
    }

    private List<tagAtr> parseAttributes(String code) {
        List<tagAtr> parsedList = new ArrayList<>();
        refresh();
        while (isPtrCorrect(code)) {
            skipWhitespace(code);
            readName(code, curKey);
            skipWhitespace(code);

            if (isPtrCorrect(code) && code.charAt(curPtr) != '=') {
                curKey = new StringBuilder();
                continue;
            }

            if (!isPtrCorrect(code)) {
                break;
            } else {
                curPtr++;
            }
            skipWhitespace(code);//
            readValue(code, curValue);
            parsedList.add(new tagAtr(curKey.toString(), curValue.toString()));
            refresh();
        }
        return parsedList;
    }


    htmlBlock(String code) {
        int pos = code.indexOf(' ');
        if (pos == -1) pos = code.length();
        if (pos != code.length()) code = code.substring(pos);

        for (tagAtr curAtr : parseAttributes(code)) {
            tagsAtrCache.put(curAtr.get(), curAtr);
        }
    }

    tagAtr getTagAtr(String name) {
        return tagsAtrCache.get(name);
    }


    private void skipWhitespace(String attributes) {
        while (isPtrCorrect(attributes)
                && Character.isWhitespace(attributes.charAt(curPtr)))
            curPtr++;

    }

    private void readName(String attributes, StringBuilder curKey) {
        while (isPtrCorrect(attributes)
                && !Character.isWhitespace(attributes.charAt(curPtr))
                && attributes.charAt(curPtr) != '=')
            curKey.append(attributes.charAt(curPtr++));

    }

    private void readValue(String attributes, StringBuilder curValue) {
        curPtr++;

        while (isPtrCorrect(attributes)
                && attributes.charAt(curPtr) != '"') {
            curValue.append(attributes.charAt(curPtr));
            curPtr++;
        }
        curPtr++;
    }


}
