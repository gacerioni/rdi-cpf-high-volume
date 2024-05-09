package io.platformengineer.rdicpfhighvolume.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DatabaseCleanupService databaseCleanupService;

    @PostMapping("/cleanup")
    public ResponseEntity<String> cleanupDatabase() {
        databaseCleanupService.cleanupDatabase();
        return ResponseEntity.ok("Database cleaned up successfully.");
    }
}
