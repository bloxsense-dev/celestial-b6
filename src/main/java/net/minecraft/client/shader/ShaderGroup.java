package net.minecraft.client.shader;

import java.util.ArrayList;
import java.util.List;

public class ShaderGroup {
    public Framebuffer mainFramebuffer;

    public ShaderGroup(Object... args) {
        this.mainFramebuffer = new Framebuffer(1920, 1080, true);
    }

    public void createBindFramebuffers(int width, int height) {
    }

    public void loadShaderGroup(float partialTicks) {
    }

    public List<Shader> getShaders() {
        List<Shader> list = new ArrayList<>();
        list.add(new Shader());
        list.add(new Shader());
        list.add(new Shader());
        return list;
    }

    public static class Shader {
        private final ShaderManager manager = new ShaderManager();
        public ShaderManager getShaderManager() { return manager; }
    }

    public static class ShaderManager {
        public ShaderUniform getShaderUniform(String name) { return new ShaderUniform(); }
    }

    public static class ShaderUniform {
        public void set(float val) {}
        public void set(int val) {}
        public void set(float v1, float v2) {}
        public void set(float v1, float v2, float v3) {}
        public void set(float v1, float v2, float v3, float v4) {}
    }
}
