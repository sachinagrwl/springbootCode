package com.nscorp.obis.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscorp.obis.common.ControllerConstants;
import com.nscorp.obis.domain.NotepadEntry;
import com.nscorp.obis.dto.NotepadEntryDTO;
import com.nscorp.obis.dto.mapper.NotepadEntryMapper;
import com.nscorp.obis.exception.NoRecordsFoundException;
import com.nscorp.obis.exception.RecordNotAddedException;
import com.nscorp.obis.response.data.APIResponse;
import com.nscorp.obis.response.data.ResponseStatusCode;
import com.nscorp.obis.services.NotepadEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Validated
@RestController
@RequestMapping("/")
public class NotepadEntryController {

    @Autowired(required = true)
    NotepadEntryService notepadEntryService;

    @GetMapping(value = ControllerConstants.NOTEPAD_ENTRY)
	public ResponseEntity<APIResponse<List<NotepadEntryDTO>>> getNotepadEntry(
        @RequestParam(name = "customerId", required = false) Long customerId,
        @RequestParam(name = "terminalId", required = false) Long terminalId) throws SQLException {
		try {
			List<NotepadEntryDTO> notepadEntryDTOs = new ArrayList<>();

			List<NotepadEntry> notepadEntry = notepadEntryService.fetchNotepadEntry(customerId,terminalId);
			if (notepadEntry != null && !notepadEntry.isEmpty()) {
				notepadEntryDTOs = notepadEntry.stream()
						.map(NotepadEntryMapper.INSTANCE::NotepadEntryToNotepadEntryDTO)
						.collect(Collectors.toList());
			}

			APIResponse<List<NotepadEntryDTO>> responseObj = new APIResponse<>(
					Arrays.asList("Successfully retrieved data!"), notepadEntryDTOs,
					ResponseStatusCode.SUCCESS.getStatusCode(), ResponseStatusCode.SUCCESS.toString());
			return ResponseEntity.status(HttpStatus.OK).body(responseObj);

		} catch (NoRecordsFoundException e) {
			APIResponse<List<NotepadEntryDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
		} catch (Exception e) {
			APIResponse<List<NotepadEntryDTO>> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()),
					ResponseStatusCode.INFORMATION.getStatusCode(), ResponseStatusCode.INFORMATION.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
		}
	}
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    @PostMapping(value = ControllerConstants.NOTEPAD_ENTRY,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<NotepadEntryDTO>> addNotepadEntry(@Valid @NotNull @RequestBody NotepadEntryDTO notepadEntryDTO, @RequestHeader Map<String, String> headers) {

        try {
            NotepadEntry notepadEntry = NotepadEntryMapper.INSTANCE.NotepadEntryDTOToNotepadEntry(notepadEntryDTO);
            NotepadEntry notepadEntryAdded = notepadEntryService.addNotepadEntry(notepadEntry, headers);
            NotepadEntryDTO addedNotepadEntryDTO = NotepadEntryMapper.INSTANCE.NotepadEntryToNotepadEntryDTO(notepadEntryAdded);
            APIResponse<NotepadEntryDTO> responseObj = new APIResponse<>(Arrays.asList("Successfully added data!"), addedNotepadEntryDTO, ResponseStatusCode.SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(responseObj);
        } catch (NoRecordsFoundException e) {
            APIResponse<NotepadEntryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
        } catch (RecordNotAddedException e){
            APIResponse<NotepadEntryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.INFORMATION);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseObj);
        } catch (Exception e) {
            APIResponse<NotepadEntryDTO> responseObj = new APIResponse<>(Arrays.asList(e.getMessage()), ResponseStatusCode.FAILURE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


}
