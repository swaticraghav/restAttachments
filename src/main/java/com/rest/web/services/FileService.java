package com.rest.web.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.springframework.stereotype.Service;

@Path("/fileservice")
@Service
public class FileService {

	private static final String FILE_PATH = "C:/Users/swaragha/Desktop/Training/1. API/Rest Files Attachments/uploaded.jpg";

	@POST
	@Path("/upload")
	public void upload(List<Attachment> attachments) throws FileNotFoundException, IOException {
		for (Attachment attachment : attachments) {
			System.err.println("Server Side");
			copyFile(attachment.getDataHandler().getInputStream());
		}
	}

	private void copyFile(InputStream inputStream) throws FileNotFoundException, IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(FILE_PATH));
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	
	@GET
	@Path("/download")
	public Response download() {
		File file = new File(FILE_PATH);
		ResponseBuilder responseBuilder = Response.ok(file);
		responseBuilder.header("content-Disposition", "attachment;filename=download.jpg");
		return responseBuilder.build();
	}

}