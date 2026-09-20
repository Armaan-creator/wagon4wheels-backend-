package com.wagon4wheels.backend.controller;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final GridFsTemplate gridFsTemplate;

    public ImageController(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<org.springframework.core.io.Resource> getImage(@PathVariable String id) throws IOException {
        GridFSFile file;
        try {
            file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(id))));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        GridFsResource resource = gridFsTemplate.getResource(file);
        MediaType contentType;
        try {
            // GridFsResource.getContentType() throws (not returns null) when
            // no content-type metadata was stored for this file - fall back
            // to a generic type rather than 500ing on a perfectly fetchable file.
            contentType = MediaType.parseMediaType(resource.getContentType());
        } catch (Exception e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }
}
