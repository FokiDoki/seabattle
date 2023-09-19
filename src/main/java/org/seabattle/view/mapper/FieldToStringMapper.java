package org.seabattle.view.mapper;

import org.seabattle.FIeld.Field;

import java.awt.*;

public class FieldToStringMapper {

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


    public static String map(Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        buildNumericHeader(field, stringBuilder);
        buildUpperBorder(field, stringBuilder);
        buildFieldBody(field, stringBuilder);
        buildDownBorder(field, stringBuilder);
        return stringBuilder.toString();
    }

    private static void buildFieldBody(Field field, StringBuilder stringBuilder){
        for (int y = 0; y < field.getHeight(); y++) {
            stringBuilder.append("%d\t".formatted(y+1));
            stringBuilder.append(VERTICAL_BORDER);
            for (int x = 0; x < field.getWidth(); x++) {
                for (int i = 0; i < 2; i++) {
                    switch (field.getCellStatus(new Point(x, y))) {
                        case EMPTY:
                            stringBuilder.append(EMPTY_CELL);
                            break;
                        case SHIP:
                            stringBuilder.append(SHIP_CELL);
                            break;
                        case HIT:
                            stringBuilder.append(HIT_CELL);
                            break;
                        case MISS:
                            stringBuilder.append(MISS_CELL);
                            break;
                    }
                }
            }
            stringBuilder.append(VERTICAL_BORDER);
            stringBuilder.append("\n");
        }
    }

    private static void buildNumericHeader(Field field, StringBuilder stringBuilder){
        stringBuilder.append("\t");
        stringBuilder.append(VERTICAL_BORDER);
        for (int i = 0; i < field.getWidth(); i++) {
            stringBuilder.append(i+1);
            stringBuilder.append(" ");
        }
        stringBuilder.append(VERTICAL_BORDER);
        stringBuilder.append("\n");
    }

    private static void buildUpperBorder(Field field, StringBuilder stringBuilder){
        stringBuilder.append("\t");
        stringBuilder.append(TOP_LEFT_CORNER);
        appendSymbols(stringBuilder, field.getWidth()*2, HORIZONTAL_BORDER);
        stringBuilder.append(TOP_RIGHT_CORNER);
        stringBuilder.append("\n");
    }

    private static void buildDownBorder(Field field, StringBuilder stringBuilder){
        stringBuilder.append("\t");
        stringBuilder.append(BOTTOM_LEFT_CORNER);
        appendSymbols(stringBuilder, field.getWidth()*2, HORIZONTAL_BORDER);
        stringBuilder.append(BOTTOM_RIGHT_CORNER);
        stringBuilder.append("\n");
    }

    private static void appendSymbols(StringBuilder stringBuilder, int count, String symbol) {
        for (int i = 0; i < count; i++) {
            stringBuilder.append(symbol);
        }
    }


}
