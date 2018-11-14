package engineTester;

import org.lwjgl.glfw.GLFW;

import renderEngine.Loader;
import renderEngine.Renderer;
import renderEngine.WindowManager;
import shaders.ShaderProgram;

public class MainGameLoop {

	public static void main(String[] args) {
		WindowManager.createWindow();
		
		ShaderProgram shader = new ShaderProgram(
				"src/shaders/vertexShader.txt",
				"src/shaders/fragmentShader.txt");

		Renderer render = new Renderer();
		
		
		float vertexData[] = {
				0.0f, 0.5f, 0.0f, 1.0f,
				0.5f, -0.366f, 0.0f, 1.0f,
				-0.5f, -0.366f, 0.0f, 1.0f,

				1.0f, 0.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 1.0f, 1.0f				
				};
		
		Loader.loadToGPU(vertexData);

		while(!GLFW.glfwWindowShouldClose(WindowManager.getWindow())) {
			render.prepare();
			shader.start();
			render.render();
			shader.stop();
			WindowManager.updateWindow();
		}
		
		shader.cleanUp();
		
		WindowManager.closeWindow();
	}

}
