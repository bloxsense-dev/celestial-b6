package net.minecraft.util.math;

public class RayTraceResult {
    public Type typeOfHit;
    public BlockPos blockPos;
    public Vec3d hitVec;
    public net.minecraft.world.entity.Entity entityHit;

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public enum Type {
        MISS,
        BLOCK,
        ENTITY
    }

    public RayTraceResult(Vec3d hitVec) {
        this.typeOfHit = Type.MISS;
        this.hitVec = hitVec;
    }

    public RayTraceResult(Type type, Vec3d hitVec, BlockPos pos) {
        this.typeOfHit = type;
        this.hitVec = hitVec;
        this.blockPos = pos;
    }

    public RayTraceResult(net.minecraft.world.phys.HitResult hit) {
        if (hit == null || hit.getType() == net.minecraft.world.phys.HitResult.Type.MISS) {
            this.typeOfHit = Type.MISS;
            this.hitVec = hit != null ? new Vec3d(hit.getLocation()) : new Vec3d(0, 0, 0);
        } else if (hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
            this.typeOfHit = Type.BLOCK;
            this.hitVec = new Vec3d(hit.getLocation());
            if (hit instanceof net.minecraft.world.phys.BlockHitResult bhr) {
                this.blockPos = new BlockPos(bhr.getBlockPos());
            }
        } else if (hit.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
            this.typeOfHit = Type.ENTITY;
            this.hitVec = new Vec3d(hit.getLocation());
            if (hit instanceof net.minecraft.world.phys.EntityHitResult ehr) {
                this.entityHit = ehr.getEntity();
            }
        }
    }
}
