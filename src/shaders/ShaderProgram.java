package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {

	List<Integer> shaders = new ArrayList<Integer>();
	List<Integer> programs = new ArrayList<Integer>();
	
	private static int vID,fID,pID;

	public static int getvID() {
		return vID;
	}

	public static int getfID() {
		return fID;
	}

	public static int getpID() {
		return pID;
	}


	//#######################################################
	public ShaderProgram(String vertex, String fragment) {
		StringBuilder vsource = new StringBuilder(readFile(vertex));
		StringBuilder fsource = new StringBuilder(readFile(fragment));		
		createShaderAndProgram(vsource, fsource);
	}
	//#######################################################
	
	public void start() {
		GL20.glUseProgram(pID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	private static StringBuilder readFile(String file){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        
        return shaderSource;
    }

	private void createShaderAndProgram(StringBuilder vsource, StringBuilder fsource) {
		vID = createShader(GL20.GL_VERTEX_SHADER);
		fID = createShader(GL20.GL_FRAGMENT_SHADER);

		putSourceToShader(vID,vsource);
		putSourceToShader(fID,fsource);

		try {
			compileShader(vID);
			compileShader(fID);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pID = createProgram();			
		attachShadersToProgram(pID, vID, fID);
		enableProgram(pID);
	}

	private int createShader(int type) {
		int shader = GL20.glCreateShader(type);
		this.shaders.add(shader);
		return shader;
	}

	private void putSourceToShader(int shader, StringBuilder data) {
		GL20.glShaderSource(shader, data);
	}
	
	private void compileShader(int shader) throws IOException {
		GL20.glCompileShader(shader);
		
		if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shader, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
	}
	
	private int createProgram() {
		int program = GL20.glCreateProgram();
		programs.add(program);
		return program;
	}

	private void attachShadersToProgram(int program, int vertexShader, int fragmentShader) {
		GL20.glAttachShader(program, vertexShader);
		GL20.glAttachShader(program, fragmentShader);
	}

	private void enableProgram(int program) {
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
	}

	public void cleanUp() {
		GL20.glDetachShader(pID, vID);
		GL20.glDetachShader(pID, vID);
		for(int s:shaders) GL20.glDeleteShader(s);
		for(int p:programs) GL20.glDeleteProgram(p);
		shaders.clear();
		programs.clear();
	}

}