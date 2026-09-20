package com.wagon4wheels.backend.service;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Stores uploaded images in MongoDB itself (via GridFS) instead of local
// disk - local disk on most cloud hosts (Render included) is wiped on every
// redeploy, but MongoDB Atlas is real persistent storage.
@Service
public class FileStorageService {

    private final GridFsTemplate gridFsTemplate;

    public FileStorageService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public List<String> store(List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file";
            try {
                Object id = gridFsTemplate.store(file.getInputStream(), original, file.getContentType());
                urls.add("/api/images/" + id.toString());
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file: " + original, e);
            }
        }
        return urls;
    }

    // Removes a stored image given its "/api/images/{id}" URL (accepts a
    // full URL or just the path - only the trailing id segment matters).
    public void deleteByUrl(String url) {
        String id = url.substring(url.lastIndexOf('/') + 1);
        try {
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(new ObjectId(id))));
        } catch (IllegalArgumentException e) {
            // Not a GridFS id (e.g. an old local-disk URL from before this
            // migration, or an external URL) - nothing for us to clean up.
        }
    }
}
