package BLTonThyme.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScriptHelper {
    private String filepath;
    private ArrayList<String> lines;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    ScriptHelper(String filepath) {
        this.filepath = filepath;
        lines = new ArrayList<>();
    }

    public ArrayList<String> getLines() {
        if (lines.isEmpty()) {
            String line;
            is = getClass().getResourceAsStream(filepath);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            try {
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public void close() {
        try {
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
