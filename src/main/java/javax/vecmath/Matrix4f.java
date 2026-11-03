package javax.vecmath;

public class Matrix4f {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    public Matrix4f() {
        setIdentity();
    }

    public void setIdentity() {
        m00 = 1; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = 1; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = 1; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;
    }

    public void transform(Vector4f vec) {
        float x = m00 * vec.x + m01 * vec.y + m02 * vec.z + m03 * vec.w;
        float y = m10 * vec.x + m11 * vec.y + m12 * vec.z + m13 * vec.w;
        float z = m20 * vec.x + m21 * vec.y + m22 * vec.z + m23 * vec.w;
        float w = m30 * vec.x + m31 * vec.y + m32 * vec.z + m33 * vec.w;
        vec.set(x, y, z, w);
    }
}
