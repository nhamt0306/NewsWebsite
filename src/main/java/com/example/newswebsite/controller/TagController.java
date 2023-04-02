package com.example.newswebsite.controller;

import com.example.newswebsite.config.LocalVariable;
import com.example.newswebsite.dto.tag.TagDTO;
import com.example.newswebsite.dto.tag.TagPagingDTO;
import com.example.newswebsite.entity.Tag;
import com.example.newswebsite.form.tag.CreateTagForm;
import com.example.newswebsite.form.tag.UpdateTagForm;
import com.example.newswebsite.mapper.TagMapper;
import com.example.newswebsite.service.impl.TagServiceImpl;
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
public class TagController {
    @Autowired
    TagServiceImpl tagService;
    @Autowired
    TagMapper tagMapper;

    @GetMapping("/tag/get-all")
    public ResponseEntity<?> getAll()
    {
        List<Tag> tagList = tagService.getAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList)
        {
            TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
            tagDTOList.add(tagDTO);
        }
        return ResponseEntity.ok(tagDTOList);
    }

    // category paging
    @GetMapping("/tag")
    public Object getAllWithPaging(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "100") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<Tag> tagList = new ArrayList<>();

        maxPageSize = tagService.findAllByStatus(LocalVariable.activeStatus).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        tagList = tagService.getAllPaging(pageNo-1, pageSize);

        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList)
        {
            TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
            tagDTOList.add(tagDTO);
        }

        TagPagingDTO tagPagingDTO = new TagPagingDTO(tagDTOList, maxPageSize);
        return tagPagingDTO;
    }

    @GetMapping("/tag/get-all-active")
    public ResponseEntity<?> getAllActive()
    {
        List<Tag> tagList = tagService.findAllByStatus(LocalVariable.activeStatus);
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList)
        {
            TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
            tagDTOList.add(tagDTO);
        }
        return ResponseEntity.ok(tagDTOList);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        Tag tag = tagService.findById(id);
        if (tag == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindTag + id, HttpStatus.NOT_FOUND);
        }
        else {
            TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
            return ResponseEntity.ok(tagDTO);
        }
    }

    @PostMapping("/admin/tag/create")
    public Object createTag(@RequestBody CreateTagForm createTagForm) throws ParseException {
        Tag tag = new Tag();
        tag.setTitle(createTagForm.getTitle());
        tag.setContent(createTagForm.getContent());
        tag.setSlug(createTagForm.getSlug());
        tag.setStatus(LocalVariable.activeStatus);
        tagService.save(tag);
        // response dto for FE
        TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
        return tagDTO;
    }

    @DeleteMapping("/admin/tag/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id)
    {
        tagService.deleteById(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteTagSuccess);
    }

    @PutMapping("/admin/tag/update")
    public Object updateTag(@RequestBody UpdateTagForm updateTagForm) throws ParseException {
        Tag tag = tagService.findById(updateTagForm.getId());
        if (tag == null) {
            return new ResponseEntity<>(LocalVariable.messageCannotFindTag + updateTagForm.getId(), HttpStatus.NOT_FOUND);
        }
        if (updateTagForm.getTitle() != null){
            tag.setTitle(updateTagForm.getTitle());
        }
        if (updateTagForm.getContent() != null){
            tag.setContent(updateTagForm.getContent());
        }
        if (updateTagForm.getSlug() != null){
            tag.setSlug(updateTagForm.getSlug());
        }

        tagService.save(tag);
        TagDTO tagDTO = tagMapper.mapperTagToDTO(tag);
        return tagDTO;
    }
}
