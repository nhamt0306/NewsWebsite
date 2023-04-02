package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.category.CategoryDTO;
import com.example.newswebsite.dto.category.CategoryPagingDTO;
import com.example.newswebsite.entity.Category;
import com.example.newswebsite.form.category.CreateCategoryForm;
import com.example.newswebsite.form.category.UpdateCategoryForm;
import com.example.newswebsite.mapper.CategoryMapper;
import com.example.newswebsite.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class CategoryController {
    @Autowired
    CategoryServiceImpl categorySerivce;
    @Autowired
    CategoryMapper categoryMapper;


    @GetMapping("/category/get-all")
    public ResponseEntity<?> getAllCategory()
    {
        List<Category> categoryList = categorySerivce.getAll();
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category categoryEntity : categoryList)
        {
            CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
            categoryDTOS.add(categoryDTO);
        }
        return ResponseEntity.ok(categoryDTOS);
    }

    // category paging
    @GetMapping("/category")
    public Object getAllCategory(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "100") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Category> categoryEntityList = new ArrayList<>();

        maxPageSize = categorySerivce.findAllByStatus(LocalVariable.activeStatus).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        categoryEntityList = categorySerivce.getAllCategoryPaging(pageNo-1, pageSize);

        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category categoryEntity : categoryEntityList)
        {
            CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
            categoryDTOS.add(categoryDTO);
        }

        CategoryPagingDTO categoryPagingResponse = new CategoryPagingDTO(categoryDTOS, maxPageSize);
        return categoryPagingResponse;
    }

    @GetMapping("/category/get-all-active")
    public ResponseEntity<?> getAllCategoryActive()
    {
        List<Category> categoryEntityList = categorySerivce.findAllByStatus(LocalVariable.activeStatus);
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category categoryEntity : categoryEntityList)
        {
            CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
            categoryDTOS.add(categoryDTO);
        }
        return ResponseEntity.ok(categoryDTOS);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable long id){
        Category categoryEntity = categorySerivce.findById(id);
        if (categoryEntity == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
        else {
            CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
            return ResponseEntity.ok(categoryDTO);
        }
    }

    @GetMapping("/category/parentid/{id}")
    public ResponseEntity<?> getCategoryByParent(@PathVariable long id){
        try {
            List<Category> categoryEntityList = categorySerivce.findByCategoryByParentId(id);
            List<CategoryDTO> categoryDTOS = new ArrayList<>();
            for (Category categoryEntity : categoryEntityList)
            {
                CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
                categoryDTOS.add(categoryDTO);
            }
            return ResponseEntity.ok(categoryDTOS);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/category/create")
    public Object createCategory(@RequestBody CreateCategoryForm createCategoryForm) throws ParseException {
        Category categoryEntity = new Category();
        categoryEntity.setTitle(createCategoryForm.getTitle());
        categoryEntity.setContent(createCategoryForm.getContent());
        categoryEntity.setSlug(createCategoryForm.getSlug());
        categoryEntity.setStatus(LocalVariable.activeStatus);
        categoryEntity.setParentId(createCategoryForm.getParentId());
        categorySerivce.save(categoryEntity);
        // response dto for FE
        CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
        return categoryDTO;
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable long id)
    {
        categorySerivce.deleteById(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }

    @PutMapping("/admin/category/update")
    public Object updateCategory(@RequestBody UpdateCategoryForm updateCategoryForm) throws ParseException {
        Category categoryEntity = categorySerivce.findById(updateCategoryForm.getId());
        if (categoryEntity == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + updateCategoryForm.getId(), HttpStatus.NOT_FOUND);
        }
        if (updateCategoryForm.getTitle() != null){
            categoryEntity.setTitle(updateCategoryForm.getTitle());
        }
        if (updateCategoryForm.getContent() != null){
            categoryEntity.setContent(updateCategoryForm.getContent());
        }
        if (updateCategoryForm.getSlug() != null){
            categoryEntity.setSlug(updateCategoryForm.getSlug());
        }
        if (updateCategoryForm.getParentId() != null){
            categoryEntity.setParentId(updateCategoryForm.getParentId());
        }

        categorySerivce.save(categoryEntity);
        CategoryDTO categoryDTO = categoryMapper.mapperCategoryToDTO(categoryEntity);
        return categoryDTO;
    }
}
