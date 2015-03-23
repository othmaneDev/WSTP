package stage.wstp.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Ressources/*")
/**
 *
 * */
public class DescriptionRessource extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public DescriptionRessource(){
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String[] fileSearch = req.getRequestURI().split("/");
		String fileName = fileSearch[fileSearch.length-1];
		resp.setContentType("text/plain");
		ServletOutputStream out = resp.getOutputStream();
		FileInputStream in = new FileInputStream("C://hrest_files/"+fileName);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0){
		    out.write(buffer, 0, length);
		}
		in.close();
		out.println();
	}
}
