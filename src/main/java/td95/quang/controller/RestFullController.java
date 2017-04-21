package td95.quang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import td95.quang.service.TagService;

@RestController
public class RestFullController {

	@Autowired
	private TagService tagService;

	@PostMapping("/getTagToPost")
	public ResponseEntity<String> getTag(@RequestBody String query) {
		String stringJSON = tagService.searchTag(query);
		HttpStatus httpStatus = HttpStatus.OK;
		if (stringJSON.isEmpty())
			httpStatus = HttpStatus.NOT_FOUND;
		return new ResponseEntity<String>(stringJSON, httpStatus);

	}

}
