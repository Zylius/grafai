package Atvaizdavimas.Drawers.OpenGL;

import java.applet.*;
import java.awt.*;


import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import Atvaizdavimas.IDrawer;
import Classes.*;
import Classes.Point;
import Intefaces.IMap;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.math.VectorUtil;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

@SuppressWarnings("serial")
public class OpenGL extends GLCanvas implements GLEventListener, IDrawer {
    Window window;
    private FPSAnimator animator;
    GLU glu;
    GL2 gl;
    float distance = (float)30.0;
    private float viewRotX = 20.0f;
    private float viewRotY = 30.0f;
    private boolean drawText = false;
    IMap map;
    TextRenderer textRenderer = new TextRenderer(new Font("CourierNew", Font.ITALIC, 12));

    public OpenGL()
    {
        super(getCapabilites());
        setPreferredSize(new Dimension(1000, 1000));
        setSize(1000,1000);
        addGLEventListener(this);

        this.window = new Window(this);

        animator = new FPSAnimator(this, 60);
        animator.start();
        animator.setUpdateFPSFrames(3, null);

        glu = new GLU();
    }

    private static GLCapabilities getCapabilites()
    {
        GLCapabilities caps = new GLCapabilities(null);
        caps.setSampleBuffers(true);
        caps.setNumSamples(4);
        return caps;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = this.getGL().getGL2();
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        gl.glEnable(GL2.GL_VERTEX_PROGRAM_POINT_SIZE);
        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int _width, int _height) {

        if ( _height <= 0)
        {
            return;
        }
        final float h = (float) _width / (float) _height;
        gl.glViewport(0, 0, _width, _height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }


    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        setCamera(gl, glu);

        for (Edge edge : this.map.returnTree()) {
            gl.glColor3f(0.0f, 1.0f, 0.0f);
            this.drawLine(edge.getFirstPoint(), edge.getSecondPoint(), 0.1, 100);

            Point midPoint = new Point(0,
                    (edge.getFirstPoint().getX() + edge.getSecondPoint().getX())/2,
                    (edge.getFirstPoint().getY() + edge.getSecondPoint().getY())/2,
                    (edge.getFirstPoint().getZ() + edge.getSecondPoint().getZ())/2
            );

            this.drawData(midPoint, (float)ShortestEdgeMap.countDistance(edge.getFirstPoint(), edge.getSecondPoint()));
        }

        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.3f);
        for(Point point : this.map.getPoints()){
            for(int anotherPointID : point.getConnection()) {
                this.drawLine(point, this.map.getPoints()[anotherPointID], 0.05, 100);
            }
        }

        for(Point point : this.map.getPoints()){
            this.drawSphere(0.1f, point, new float[]{1f, 1f, 1f});
            this.drawData(point, point.getID());
        }

        this.drawFps();

        gl.glFlush();
    }

    public void setDrawText(boolean enabled)
    {
        this.drawText = enabled;
    }

    private void drawData(Point point, float value)
    {
        if(!drawText) {
            return;
        }
        textRenderer.begin3DRendering();
        textRenderer.setSmoothing(true);
        textRenderer.setUseVertexArrays(false);
        textRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        textRenderer.draw3D(
                (value==Math.ceil(value)?((Integer)Math.round(value)).toString():String.format("%.2f", value)),
                point.getX(),
                point.getY(),
                point.getZ(),
                0.03f
        );
        textRenderer.end3DRendering();
    }

    private void drawFps()
    {
        textRenderer.beginRendering(this.getWidth(), this.getHeight());
        textRenderer.setSmoothing(true);
        textRenderer.setUseVertexArrays(false);
        textRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        textRenderer.draw(String.format("FPS: %.2f", animator.getLastFPS()), +16, this.getHeight() - 16);
        textRenderer.endRendering();
    }

    private void drawSphere(float radius, Point point, float[] color)
    {
        gl.glPushMatrix();
        gl.glColor3fv(color, 0);
        GLUquadric sphere = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
        glu.gluQuadricNormals(sphere, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(sphere, GLU.GLU_OUTSIDE);
        gl.glTranslated(point.getX(), point.getY(), point.getZ());
        glu.gluSphere(sphere, radius, 20, 20);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(sphere);
    }

    private void drawLine(Point from, Point to, double width, int slices)
    {
        float d[] = new float[]{ to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ() };
        gl.glPushMatrix();
        GLUquadric line = glu.gluNewQuadric();
        float z[] = new float[]{ 0, 0, 1 };
        float angle = (float) Math.acos(dot(z, d) / Math.sqrt(dot(d, d) * dot(z, z)));
        z = VectorUtil.crossVec3(new float[]{1.f, 1.f, 1.f}, z, d);
        gl.glTranslated(from.getX(), from.getY(), from.getZ());
        gl.glRotated(angle * 180 / Math.PI, z[0], z[1], z[2]);
        glu.gluCylinder(line, width / 2, width / 2, Math.sqrt(dot(d, d)), slices, 1);
        gl.glPopMatrix();
        glu.gluDeleteQuadric(line);
    }

    private float dot(float[] a, float[] b)
    {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public void setMap(IMap map)
    {
        this.map = map;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    private void setCamera(GL2 gl, GLU glu) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(distance, distance, distance, 0, 0, 0, 0, 1, 0);
        gl.glRotatef(viewRotX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(viewRotY, 0.0f, 1.0f, 0.0f);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void deltaZoom(float delta)
    {
        distance += delta;
    }

    public void setCameraPos(float thetaX, float thetaY)
    {
        viewRotX += thetaX;
        viewRotY += thetaY;
    }
}