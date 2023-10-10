package com.codewithroshan.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithroshan.blog.entities.Category;
import com.codewithroshan.blog.exceptions.ResourceNotFoundException;
import com.codewithroshan.blog.payloads.CategoryDto;
import com.codewithroshan.blog.repositories.CategoryRepo;
import com.codewithroshan.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category c1=this.modelMapper.map(categoryDto, Category.class);
		CategoryDto savedCategory=this.modelMapper.map(this.categoryRepo.save(c1),CategoryDto.class);
		return savedCategory;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category c1=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		c1.setCategoryTitle(categoryDto.getCategoryTitle());
		c1.setCategoryDescription(categoryDto.getCategoryDescription());
		
		return this.modelMapper.map(this.categoryRepo.save(c1), CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category c1=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		this.categoryRepo.delete(c1);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category c1=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
		CategoryDto d1=this.modelMapper.map(c1, CategoryDto.class);
		return d1;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> l1=this.categoryRepo.findAll();
		List<CategoryDto> l=new ArrayList<>();
		for(int i=0;i<l1.size();i++)
		{
			Category c=l1.get(i);
			l.add(this.modelMapper.map(c,CategoryDto.class));
		}
		return l;
	}

}
