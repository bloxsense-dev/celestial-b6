package net.minecraft.util;

import net.minecraft.resources.Identifier;

public class ResourceLocation {
    private final Identifier identifier;

    public ResourceLocation(String resourceName) {
        if (!resourceName.contains(":")) {
            this.identifier = new Identifier("minecraft", resourceName);
        } else {
            this.identifier = new Identifier(resourceName);
        }
    }

    public ResourceLocation(String namespaceIn, String pathIn) {
        this.identifier = new Identifier(namespaceIn, pathIn);
    }

    public ResourceLocation(Identifier identifier) {
        this.identifier = identifier;
    }

    public Identifier toIdentifier() {
        return this.identifier;
    }

    public String getResourcePath() {
        return this.identifier.getPath();
    }

    public String getResourceDomain() {
        return this.identifier.getNamespace();
    }

    public String getPath() {
        return this.identifier.getPath();
    }

    public String getNamespace() {
        return this.identifier.getNamespace();
    }

    @Override
    public String toString() {
        return this.identifier.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ResourceLocation) return this.identifier.equals(((ResourceLocation) o).identifier);
        if (o instanceof Identifier) return this.identifier.equals(o);
        return false;
    }

    @Override
    public int hashCode() {
        return this.identifier.hashCode();
    }
}
