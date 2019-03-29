package md2html;

import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;

public class toHtml {


    toHtml() {
        a1 = "in.txt";
        a2 = "out.txt";
    }

    private void endOfStaff(StringBuilder formated) {
        if (titleSum == 0) {
            if (formated.length() != 0)
                formated.append("</p>\n");
        } else {
            formated.append("</h").append(Integer.toString(titleSum)).append(">\n");
        }
    }

    private void startOfStaff(StringBuilder formated) {
        StringBuilder staffElements = new StringBuilder(line);
        if (!fisrtStaffLocker) {
            i = 0;
            while (staffElements.charAt(i) == '#')
                i++;
            if (i > 0 && staffElements.charAt(i) == ' ') {
                titleSum = i;
                formated.append("<h").append(Integer.toString(titleSum)).append(">");
                formated.append(staffElements.subSequence(++i, staffElements.length()));
            } else {
                formated.append("<p>").append(staffElements);
            }
        } else {
            formated.append("\n").append(staffElements);
        }
        fisrtStaffLocker = true;
    }


    private void mainStaff(StringBuilder formated) {
        createLibruary();
        int i = 0, l = formated.length();
        String staff;
        String singleStaffPtr = "`";
        while (i < l) {
            //screen
            if ((formated.charAt(i) == '\\') && ((i < l - 1) && (formated.charAt(i + 1) == '*' || formated.charAt(i + 1) == '_'))) {
                formated.replace(i, i + 1, "");
                i++;
            } else {
                //staff
                if ((i < l - 1) && (libuary.get(formated.subSequence(i, i + 2).toString()) != null)) {
                    staff = formated.subSequence(i, i + 2).toString();
                } else if (libuary.get(formated.subSequence(i, i + 1).toString()) == null) {
                    i++;
                    continue;
                } else {
                    staff = formated.subSequence(i, i + 1).toString();
                }
                if (libuary.get(staff).getKey() == -3) {
                    libuary.put(staff, new Pair<>(i, libuary.get(staff).getValue()));
                } else {
                    if (staff.equals(singleStaffPtr)) switch (formated.charAt(i - 1)) {
                        case '<':
                            formated.replace(i - 1, i, "&lt;");
                            i += 3;
                            break;
                        case '>':
                            formated.replace(i - 1, i, "&gt;");
                            i += 3;
                            break;
                        case '&':
                            formated.replace(i - 1, i, "&amp;");
                            i += 4;
                            break;
                    }
                    int endPtr = i + staff.length();
                    formated.replace(i, endPtr,
                            "</" + libuary.get(staff).getValue() + ">");
                    endPtr = libuary.get(staff).getKey() + staff.length();
                    formated.replace(libuary.get(staff).getKey(), endPtr,
                            "<" + libuary.get(staff).getValue() + ">");
                    //refresh
                    libuary.put(staff, new Pair<>(-3, libuary.get(staff).getValue()));
                }
                i += staff.length();
            }
            l = formated.length();
        }
    }

    public void solve() {
        try (BufferedReader INPUT = new BufferedReader(new InputStreamReader(new FileInputStream(a1),"UTF-8"))) {
            try (BufferedWriter OUTPUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(a2),"UTF-8"))) {
                StringBuilder liner = new StringBuilder();
                boolean flag = true;
                while (flag) {
                    line = INPUT.readLine();
                    if (line != null) flag = true;
                    else {
                        flag = false;
                    }

                    if (flag && (line.length() > 0)) {
                        startOfStaff(liner);
                    } else {
                        mainStaff(liner);
                        endOfStaff(liner);
                        OUTPUT.write(liner.toString());
                        fisrtStaffLocker = false;
                        titleSum = 0;
                        liner = new StringBuilder();
                    }
                }

            } catch (IOException e) {
                System.out.println("OUTPUT FAILED//OTCHEN'_ZSHAL'");
            }
        } catch (IOException e) {
            System.out.println("INPUT FAILED//OTCHEN'_ZSHAL'");
        }
    }

    private String a1, a2, line;
    private Boolean fisrtStaffLocker = false;
    private int titleSum = 0, i;
    private HashMap<String, Pair<Integer, String>> libuary = new HashMap<>();

    private void addStaff(String k, String v) {
        libuary.put(k, new Pair<>(-3, v));
    }

    private void createLibruary() {
        addStaff("*", "em");
        addStaff("**", "strong");
        addStaff("_", "em");
        addStaff("--", "s");
        addStaff("`", "code");
        addStaff("++", "u");
        addStaff("__", "strong");
        addStaff("~", "mark");
    }
}

