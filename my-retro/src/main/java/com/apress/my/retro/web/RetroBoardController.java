package com.apress.my.retro.web;

import com.apress.my.retro.board.Card;
import com.apress.my.retro.board.RetroBoard;
import com.apress.my.retro.service.RetroBoardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings("unused")
@AllArgsConstructor //Creates constructor for all fields of the class;
// For this case RetroBoardService gets injected automatically by SpringBoot (used to need @Autowired to do so)
@RestController // "...writes directly to the response body using all the methods declared."
@RequestMapping("/retros")//marks this class as handler of requests made for the specified path,
// HTTP requests that it is equipped to handle that is. Also marks /retros as base path of the endpoint.
public class RetroBoardController {
    private RetroBoardService retroBoardService;

    /**
     * "@GetMapping" -> This method will handle GET HTTP requests made on the base endpoint
     * -> Replaces/Is a Shortcut for: @RequestMapping("method = RequestMethod.GET")
     * -> Has multiple parameters that can be used;
     * "/{uuid}" variables can be extracted from the path, this is mapped to URL patterns.
     * "retros/docu?ent.doc" -> Match only one character in a path
     * "retros/*.jpg" -> Match 0 or more characters in a path; regex style
     * "retros/**" -> Match multiple path sequence
     * "retros/{project}/versions" -> Match a path segment and capture it as a variable
     * "retros/{project:[a-z]+}/versions" -> Match and capture a variable with a regex
     * Ex: @GetMapping("/{product:[a-z]+}-{version:\\d\\.\\d\\.\\d}{ext:\\.[a-z]+}")
     * There are many ways to define how an endpoint can get accessed (aka specify the path).
     * This path creation works with @RequestMapping and its shortcuts
     *
     * ResponseEntity -> Extends HttpEntity (brings headers and the body of a request/response);
     * -> Common practice for web apps; SpringBoot responds by default using HTTP JSON message
     * converter, so unless changed, a JSON response should always be expected.
     * @return 200 OK Http response and all stored RetroBoards
     */
    @GetMapping
    public ResponseEntity<Iterable<RetroBoard>> getAllRetroBoards(){
//        List<RetroBoard> result = new ArrayList<>();
//        for (RetroBoard retroBoard : retroBoardService.findAll()){
//            result.add(retroBoard);
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity response = new ResponseEntity(result, headers, HttpStatus.OK);
//        return response;

        return ResponseEntity.ok(retroBoardService.findAll());
    }

    /**
     * "@PostMapping" -> @RequestMapping( method = RequestMethod.POST);
     * "@RequestBody" -> Tries to map the body of the request, using HttpMessageConverter,
     * to the instance of the type(class) that this annotation precedes.
     * Validation can be used to validate data through @Valid annotation
     * "@Valid" -> Does a cascade style validation where it checks the constraints for the
     * object it annotates. Types of constraints: @NotNull,@NotEmpty,@NotBlank,@Pattern,@Size,etc
     * There can also be defined a custom validator but the default handles pretty well common data constraints.
     * "@PathVariable" -> This binds the path value to the instance(with the same name)
     * of the type it annotates.
     * @param retroBoard -> request body converted to RetroBoard type passed as parameter
     *                   to the method.
     * @return created response with 201 code for the exact path the request was made to, and
     * the RetroBoard object, created as a result of this request, converted to JSON as the body.
     */
    @PostMapping
    public ResponseEntity<RetroBoard> saveRetroBoard(@Valid @RequestBody RetroBoard retroBoard){
        RetroBoard result = retroBoardService.save(retroBoard);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(result.getId().toString())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RetroBoard> findRetroBoardById(@PathVariable UUID uuid) {
        return ResponseEntity.ok(retroBoardService.findById(uuid));
    }

    @GetMapping("/{uuid}/cards")
    public ResponseEntity<Iterable<Card>> getAllCardsFromBoard(@PathVariable UUID uuid){
        return ResponseEntity.ok(retroBoardService.findAllCardsFromRetroBoard(uuid));
    }

    // Usually post, for this case it is a combination of a path (with URL pattern) and an HTTP
    // body (@RequestBody).
    @PutMapping("/{uuid}/cards")
    public ResponseEntity<Card> addCardToRetroBoard(@PathVariable UUID uuid, @Valid @RequestBody Card card) {
        Card result = retroBoardService.addCardToRetroBoard(uuid, card);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{uuid}/cards/{uuidCard}")
                .buildAndExpand(uuid.toString(),result.getId().toString())
                .toUri();
        return ResponseEntity.created(location).body(result);
    }

    @GetMapping("/{uuid}/cards/{uuidCard}")
    public ResponseEntity<Card> getCardFromRetroBoard(@PathVariable UUID uuid, @PathVariable UUID uuidCard){
        return ResponseEntity.ok(retroBoardService.findCardByUUIDFromRetroBoard(uuid,uuidCard));
    }

    //For setting up a HTTP code into the response of a controller method or an exception handler.
    //204 NO_CONTENT in this case
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    public void deleteCardFromRetroBoard(@PathVariable UUID uuid, @PathVariable UUID uuidCard){
        retroBoardService.removeCardFromRetroBoard(uuid, uuidCard);
    }

    /* Marks a method as the handler of a specified exception thrown withing the controller
     * or withing the scope of @ControllerAdvice class (for global exception handling).
     * MethodArgumentNotValidException class -> this will be thrown if any validations fail
     * during the @Valid cascade validation. And the exception handler for this exception is
     * the method handleValidationException.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex){
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "There is an error");
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.put("errors", errors);
        return response;
    }
}

/*
"ServletUriComponentsBuilder: This is a helper class that can be used
to create a URI and expand to the base path with the keys and values.
In this case, this class is used in the saveRetroBoard method, where it
will create the location needed in the Header that will be set as part of
the response with the ResponseEntity.create(<URI>) method call."
 */
