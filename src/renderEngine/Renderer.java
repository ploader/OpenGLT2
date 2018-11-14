package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Renderer {

	public void prepare() {
		GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
		GL30.glClearColor(0, 0, 0, 0);
	}

	public void render() {
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
	}
}
