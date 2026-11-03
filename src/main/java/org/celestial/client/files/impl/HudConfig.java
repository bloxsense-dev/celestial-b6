package org.celestial.client.files.impl;

import org.celestial.client.Celestial;
import org.celestial.client.files.FileManager;
import org.celestial.client.ui.components.draggable.DraggableModule;

import java.io.*;

public class HudConfig extends FileManager.CustomFile {

    public HudConfig(String name, boolean loadOnStart) {
        super(name, loadOnStart);
    }

    public void loadFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.getFile().getAbsolutePath());
            DataInputStream in = new DataInputStream(fileInputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            boolean currentLayout = false;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                if (curLine.equals("# layout-v2")) { currentLayout = true; continue; }
                if (curLine.isEmpty() || curLine.startsWith("#")) continue;
                if (!currentLayout && curLine.startsWith("ClientInfoComponent:")) continue;
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                for (DraggableModule hudModule : Celestial.instance.draggableManager.getMods()) {
                    if (hudModule.getName().equals(curLine.split(":")[0])) {
                        hudModule.layoutLoaded = currentLayout;
                        hudModule.drag.setXPosition(Integer.parseInt(x));
                        hudModule.drag.setYPosition(Integer.parseInt(y));
                    }
                }
            }
            br.close();
        } catch (Exception ignored) {

        }
    }

    public void saveFile() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(this.getFile()));
            out.write("# layout-v2\r\n");
            for (DraggableModule draggableModule : Celestial.instance.draggableManager.getMods()) {
                out.write(draggableModule.getName() + ":" + draggableModule.drag.getXPosition() + ":" + draggableModule.drag.getYPosition());
                out.write("\r\n");
            }
            out.close();
        } catch (Exception ignored) {

        }
    }
}
