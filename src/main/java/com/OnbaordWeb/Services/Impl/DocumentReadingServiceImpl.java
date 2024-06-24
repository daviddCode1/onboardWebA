package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.DocumentReadingDto;
import com.OnbaordWeb.Exceptions.ResourceNotFoundException;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Document;
import com.OnbaordWeb.Models.DocumentReading;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.DocumentReadingRepository;
import com.OnbaordWeb.Services.DocumentReadingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentReadingServiceImpl implements DocumentReadingService {

    private final DocumentReadingRepository documentReadingRepository;
    private final UserServiceImpl userServiceImpl;
    private final DocumentServiceImpl documentServiceImpl;

    //save all
    @Override
    public List<DocumentReading> saveAll(List<DocumentReading> documentReadings) {
        return documentReadingRepository.saveAll(documentReadings);
    }

    //save
    @Override
    public DocumentReading save(DocumentReading documentReading) {
        return documentReadingRepository.save(documentReading);
    }

    //finaAll
    @Override
    public List<DocumentReading> findAll() {
        return documentReadingRepository.findAll();
    }

    //find by id
    @Override
    public Optional<DocumentReading> findById(Long id) {
        return documentReadingRepository.findById(id);
    }

    //find by user
    @Override
    public List<DocumentReading> findByUser(User user) {
        return documentReadingRepository.findByUser(user);
    }

    //delete by user
    @Override
    public void deleteByUser(User user) {
        documentReadingRepository.deleteByUser(user);
    }

    //method fr get Readings by user Id
    public List<DocumentReading> getReadingsByUserId(Long userId){

        User user = userServiceImpl.findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found!"));

        if(user != null){
            return findByUser(user);
        }

        return null;
    }

    //method for get all readings count by userId
    public int getReadingCountByUserId(Long userId){

        List<DocumentReading> readings = getReadingsByUserId(userId);

        if(!readings.isEmpty()){
            return readings.size();
        }

        return 0;
    }

    //method to get reading dto by user id
    public DocumentReadingDto getReadingDtoByUserId(Long userId){

        List<DocumentReading> readingList = getReadingsByUserId(userId);


        return  buildDtoByDocumentReadingList(readingList);
    }

    //method for build dto by obj list
    public DocumentReadingDto buildDtoByDocumentReadingList(List<DocumentReading> documentReadings){

        if(!documentReadings.isEmpty()){

            DocumentReadingDto documentReadingDto = new DocumentReadingDto();

            for(DocumentReading documentReading : documentReadings){

                //add values to dto
                documentReadingDto.setDocId(documentReading.getDocId());
                documentReadingDto.setUserId(documentReading.getUser().getUserId());

                //check obj values and add to relevant filed of dto
                if(documentReading.getDocument().getDocId() == 1){
                    documentReadingDto.setIsDocOneRead(1);
                }
                if(documentReading.getDocument().getDocId() == 2){
                    documentReadingDto.setIsDocTwoRead(1);
                }
                if(documentReading.getDocument().getDocId() == 3){
                    documentReadingDto.setIsDocThreeRead(1);
                }
                if(documentReading.getDocument().getDocId() == 4){
                    documentReadingDto.setIsDocFourRead(1);
                }
                if(documentReading.getDocument().getDocId() == 5){
                    documentReadingDto.setIsDocFiveRead(1);
                }
                if(documentReading.getDocument().getDocId() == 6){
                    documentReadingDto.setIsDocSixRead(1);
                }
                if(documentReading.getDocument().getDocId() == 7){
                    documentReadingDto.setIsDocSevenRead(1);
                }
            }

            return documentReadingDto;
        }

        return null;
    }

    //method for prepare save reading by dto
    public List<Document> getDocumentsListByDto(DocumentReadingDto documentReadingDto) {

                //build a document list which need to be saved as read
                List<Document> documentList = new ArrayList<>();

                //check the dto and add relevant obj to arraylist
                if (documentReadingDto.getIsDocOneRead() > 0) {
                    Document document = documentServiceImpl.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocTwoRead() > 0) {
                    Document document = documentServiceImpl.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocThreeRead() > 0) {
                    Document document = documentServiceImpl.findById(3L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocFourRead() > 0) {
                    Document document = documentServiceImpl.findById(4L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocFiveRead() > 0) {
                    Document document = documentServiceImpl.findById(5L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocSixRead() > 0) {
                    Document document = documentServiceImpl.findById(6L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }
                if (documentReadingDto.getIsDocSevenRead() > 0) {
                    Document document = documentServiceImpl.findById(7L).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));

                    documentList.add(document);
                }

                return documentList;

    }

    //save reading by dto
@Transactional
    public DocumentReadingDto saveDocumentReadingByDto(DocumentReadingDto documentReadingDto) {

        boolean isSaved = false;

        if (documentReadingDto != null) {
            //get the user
            User user = userServiceImpl.findById(documentReadingDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not Found!"));

            if (user != null) {
                //build documentReading list to be saved
                List<Document> documentListToSaved = getDocumentsListByDto(documentReadingDto);

                if(!documentListToSaved.isEmpty()){

                    List<DocumentReading> documentReadings = new ArrayList<>();

                    for(Document document : documentListToSaved){
                        //new obj for save
                        DocumentReading documentReading = new DocumentReading();

                        documentReading.setDocument(document);
                        documentReading.setIsRead(1);
                        documentReading.setUser(user);

                        documentReadings.add(documentReading);
                    }

                    //check lsit to save
                    if(!documentReadings.isEmpty()) {
                        //delete previous
                        deleteByUser(user);

                        //then save new records
                        List<DocumentReading> list = saveAll(documentReadings);

                        if (!list.isEmpty()) {
                            return buildDtoByDocumentReadingList(documentReadings);
                        }
                    }
                    }

                }


            }

        return null;
    }
}