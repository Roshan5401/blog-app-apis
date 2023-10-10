package com.codewithroshan.blog.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithroshan.blog.services.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//File name
		String name=file.getOriginalFilename();
		//Full path
		String filePath=path+File.separator+name;
		//create folder if not there
		File f=new File(path);
		if(!f.exists())
			f.mkdir();
		//file copy
		Files.copy(file.getInputStream(),Paths.get(filePath));
		return name;
	}

	@Override
	public InputStream getResouce(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
