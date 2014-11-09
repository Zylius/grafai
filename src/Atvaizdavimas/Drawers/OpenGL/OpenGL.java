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
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class OpenGL extends GLCanvas implements GLEventListener, IDrawer {
    Window window;
    private FPSAnimator animator;
    GLU glu;
    GL2 gl;
    float distance = (float)30.0;
    private float viewRotX = 20.0f;
    private float viewRotY = 30.0f;
    IMap map;

    public OpenGL()
    {
        super(new GLCapabilities(null));
        setPreferredSize(new Dimension(1000, 1000));
        setSize(1000,1000);
        addGLEventListener(this);

        this.window = new Window(this);

        animator = new FPSAnimator(this, 60);
        animator.start();
        animator.setUpdateFPSFrames(3, null);

        glu = new GLU();
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

        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {1.f, 1.f, 0.f, 0f};

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        float[] rgba = {1.f, 1.f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL2.GL_SHININESS, 0.9f);

        for(Point point : this.map.getPoints()){
            this.drawSphere(1.0f, new float[]{point.getX(), point.getY(), point.getZ()}, new float[]{1f, 0f, 0f});
        }

        gl.glFlush();
    }

    private void drawSphere(float radius, float[] point, float[] color)
    {
        gl.glColor3fv(color, 0);
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        gl.glTranslated(point[0], point[1], point[2]);
        glu.gluSphere(earth, radius, 100, 100);

        glu.gluDeleteQuadric(earth);
    }

    private void drawLine(float x1, float x2, float y1, float y2, float z1, float z2) {
        gl.glVertex3f(x1, y1, z1);
        gl.glVertex3f(x2, y2, z2);
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