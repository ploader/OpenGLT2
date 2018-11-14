package renderEngine;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	List<Integer> vbos = new ArrayList<Integer>();
	
	private static int vboID;
	
	public static void loadToGPU(float[] vertices) {
		vboID = createBufferGPUMemory();
		storeDataInVBO(vboID, vertices);
		ReadData(0,4, GL11.GL_FLOAT, false, 0);
		ReadData(1,4, GL11.GL_FLOAT, false, vertices.length*2);
		unbind();
	}

	private static void ReadData(int attributeNumber, int nvalues, int type,
			boolean glFalse, int offset) {
		GL30.glVertexAttribPointer(attributeNumber, nvalues, type, glFalse, 0, offset);
	}

	private static void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private static void storeDataInVBO(int vboID, float[] vertices) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer fbuffer = BufferUtils.createFloatBuffer(vertices.length);
		fbuffer.put(vertices);
		fbuffer.flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fbuffer, GL15.GL_STATIC_DRAW);
	}

	private static int createBufferGPUMemory() {
		int vbo = GL20.glGenBuffers();
		return vbo;
	}

}
