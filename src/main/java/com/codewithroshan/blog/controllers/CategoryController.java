package com.codewithroshan.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithroshan.blog.payloads.ApiResponse;
import com.codewithroshan.blog.payloads.CategoryDto;
import com.codewithroshan.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	//create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createdCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId")Integer categoryId)
	{
		CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,categoryId);
		return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
	}
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@Valid @PathVariable("categoryId")Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
		List<CategoryDto> lc=this.categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDto>>(lc,HttpStatus.OK);
	}
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId")Integer categoryId)
	{
		CategoryDto lc=this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(lc,HttpStatus.OK);
	}
}
