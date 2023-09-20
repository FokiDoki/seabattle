package org.seabattle.view.mapper;

import org.seabattle.FIeld.Field;

import java.awt.*;

public class FieldToStringMapper implements Mapper<Field>{

    static final String EMPTY_CELL = "░";
    static final String SHIP_CELL = "O";
    static final String HIT_CELL = "X";
    static final String MISS_CELL = "M";

    static final String HORIZONTAL_BORDER = "═";

    static final String VERTICAL_BORDER = "║";

    static final String TOP_LEFT_CORNER = "╔";
    static final String TOP_RIGHT_CORNER = "╗";
    static final String BOTTOM_LEFT_CORNER = "╚";
    static final String BOTTOM_RIGHT_CORNER = "╝";


    public String map(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        buildNumericHeader(field, stringBuilder);
        buildUpperBorder(field, stringBuilder);
        buildFieldBody(field, stringBuilder);
        buildDownBorder(field, stringBuilder);
        return stringBuilder.toString();
    }

    private void buildFieldBody(Field field, StringBuilder stringBuilder){
        for (int y = 0; y < field.getHeight(); y++) {
            stringBuilder.append("%d%s".formatted(y+1, spaceTabByNumber(y+1, 4)));
            stringBuilder.append(VERTICAL_BORDER);
            for (int x = 0; x < field.getWidth(); x++) {
                for (int i = 0; i < 2; i++) {
                    getCellStatusByCords(field, stringBuilder, x, y);
                }
            }
            stringBuilder.append(VERTICAL_BORDER);
            stringBuilder.append("\n");
        }
    }

    protected void getCellStatusByCords(Field field, StringBuilder stringBuilder, int x, int y) {
        switch (field.getCellStatus(new Point(x, y))) {
            case EMPTY -> stringBuilder.append(EMPTY_CELL);
            case SHIP -> stringBuilder.append(SHIP_CELL);
            case HIT -> stringBuilder.append(HIT_CELL);
            case MISS -> stringBuilder.append(MISS_CELL);
        }
    }

    private String spaceTabByNumber(int number, int tabSize){
        int spacesCount = Math.max(0, tabSize-String.valueOf(number).length());
        return " ".repeat(spacesCount);
    }

    private void buildNumericHeader(Field field, StringBuilder stringBuilder){
        stringBuilder.append("    ");
        stringBuilder.append(VERTICAL_BORDER);
        for (int i = 1; i <= field.getWidth(); i++) {
            stringBuilder.append(i);
            stringBuilder.append(spaceTabByNumber(i, 2));
        }
        stringBuilder.append(VERTICAL_BORDER);
        stringBuilder.append("\n");
    }

    private void buildUpperBorder(Field field, StringBuilder stringBuilder){
        stringBuilder.append("    ");
        stringBuilder.append(TOP_LEFT_CORNER);
        appendSymbols(stringBuilder, field.getWidth()*2, HORIZONTAL_BORDER);
        stringBuilder.append(TOP_RIGHT_CORNER);
        stringBuilder.append("\n");
    }

    private void buildDownBorder(Field field, StringBuilder stringBuilder){
        stringBuilder.append("    ");
        stringBuilder.append(BOTTOM_LEFT_CORNER);
        appendSymbols(stringBuilder, field.getWidth()*2, HORIZONTAL_BORDER);
        stringBuilder.append(BOTTOM_RIGHT_CORNER);
        stringBuilder.append("\n");
    }

    private void appendSymbols(StringBuilder stringBuilder, int count, String symbol) {
        for (int i = 0; i < count; i++) {
            stringBuilder.append(symbol);
        }
    }


}
