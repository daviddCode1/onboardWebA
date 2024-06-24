package com.OnbaordWeb.Controllers;


import com.OnbaordWeb.Dtos.DocumentReadingDto;
import com.OnbaordWeb.Services.Impl.DocumentReadingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/readings")
public class DocumentReadingController {

    private final DocumentReadingServiceImpl documentReadingServiceImpl;

    //save document reading
    @PostMapping("/reading")
    public ResponseEntity<?> saveReading(@RequestBody DocumentReadingDto documentReadingDto){


        try {

            DocumentReadingDto savedDto = documentReadingServiceImpl.saveDocumentReadingByDto(documentReadingDto);

            if(savedDto != null) {
                return ResponseEntity.ok(savedDto);
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in Results Saving Process!");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //get document reading by userId
    @GetMapping("/reading/{userId}")
    public ResponseEntity<?> getAllReadingDtosByUserId(@PathVariable("userId") Long userId){

        DocumentReadingDto reading = documentReadingServiceImpl.getReadingDtoByUserId(userId);

        return ResponseEntity.ok(reading);
    }

    //get document reading by userId
    @GetMapping("/readingsCount/{userId}")
    public ResponseEntity<?> getAllReadingCountByUserId(@PathVariable("userId") Long userId){

        int count = documentReadingServiceImpl.getReadingCountByUserId(userId);

        return ResponseEntity.ok(count);
    }
}
