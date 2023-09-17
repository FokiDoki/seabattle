package org.seabattle.view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FrameRepository {

    Map<String, String> frames = new HashMap<>();

    private static final String FRAME_DELIMITER = "#";

    public FrameRepository(String path) {
        try {
            readFile(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read file " + path, e);
        }
    }

    public void readFile(String path) throws IOException {
        InputStreamReader is = new InputStreamReader(new FileInputStream(path));
        try (BufferedReader br = new BufferedReader(is)){
            String currentFrame = null;
            while (br.ready()){
                String line = br.readLine();
                if (line.startsWith(FRAME_DELIMITER)){
                    currentFrame = line.substring(1);
                    frames.put(currentFrame, "");
                } else {
                    frames.put(currentFrame, frames.get(currentFrame) + line + "\n");
                }
            }

        }
    }

    public String getFrame(String name){
        return frames.get(name);
    }
}
