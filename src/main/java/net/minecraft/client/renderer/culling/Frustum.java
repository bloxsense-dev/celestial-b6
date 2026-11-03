package net.minecraft.client.renderer.culling;

import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
public class Frustum {
    public static final int OFFSET_STEP = 4;
    private final FrustumIntersection intersection = new FrustumIntersection();
    private final Matrix4f matrix = new Matrix4f();
    private Vector4f viewVector;
    private double camX;
    private double camY;
    private double camZ;

    public final Vector4f[] planes = new Vector4f[] {
        new Vector4f(), new Vector4f(), new Vector4f(), new Vector4f(), new Vector4f(), new Vector4f()
    };

    public Frustum(final Matrix4fc modelView, final Matrix4f projection) {
        this.calculateFrustum(modelView, projection);
    }

    // cel-compat ctor
    public Frustum(final Matrix4fc modelView, final Matrix4f projection, final boolean forShadow) {
        this(modelView, projection);
    }

    public Frustum(final Frustum frustum) {
        this.set(frustum);
    }

    public void set(final Frustum frustum) {
        this.intersection.set(frustum.matrix);
        this.matrix.set(frustum.matrix);
        this.camX = frustum.camX;
        this.camY = frustum.camY;
        this.camZ = frustum.camZ;
        this.viewVector = frustum.viewVector;
    }

    public Frustum offset(final float offset) {
        this.camX = this.camX + this.viewVector.x * offset;
        this.camY = this.camY + this.viewVector.y * offset;
        this.camZ = this.camZ + this.viewVector.z * offset;
        return this;
    }

    public Frustum offsetToFullyIncludeCameraCube(final int cubeSize) {
        double camX1 = Math.floor(this.camX / cubeSize) * cubeSize;
        double camY1 = Math.floor(this.camY / cubeSize) * cubeSize;
        double camZ1 = Math.floor(this.camZ / cubeSize) * cubeSize;
        double camX2 = Math.ceil(this.camX / cubeSize) * cubeSize;
        double camY2 = Math.ceil(this.camY / cubeSize) * cubeSize;

        for (double camZ2 = Math.ceil(this.camZ / cubeSize) * cubeSize;
            this.intersection
                    .intersectAab(
                        (float)(camX1 - this.camX),
                        (float)(camY1 - this.camY),
                        (float)(camZ1 - this.camZ),
                        (float)(camX2 - this.camX),
                        (float)(camY2 - this.camY),
                        (float)(camZ2 - this.camZ)
                    )
                != -2;
            this.camZ = this.camZ - this.viewVector.z() * 4.0F
        ) {
            this.camX = this.camX - this.viewVector.x() * 4.0F;
            this.camY = this.camY - this.viewVector.y() * 4.0F;
        }

        return this;
    }

    public void prepare(final double camX, final double camY, final double camZ) {
        this.camX = camX;
        this.camY = camY;
        this.camZ = camZ;
    }

    private void calculateFrustum(final Matrix4fc modelView, final Matrix4f projection) {
        projection.mul(modelView, this.matrix);
        this.intersection.set(this.matrix);
        this.viewVector = this.matrix.transformTranspose(new Vector4f(0.0F, 0.0F, 1.0F, 0.0F));
    }

    public boolean isVisible(final AABB bb) {
        int intersectionResult = this.cubeInFrustum(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
        return intersectionResult == -2 || intersectionResult == -1;
    }

    public int cubeInFrustum(final BoundingBox bb) {
        return this.cubeInFrustum(bb.minX(), bb.minY(), bb.minZ(), bb.maxX() + 1, bb.maxY() + 1, bb.maxZ() + 1);
    }

    private int cubeInFrustum(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
        float x1 = (float)(minX - this.camX);
        float y1 = (float)(minY - this.camY);
        float z1 = (float)(minZ - this.camZ);
        float x2 = (float)(maxX - this.camX);
        float y2 = (float)(maxY - this.camY);
        float z2 = (float)(maxZ - this.camZ);
        return this.intersection.intersectAab(x1, y1, z1, x2, y2, z2);
    }

    public boolean pointInFrustum(final double x, final double y, final double z) {
        return this.intersection.testPoint((float)(x - this.camX), (float)(y - this.camY), (float)(z - this.camZ));
    }

    public Vector4f[] getFrustumPoints() {
        Vector4f[] frustumPoints = new Vector4f[]{
            new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F),
            new Vector4f(1.0F, -1.0F, -1.0F, 1.0F),
            new Vector4f(1.0F, 1.0F, -1.0F, 1.0F),
            new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F),
            new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F),
            new Vector4f(1.0F, -1.0F, 1.0F, 1.0F),
            new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
            new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F)
        };
        Matrix4f clipToWorldMatrix = this.matrix.invert(new Matrix4f());

        for (int i = 0; i < 8; i++) {
            clipToWorldMatrix.transform(frustumPoints[i]);
            frustumPoints[i].div(frustumPoints[i].w());
        }

        return frustumPoints;
    }

    // cel-compat disabled
    public boolean disabled;

    public double getCameraX() { return this.camX; }

    public double getCameraY() { return this.camY; }

    public double getCameraZ() { return this.camZ; }

    private void updatePlanes() {
        Vector4f r0 = new Vector4f(); this.matrix.getRow(0, r0);
        Vector4f r1 = new Vector4f(); this.matrix.getRow(1, r1);
        Vector4f r2 = new Vector4f(); this.matrix.getRow(2, r2);
        Vector4f r3 = new Vector4f(); this.matrix.getRow(3, r3);
        this.planes[0].set(r3).add(r0);
        this.planes[1].set(r3).sub(r0);
        this.planes[2].set(r3).add(r1);
        this.planes[3].set(r3).sub(r1);
        this.planes[4].set(r3).add(r2);
        this.planes[5].set(r3).sub(r2);
    }

    // cel-frustum
    public void setPosition(double x, double y, double z) {
        this.prepare(x, y, z);
    }

    public boolean isBoundingBoxInFrustum(net.minecraft.util.math.AxisAlignedBB box) {
        return this.isVisible(new net.minecraft.world.phys.AABB(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ));
    }

    public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.cubeInFrustum(minX, minY, minZ, maxX, maxY, maxZ) == -2;
    }

    public double getCamX() {
        return this.camX;
    }

    public double getCamY() {
        return this.camY;
    }

    public double getCamZ() {
        return this.camZ;
    }
}