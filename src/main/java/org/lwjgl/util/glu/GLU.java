package org.lwjgl.util.glu;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLU {
    public static boolean gluProject(float objx, float objy, float objz, FloatBuffer modelMatrix, FloatBuffer projMatrix, IntBuffer viewport, FloatBuffer win_pos) {
        float[] in = new float[] { objx, objy, objz, 1.0f };
        float[] out = new float[4];
        float[] out2 = new float[4];

        multMatrixVec(modelMatrix, in, out);
        multMatrixVec(projMatrix, out, out2);

        if (out2[3] == 0.0f) return false;

        out2[0] /= out2[3];
        out2[1] /= out2[3];
        out2[2] /= out2[3];

        int vPos = viewport.position();
        win_pos.put(0, viewport.get(vPos) + (1.0f + out2[0]) * viewport.get(vPos + 2) / 2.0f);
        win_pos.put(1, viewport.get(vPos + 1) + (1.0f + out2[1]) * viewport.get(vPos + 3) / 2.0f);
        win_pos.put(2, (1.0f + out2[2]) / 2.0f);

        return true;
    }

    private static void multMatrixVec(FloatBuffer m, float[] in, float[] out) {
        int pos = m.position();
        for (int i = 0; i < 4; i++) {
            out[i] = in[0] * m.get(pos + i) +
                     in[1] * m.get(pos + 4 + i) +
                     in[2] * m.get(pos + 8 + i) +
                     in[3] * m.get(pos + 12 + i);
        }
    }
}
